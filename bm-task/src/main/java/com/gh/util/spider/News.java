
 /**  
 *@Description:   新闻类网站新闻内容 
 */ 
package com.gh.util.spider;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gh.model.vo.SysCode;
import com.gh.util.CommonUtils;
import com.gh.util.constant.Constant;
import com.gh.util.constant.Message;
  
public class News extends CrawlBase{
	private static Logger logger = Logger.getLogger(News.class);
	private String url;
	private String picUrl1;
	private String picUrl2;
	private String picUrl3;
	private String summary;
	private String content;
	private String title;
	private String type;
	
	private static String contentRegex = "<p.*?>(.*?)</p>";
	private static String titleRegex = "<title>(.*?)</title>";
	private static String picRegex = "<p.*?<img.*?src=\"(.*?)\"";
	private static int maxLength = 300; // 概要最大长度
	private static int titileMaxLength = 100;
	
	private static HashMap<String, String> params;
	/**
	 * 添加相关头信息，对请求进行伪装
	 */
	static {
		params = new HashMap<String, String>();
		params.put("Referer", "http://www.baidu.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	}
	
	/**
	 * @throws IOException 
	 * @Author:lulei  
	 * @updateBy:HeMingwei
	 * @Description: 默认p标签内的内容为正文，如果正文长度查过设置的最大长度，则截取前半部分
	 */
	private void setContent(String url, List<SysCode> keyWordList,String shieldingWords) throws IOException {
		// httpClient方式变为Jsoup获取网页源码
//		String html = getPageSourceCode();
		// 通过Readability取得精简过后的正文内容
		this.content = CommonUtils.getMajorHtmlByUri(url);
		if(StringUtils.isBlank(this.content)){
			return;
		}
		// 去掉a标签的内容
		String summary = this.content.replaceAll("<a.*?/a>", "");
		// 取得新闻内容
		summary = DoRegex.getString(summary, contentRegex, 1);
		// 过滤script style 等非新闻内容部分
		summary = summary.replaceAll("\n", "")
		  .replaceAll("<script.*?/script>", "")
		  .replaceAll("<style.*?/style>", "")
		  .replaceAll("<.*?>", "");
		// 判断页面中是否包含关键词(空格分开,关键词在页面整体代码里查会一点)
		boolean exists = false;
		boolean InnerExists; // 
		for(SysCode code : keyWordList){
			InnerExists = true;
			String[] keyWords = code.getName().split(" ");
			for(String str : keyWords){
				if(StringUtils.isNotEmpty(str.trim()) && this.content.toUpperCase().indexOf(str.trim().toUpperCase()) == -1){
					InnerExists = false;
					break;
				}
			}
			if(InnerExists){
				exists = true;
				break;
			}
		}
		// 若任意关键词组合都不匹配,则跳出内容设定
		if(!exists){
			return;
		}
		
		// 判断页面中是否包含敏感词(空格分开)
		String[] shieldings = shieldingWords.split(" ");
		// 过滤敏感词汇
		for(String str : shieldings){
			if(StringUtils.isNotEmpty(str.trim()) && this.content.toUpperCase().indexOf(str.trim().toUpperCase()) != -1){
				logger.info("网页:" + url + "含有敏感词汇："+ str.trim() + ",跳过抓取。");
				return;
			}
		}
		this.summary = summary.length() > maxLength ? summary.substring(0, maxLength) : summary;
	}
	
	/**
	 * @Author:lulei  
	 * @Description: 默认title标签内的内容为标题
	 */
	private void setTitle() {
		String titleStr = DoRegex.getString(content, titleRegex, 1);
		this.title = titleStr.length() > titileMaxLength ? titleStr.substring(0, titileMaxLength) : titleStr;
	}
	
	/**
	 * 设置图片地址
	 * @param picUrl1
	 */
	public void setPicUrl() {
		String content = this.content.replaceAll("\n", "")
		  .replaceAll("<script.*?/script>", "")
		  .replaceAll("<style.*?/style>", "");
		Pattern pattern = Pattern.compile(picRegex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		int picNum = 0;
		while (matcher.find()) {
			String url = matcher.group(1).trim();
			if(url.startsWith("http")){
				// 得到图片的像素
				int[] xy = CommonUtils.returnImgWH(url);
				// 横向像素不小于160,纵向像素不小于120的图片认为是新闻图片,否则是广告或者logo
				if(!(xy[0] < Constant.MIN_NEWS_PICTURE_X || xy[1] < Constant.MIN_NEWS_PICTURE_Y)){
					if(picNum == 0){
						this.picUrl1 = matcher.group(1).trim();
					}else if(picNum == 1){
						this.picUrl2 = matcher.group(1).trim();
					}else if(picNum == 2){
						this.picUrl3 = matcher.group(1).trim();
					}else{
						break;
					}
					picNum++;
				}
			}
		}
		// 图片模式设定
		this.type = String.valueOf(picNum);
	}
	
	public News(String url, List<SysCode> keyWordList, String shieldingWords) {
		this.url = url;
		try {
			// 设定新闻内容，同时过滤敏感词汇，打印日志
			setContent(url, keyWordList, shieldingWords);
		} catch (IOException e) {
			logger.error(Message.GRAB_NEWS_EXCEPTION + ",地址为：" + url);
		} 
		if(StringUtils.isNotEmpty(content)){
			setTitle();
			setPicUrl();
		}
//		if(readPageByGet(url, "utf-8", params)){
//			setContent(url, keyWordList, shieldingWords); // 设定新闻内容，同时过滤敏感词汇，打印日志
//			if(StringUtils.isNotEmpty(content)){
//				setTitle();
//				setPicUrl();
//			}
//		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static void setMaxLength(int maxLength) {
		News.maxLength = maxLength;
	}

	public String getPicUrl1() {
		return picUrl1;
	}
	
	public String getPicUrl2() {
		return picUrl2;
	}
	
	public String getPicUrl3() {
		return picUrl3;
	}

	/**
	 * @param args
	 * @throws HttpException
	 * @throws IOException
	 * @Author:lulei  
	 * @Description: 测试用例
	 */
	public static void main(String[] args) throws HttpException, IOException {
		// TODO Auto-generated method stub  
		SysCode sc = new SysCode();
		sc.setName(" ");
		List<SysCode> asdf = new ArrayList<SysCode>();
		asdf.add(sc);
		News news = new News("http://www.joyup.tv/articles/5154.html", asdf, StringUtils.EMPTY);
		System.out.println(news.getContent());
		System.out.println(news.getTitle());
		System.out.println(news.getType());
		System.out.println(news.getPicUrl1());
		System.out.println(news.getPicUrl2());
		System.out.println(news.getPicUrl3());
	}

}

