 /**  
  *@Description: 获取页面链接地址信息基类  
 */ 
package com.gh.util.spider;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class CrawlListPageBase extends CrawlBase {
	private String pageurl;
	private String newsUrlRegularExpression;
	
	/**
	* @param urlStr
	* @param charsetName
	* @throws IOException
	 */
	public CrawlListPageBase(String urlStr, String charsetName, String keyWord, 
			String shieldingWords) throws IOException{
		readPageByGet(urlStr, charsetName, keyWord, shieldingWords);
		pageurl = urlStr;
	}
	
	/**
	* @param urlStr
	* @param charsetName
	* @param method
	* @param params
	* @throws IOException
	 */
	public CrawlListPageBase(String urlStr, String charsetName, String method, HashMap<String, String> params) throws IOException{
		readPage(urlStr, charsetName, method, params);	
		pageurl = urlStr;
	}
	
	/**
	 * @return List<String>
	 * @Author: lulei  
	 * @Description: 返回页面上需求的链接地址
	 */
	public List<String> getPageUrls(){
		List<String> pageUrls = new ArrayList<String>();
		pageUrls = DoRegex.getArrayList(getPageSourceCode(), getNewsUrlRegularExpression(), pageurl, getUrlRegexStringNum());
		return pageUrls;
	}
	
	/**
	 * @return int
	 * @Author: lulei  
	 * @Description: 正则表达式中要去的字段位置
	 */
	public abstract int getUrlRegexStringNum();

	public String getNewsUrlRegularExpression() {
		return newsUrlRegularExpression;
	}

	public void setNewsUrlRegularExpression(String newsUrlRegularExpression) {
		this.newsUrlRegularExpression = newsUrlRegularExpression;
	}	
	
}
