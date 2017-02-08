
 /**  
 *@Description:     
 */ 
package com.gh.util.spider;  

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gh.model.vo.SysCode;
  
public class CrawlNews {
	private static List<Info> infos;
	private static int pageNo = 20; // 每类新闻查询页数
	private static List<SysCode> keyWord = new ArrayList<SysCode>(); // 搜索关键词
	private static int newsCount = 0; // 新闻数量统计
	
	static {
		infos = new ArrayList<Info>();
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=sportnews&pn=1&from=tab", "体育类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=sportnews&pn=2&from=tab", "体育类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=sportnews&pn=3&from=tab", "体育类"));
//		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=mil&pn=1&sub=0", "军事类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=mil&pn=2&sub=0", "军事类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=mil&pn=3&sub=0", "军事类"));
//		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=finannews&pn=1&sub=0", "财经类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=finannews&pn=2&sub=0", "财经类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=finannews&pn=3&sub=0", "财经类"));
//		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=internet&pn=1&from=tab", "互联网"));
//		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=housenews&pn=1&sub=0", "房产类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=housenews&pn=2&sub=0", "房产类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=housenews&pn=3&sub=0", "房产类"));
		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=gamenews&pn=1&sub=0", "游戏类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=gamenews&pn=2&sub=0", "游戏类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=gamenews&pn=3&sub=0", "游戏类"));
		
		// 每类新闻查询翻页查询
		for(int i=1; i<=pageNo; i++){
//			infos.add(new Info("http://news.baidu.com/n?cmd=4&class=gamenews&pn="+i+"&sub=0", "游戏类"));
			infos.add(new Info("http://news.baidu.com/n?cmd=4&class=tvgames&pn="+i+"&sub=0", "电视游戏类"));
//			infos.add(new Info("http://news.baidu.com/n?cmd=4&class=tv&pn="+i+"&sub=0", "电视焦点"));
//			infos.add(new Info("http://news.baidu.com/n?cmd=4&class=netgames&pn="+i+"&sub=0", "网络游戏类"));
		}
		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=tv&pn=1&sub=0", "电视焦点"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=tv&pn=2&sub=0", "电视焦点"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=tv&pn=3&sub=0", "电视焦点"));
		
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=netgames&pn=1&sub=0", "网络游戏类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=netgames&pn=2&sub=0", "网络游戏类"));
//		infos.add(new Info("http://news.baidu.com/n?cmd=4&class=netgames&pn=3&sub=0", "网络游戏类"));
	}
	
	/**
	 *@Description:  抓取网址信息
	 *@Author:lulei  
	 */
	static class Info{
		String url;
		String type;
		Info(String url, String type) {
			this.url = url;
			this.type = type;
		}
	}
	
	/**
	 * @param info
	 * @Author:lulei  
	 * @Description: 抓取一个列表页面下的新闻信息
	 */
	private void crawl(Info info) {
		if (info == null) {
			return;
		}
		try {
			TestNewsList baiduNewList = new TestNewsList(info.url);
			List<String> urls = baiduNewList.getPageUrls();
			for (String url : urls) {
				News news = new News(url, keyWord, StringUtils.EMPTY);
				if(StringUtils.isNotEmpty(news.getContent())){
					newsCount++;
					System.out.println(newsCount);
					System.out.println(url);
					System.out.println(info.type);
					System.out.println(news.getTitle());
					System.out.println(news.getContent());
					System.out.println(news.getPicUrl1());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Author:lulei  
	 * @Description: 启动入口
	 */
	public void run() {
		for (Info info : infos) {
			crawl(info);
		}
	}
	
	public static void main(String[] args) {
		new CrawlNews().run();
	}
}

