/**
**com.lulei.crawl.CrawlBase
**/
 /**  
 *@Description: 获取网页信息基类
 */ 
package com.gh.util.spider;  

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

public abstract class CrawlBase {
	private static Logger logger = Logger.getLogger(CrawlBase.class);
	
	//链接源代码
	private String pageSourceCode = "";
	//返回头信息
	private Header[] responseHeaders = null;
	//连接超时时间
	private static int connectTimeout = 10000;
	//连接读取时间
	private static int readTimeout = 10000;
	//默认最大访问次数
	private static int maxConnectTimes = 3;
	//网页默认编码方式
	private static String charsetName = "iso-8859-1";
	//将HttpClient委托给MultiThreadedHttpConnectionManager，支持多线程
	private static MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
	private static HttpClient httpClient = new HttpClient(httpConnectionManager);
	
	static {
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeout);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
		httpClient.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(32); // very important!! 默认是2
		httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(256); // very important!! ，默认是20
		//设置请求的编码格式
		httpClient.getParams().setContentCharset("utf-8");
	}
	/**
	 * @param urlStr
	 * @param charsetName
	 * @param method
	 * @param params
	 * @return
	 * @Author: lulei  
	 * @Description: method方式访问页面
	 */
	public boolean readPage(String urlStr, String charsetName, String method, HashMap<String, String> params) {
		if ("post".equals(method) || "POST".equals(method)) {
			return readPageByPost(urlStr, charsetName, params);
		} else {
			return readPageByGet(urlStr, charsetName, params);	
		}
	}
	
	/**
	 * @param urlStr
	 * @param charsetName
	 * @param params
	 * @param keyWord
	 * @return 访问是否成功
	 * @Author: lulei  
	 * @Description: Get方式访问页面
	 */
	public boolean readPageByGet(String urlStr, String charsetName, HashMap<String, String> params) {
		GetMethod getMethod = createGetMethod(urlStr, params);
		return readPage(getMethod, charsetName, urlStr);
	}
	
	/**
	 * @param urlStr
	 * @param charsetName
	 * @param params
	 * @return 访问是否成功
	 * @throws HttpException
	 * @throws IOException
	 * @Author: lulei  
	 * @Description: Post方式访问页面
	 */
	public boolean readPageByPost(String urlStr, String charsetName, HashMap<String, String> params){
		PostMethod postMethod = createPostMethod(urlStr, params);
		return readPage(postMethod, charsetName, urlStr);
	}
	
	/**
	 * @param urlStr
	 * @param charsetName
	 * @param xmlString
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws HttpException
	 * @throws IOException
	 * @Author:lulei  
	 * @Description: 提交xml流参数
	 */
	public boolean readPageByPostXml(String urlStr, String charsetName, String xmlString, String keyWord, 
			String shieldingWords) throws UnsupportedEncodingException {
		PostMethod postMethod = createPostMethodXml(urlStr, xmlString);
		return readPage(postMethod, charsetName, urlStr);
	}
	
	/**
	 * @param urlStr
	 * @param charsetName
	 * @param jsonString
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws HttpException
	 * @throws IOException
	 * @Author:lulei  
	 * @Description: 提交json流参数
	 */
	public boolean readPageByPostJson(String urlStr, String charsetName, String jsonString, String keyWord, 
			String shieldingWords) throws UnsupportedEncodingException {
		PostMethod postMethod = createPostMethodJson(urlStr, jsonString);
		return readPage(postMethod, charsetName, urlStr);
	}
	
	
	/**
	 * @param method
	 * @param defaultCharset
	 * @param urlStr
	 * @return 访问是否成功
	 * @throws HttpException
	 * @throws IOException
	 * @Author: lulei  
	 * @Description: 读取页面信息和头信息
	 */
	private boolean readPage(HttpMethod method, String defaultCharset, String urlStr){
		int n = maxConnectTimes;
		while (n > 0) {
			try {
				// 发起http请求
				int httpStatusCode = httpClient.executeMethod(method);
				// 通过http状态码 判断是否要再次发起请求
				if (httpStatusCode != HttpStatus.SC_OK){
					logger.info("can not connect " + urlStr + "\t" + (maxConnectTimes - n + 1) + "\t" + httpStatusCode);
					n--;
				} else {
					//获取头信息
					responseHeaders = method.getResponseHeaders();
					//获取页面源代码
					InputStream inputStream = method.getResponseBodyAsStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
					StringBuffer stringBuffer = new StringBuffer();
					String lineString = null;
					while ((lineString = bufferedReader.readLine()) != null){
						stringBuffer.append(lineString);
						stringBuffer.append("\n");
					}
					pageSourceCode = stringBuffer.toString();
					InputStream in =new  ByteArrayInputStream(pageSourceCode.getBytes(charsetName));
					String charset = CharsetUtil.getStreamCharset(in, defaultCharset);
					//下面这个判断是为了IP归属地查询特意加上去的
					if ("Big5".equals(charset)) {
						charset = "gbk";
					}
					if (!charsetName.toLowerCase().equals(charset.toLowerCase())) {
						pageSourceCode = new String(pageSourceCode.getBytes(charsetName), charset);
					}
					return true;
				}
			} catch (Exception e) {
				logger.error(urlStr + " -- can't connect  " + (maxConnectTimes - n + 1));
				n--;
			}
		}
		return false;
	}
	
	/**
	 * @param url
	 * @return
	 * @Author:lulei  
	 * @Description: 对URL中的中文做预处理
	 */
	private String encodeUrlCh(String url) {
		try {
			return DoRegex.encodeUrlCh(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return url;
		}
	}
	
	/**
	 * @param urlStr
	 * @param params
	 * @return GetMethod
	 * @Author: lulei  
	 * @Description: 设置get请求参数
	 */
	private GetMethod createGetMethod(String urlStr, HashMap<String, String> params){
		urlStr = encodeUrlCh(urlStr);
		GetMethod getMethod = new GetMethod(urlStr);
		if (params == null){
			return getMethod;
		}
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			getMethod.setRequestHeader(key, val);
		}
		return getMethod;
	}
	
	/**
	 * @param urlStr
	 * @param params
	 * @return PostMethod
	 * @Author: lulei  
	 * @Description: 设置post请求参数
	 */
	private PostMethod createPostMethod(String urlStr, HashMap<String, String> params){
		urlStr = encodeUrlCh(urlStr);
		PostMethod postMethod = new PostMethod(urlStr);
		if (params == null){
			return postMethod;
		}
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry =  iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			postMethod.setParameter(key, val);
		}
		return postMethod;
	}
	
	/**
	 * @param urlStr
	 * @param jsonString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Author:lulei  
	 * @Description: 设置json格式流参数
	 */
	private PostMethod createPostMethodJson(String urlStr, String jsonString) throws UnsupportedEncodingException{
		urlStr = encodeUrlCh(urlStr);
		PostMethod postMethod = new PostMethod(urlStr);
		StringRequestEntity entity = new StringRequestEntity(jsonString, "text/json", "utf-8");
		postMethod.setRequestEntity(entity);
		return postMethod;
	}
	
	/**
	 * @param urlStr
	 * @param xmlString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Author:lulei  
	 * @Description: 设置xml格式流参数
	 */
	private PostMethod createPostMethodXml(String urlStr, String xmlString) throws UnsupportedEncodingException{
		urlStr = encodeUrlCh(urlStr);
		PostMethod postMethod = new PostMethod(urlStr);
		StringRequestEntity entity = new StringRequestEntity(xmlString, "text/xml", "utf-8");
		postMethod.setRequestEntity(entity);
		return postMethod;
	}
	
	/**
	 * @param urlStr
	 * @param charsetName
	 * @return 访问是否成功
	 * @Author: lulei  
	 * @Description: 不设置任何头信息直接访问网页
	 */
	public boolean readPageByGet(String urlStr, String charsetName, String keyWord, String shieldingWords){
		return this.readPageByGet(urlStr, charsetName, null);
	}
	
	/**
	 * @return String
	 * @Author: lulei  
	 * @Description: 获取网页源代码
	 */
	public String getPageSourceCode(){
		return pageSourceCode;
	}
	
	/**
	 * @return Header[]
	 * @Author: lulei  
	 * @Description: 获取网页返回头信息
	 */
	public Header[] getHeader(){
		return responseHeaders;
	}
	
	/**
	 * @param timeout
	 * @Author: lulei  
	 * @Description: 设置连接超时时间
	 */
	public void setConnectTimeout(int timeout){
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		CrawlBase.connectTimeout = timeout;
	}
	
	/**
	 * @param timeout
	 * @Author: lulei  
	 * @Description: 设置读取超时时间
	 */
	public void setReadTimeout(int timeout){
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		CrawlBase.readTimeout = timeout;
	}
	
	/**
	 * @param maxConnectTimes
	 * @Author: lulei  
	 * @Description: 设置最大访问次数，链接失败的情况下使用
	 */
	public static void setMaxConnectTimes(int maxConnectTimes) {
		CrawlBase.maxConnectTimes = maxConnectTimes;
	}

	/**
	 * @param connectTimeout
	 * @param readTimeout
	 * @Author: lulei  
	 * @Description: 设置连接超时时间和读取超时时间
	 */
	public void setTimeout(int connectTimeout, int readTimeout){
		setConnectTimeout(connectTimeout);
		setReadTimeout(readTimeout);
	}

	/**
	 * @param charsetName
	 * @Author: lulei  
	 * @Description: 设置默认编码方式
	 */
	public static void setCharsetName(String charsetName) {
		CrawlBase.charsetName = charsetName;
	}
}
