package com.gh.service.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gh.dao.OperationNewsMapper;
import com.gh.dao.OperationNewsWebsitesMapper;
import com.gh.dao.SysCodeMapper;
import com.gh.model.vo.OperationNews;
import com.gh.model.vo.OperationNewsWebsites;
import com.gh.model.vo.SysCode;
import com.gh.util.CommonUtils;
import com.gh.util.SimilarityUtil;
import com.gh.util.constant.Constant;
import com.gh.util.constant.Message;
import com.gh.util.spider.News;
import com.gh.util.spider.NewsList;

@Service("crawlNews")
public class CrawlNewsServiceImpl {
	
	private static final Logger logger = Logger.getLogger(CrawlNewsServiceImpl.class);
	
	@Autowired
	OperationNewsWebsitesMapper operationNewsWebsitesMapper;
	
	@Autowired
	SysCodeMapper sysCodeMapper;
	
	@Autowired
	OperationNewsMapper operationNewsMapper;
	
	/**
	 * @param info
	 * @Author:HeMingwei 
	 * @Description: 抓取一个列表页面下的新闻信息
	 */
	private List<OperationNews> crawl(OperationNewsWebsites info, List<SysCode> keyWordList, String shieldingWords) {
		List<OperationNews> newsList = new ArrayList<OperationNews>();
		if (info == null) {
			return null;
		}
		// 获取到的新闻网址集合
		List<String> urls = null;
		NewsList nl = null;
		try {
			nl = new NewsList(info.getWebsiteUrl());
			nl.setNewsUrlRegularExpression(info.getNewsUrlRegularExpression());
			urls = nl.getPageUrls();
		} catch (IOException e) {
			logger.error("新闻资源网站访问异常，资源网站地址为：" + info.getWebsiteUrl());
		}
		// 新闻抓取结果封装
		for (String url : urls) {
			News news = new News(url, keyWordList, shieldingWords);
			OperationNews tempNews = new OperationNews();
			if(StringUtils.isNotEmpty(news.getContent())){
				tempNews.setOriginalUri(url);
				tempNews.setTitle(news.getTitle());
				tempNews.setSummary(news.getSummary());
				tempNews.setContent(news.getContent());
				tempNews.setType(news.getType());
				tempNews.setPicUrl1(news.getPicUrl1());
				tempNews.setPicUrl2(news.getPicUrl2());
				tempNews.setPicUrl3(news.getPicUrl3());
				newsList.add(tempNews);
			}
		}
		return newsList;
	}
	
	/**
	 * @Author:HeMingwei 
	 * @Description: 启动入口
	 */
	public void run() {
		try{
			logger.info("获取新闻计划任务开始...");
			// 获取敏感词汇
			SysCode code = sysCodeMapper.selectByPrimaryKey(new SysCode(Constant.NUMBER_ZERO, Constant.NUMBER_ZERO));
			// 获取关键词组合 列表
			List<SysCode> codeList = sysCodeMapper.selectSysCodeForNewsKeyWords();
			String shieldingWords = code != null ? code.getName() : StringUtils.EMPTY;
			List<OperationNews> newsList = new ArrayList<OperationNews>();
			OperationNewsWebsites onw = new OperationNewsWebsites();
			onw.setEnabled(Constant.WORD_YES);
			List<OperationNewsWebsites> websitelist = operationNewsWebsitesMapper.selectNewsWebsitesList(onw);
			for (OperationNewsWebsites website : websitelist) {
				List<OperationNews> list = crawl(website, codeList, shieldingWords);
				if(list != null){
					newsList.addAll(list);
				}
			}
			batch(newsList); // 执行批处理
			logger.info("获取新闻计划任务结束...");
		}catch(Exception e){
//			logger.info("jvm监听:maxMemory:" + Runtime.getRuntime().maxMemory()/1048576 
//					+ "M,totalMemory:" + Runtime.getRuntime().totalMemory()/1048576
//					+ "M,freeMemory:" + Runtime.getRuntime().freeMemory()/1048576 + "M");
			logger.error("获取新闻计划任务异常终止", e);
		}
	}
	
	/**
	 * 新闻批处理
	 * @param newsList
	 * @return 删除的历史新闻数量
	 * @throws 
	 */
	private void batch(List<OperationNews> newsList){
		Date date = new Date();
		int addCount = 0; // 新增新闻数量
		int delCount = 0; // 删除的历史新闻数量
		try{
			// 删除{days}天前的非推荐历史新闻,并且DB中要保留指定数量(推荐和非推荐暂定一共1000条)的新闻
			delCount = operationNewsMapper.deleteHistoryNews(Constant.NEED_TO_DELETE_NEWS_DAYS, Constant.PERSIST_NEWS_AMOUNT);
			List<OperationNews> historyNews = operationNewsMapper.selectNewsList(null);
			// 插入新闻
			for(OperationNews news : newsList){
				boolean similar = false;
				int sameNewsAmount = 0;
				// 抓取到的新闻(抓取的list本身)对比 去除重复 两条一样即为重复新闻
				for(OperationNews prepareToCheckNews : newsList){
					if(StringUtils.isEmpty(prepareToCheckNews.getSummary()) && StringUtils.isEmpty(news.getSummary())){
						sameNewsAmount++;
					}else if(StringUtils.isNotEmpty(prepareToCheckNews.getSummary()) && StringUtils.isNotEmpty(news.getSummary())){
						if(SimilarityUtil.sim(prepareToCheckNews.getSummary(), news.getSummary()) > Constant.SIMILARITY_INDEX){
							sameNewsAmount++;
						}
					}
				}
				
				// 如果抓取到的新闻自身没有重复新闻,与历史新闻比对,内容不相似则为新闻，执行数据归档(要求高设置小一些，要求低指标就设置的大一些)
				if(sameNewsAmount == 1){
					for(OperationNews compareNews : historyNews){
						if(StringUtils.isEmpty(compareNews.getSummary()) && StringUtils.isEmpty(news.getSummary())){
							similar = true;
							break;
						}else if(StringUtils.isNotEmpty(compareNews.getSummary()) && StringUtils.isNotEmpty(news.getSummary())){
							if(SimilarityUtil.sim(compareNews.getSummary(), news.getSummary()) > Constant.SIMILARITY_INDEX){
								similar = true;
								break;
							}
						}
					}
				}else{
					similar = true;
				}
				
				// 没有相似新闻，则数据归档
				if(!similar){
					news.setId(CommonUtils.nextId());
					// 生成项目的新闻uri
					news.setOriginalUri(Constant.DOMAIN_NAME + Constant.API_START_URI + Constant.API_VERSION
							+ "/news/" + news.getId() + Constant.SUFFIX);
					news.setSort(Constant.MAX_SORT_NUM);
					news.setIsRecommend(Constant.NUMBER_ZERO);
					news.setCreateDate(date);
					news.setUpdateDate(date);
					news.setCreateBy(Constant.SCHEDULED_TASK);
					news.setUpdateBy(Constant.SCHEDULED_TASK);
					operationNewsMapper.insert(news);
					addCount++;
				}
			}
		}catch(Exception e){
			logger.error(Message.BATCH_NEWS_EXCEPTION, e);
		}
		logger.info("本次新增新闻：" + addCount + "条,删除" + Constant.NEED_TO_DELETE_NEWS_DAYS + "天前历史新闻：" + delCount + "条。");
	}
	
}
