
 /**  
 *@Description:   测试 有RSS地址的新闻 网站 RSS正则表达式
 */ 
package com.gh.util.spider;  

import java.io.IOException;
import java.util.HashMap;
  
public class TestNewsList extends CrawlListPageBase{
	private static HashMap<String, String> params;
	
	/**
	 * 添加相关头信息，对请求进行伪装
	 */
	static {
		params = new HashMap<String, String>();
		params.put("Referer", "http://www.baidu.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	}

	public TestNewsList(String urlStr) throws IOException {
		super(urlStr, "utf-8", "get", params);  
	}
	 
	@Override
	public int getUrlRegexStringNum() {
		// TODO Auto-generated method stub  
		//链接地址在正则表达式中的位置
		return 1;
	}

	/**  
	 * @param args
	 * @throws IOException
	 * @Author:lulei  
	 * @Description:  测试用例
	 * 乐上-电视游戏
	 * http://www.joyup.tv/category/news/
	 * img-mask.*?<div>.*?<a.*?href=\"(.*?)\"
	 * 乐上-测评中心
	 * http://www.joyup.tv/category/gamereviews/
	 * img-mask.*?<div>.*?<a.*?href=\"(.*?)\"
	 * 乐上-行业关注
	 * http://www.joyup.tv/category/news/Industry/
	 * img-mask.*?<div>.*?<a.*?href=\"(.*?)\"
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub  
		TestNewsList baidu = new TestNewsList("http://news.baidu.com/n?cmd=4&class=gamenews&pn=1&sub=0");
		baidu.setNewsUrlRegularExpression("<div>\\S{7}<a href=\"(.*?)\"");
		for (String s : baidu.getPageUrls()) {
			System.out.println(s);
		}
	}
}

