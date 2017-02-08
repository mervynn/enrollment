package com.gh.util;
 

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * 工具类
 * @author HeMingwei
 */
public class Tools {
	
	/**
	 * 得到排序内容
	 * @param field
	 * @param sort
	 * @return
	 */
	public static String toOrderSegment(Map<String, String> map){
		String resSeg = StringUtils.EMPTY;
		for(Map.Entry<String, String> entry : map.entrySet()){
			resSeg += toSeg(entry.getKey(),entry.getValue());
		}
		if(resSeg.startsWith(",")){
			resSeg = resSeg.replaceFirst(",", StringUtils.EMPTY);;
		}
		return resSeg;
	}
	
	/**
	 * 生成单个排序
	 * @param field
	 * @param sort
	 * @return
	 */
	public static String toSeg(String field,String sort){
		if(sort == null){
			return StringUtils.EMPTY;
		}
		return "," +("0".equals(sort)?field + ".desc":field + ".asc");
	}
	
	/**
	 * html设定行选择事件 默认居左
	 * 
	 * @param param
	 * @return
	 */
	public static String rowSelectionSet(String param){
		if(param == null)param=StringUtils.EMPTY;
		return "<td onclick='doSelection(this);'>" + cleanXSS(param) + "</td>";
	}
	
	/**
	 * 数字居右 不带有行选择事件
	 * 
	 * @param param
	 * @return
	 */
	public static String alignRight(String param){
		if(param == null)param=StringUtils.EMPTY;
		return "<td style='text-align:right;'>" + cleanXSS(param) + "</td>";
	}
	
	/**
	 * 居中 不带有行选择事件
	 * 
	 * @param param
	 * @return
	 */
	public static String alignCenter(String param){
		if(param == null)param=StringUtils.EMPTY;
		return "<td style='text-align:center;'>" + cleanXSS(param) + "</td>";
	}
	
	/**
	 * html数字居右设定、设定行选择事件
	 * @param param
	 * @return
	 */
	public static String numAlignRight(String param){
		if(param == null)param=StringUtils.EMPTY;
		return "<td style='text-align:right;' onclick='doSelection(this);'>" + cleanXSS(param) + "</td>";
	}
	
	/**
	 * 判断方法是否为空
	 * @param method
	 * @return
	 */
	public static boolean methodIsNull(String method){
		boolean flag=false;
		if(method!=null && !method.equals("")){
			flag=true;
		}
		return flag;
	}

	/**
	 * 得到随机数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getRandomNum(int start,int end){
		int temp = 0;
        try {
        	temp = new Random().nextInt(end - start);
            return temp + start;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp + start;
	}
	
	/**
	 * 不会空指针的toString
	 * @param str
	 * @return
	 */
	public static String toString(Object str){
		return str != null ? str.toString() : null;
	}
	
	/**
	 * 格式化空字段(防止前端出现null字符)
	 * 
	 * @param str
	 * @return
	 */
	public static String emptyFormat(String str){
		return str != null ? cleanXSS(str) : StringUtils.EMPTY;
	}
	
	/**
	 * 防止xss 钓鱼网站 CSRF 等(视图层展示阶段)
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanXSS(String value) {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        //value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("&", "&amp;");
        value = value.replaceAll("\"", "&quot;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        return value;
    }

}
