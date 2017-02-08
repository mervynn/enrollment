 /**  
 *@Description:  编码方式检测类  
 */ 
package com.gh.util.spider;  

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import cpdetector.io.ASCIIDetector;
import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;
import cpdetector.io.ParsingDetector;
import cpdetector.io.UnicodeDetector;
  
public class CharsetUtil {
	private static final CodepageDetectorProxy detector;
	
	static {//初始化探测器
		detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		detector.add(JChardetFacade.getInstance());
	}

	/**
	 * @param url
	 * @param defaultCharset
	 * @Author:lulei  
	 * @return 获取文件的编码方式
	 */
	public static String getStreamCharset (URL url, String defaultCharset) {
		if (url == null) {
			return defaultCharset;
		}
		try {
			//使用第三方jar包检测文件的编码
			Charset charset = detector.detectCodepage(url);
			if (charset != null) {
				return charset.name();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return defaultCharset;
	}
	
	/**
	 * @param inputStream
	 * @param defaultCharset
	 * @return
	 * @Author:lulei  
	 * @Description: 获取文件流的编码方式
	 */
	public static String getStreamCharset (InputStream inputStream, String defaultCharset) {
		if (inputStream == null) {
			return defaultCharset;
		}
		int count = 200;
		try {
			count = inputStream.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//使用第三方jar包检测文件的编码
			Charset charset = detector.detectCodepage(inputStream, count);
			if (charset != null) {
				return charset.name();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return defaultCharset;
	}
}
