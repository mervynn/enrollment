package com.xinyin.baoming.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException; 
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;


public class JsonUtil {
	
	
private static ObjectMapper objectMapper;
	private static Logger logger= Logger.getLogger(JsonUtil.class);
	
	public static String toJsonString(Object obj){
		String resp = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			resp = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * 懒惰单例模式得到ObjectMapper实例
	 * 此对象为Jackson的核心
	 */
	private static ObjectMapper getMapper(){
		if (objectMapper== null){
			objectMapper= new ObjectMapper();
			//当找不到对应的序列化器时 忽略此字段
			objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			//使Jackson JSON支持Unicode编码非ASCII字符
			CustomSerializerFactory serializerFactory= new CustomSerializerFactory();
			serializerFactory.addSpecificMapping(Date.class, new DateTimeSerializer());
			serializerFactory.addSpecificMapping(String.class, new StringUnicodeSerializer());
			
			objectMapper.setSerializerFactory(serializerFactory);
			//支持结束
		}
		return objectMapper;
	}
	
	/**
	 * 创建JSON处理器的静态方法
	 * @param content JSON字符串
	 * @return
	 */
	private static JsonParser getParser(String content){
		try{
			return getMapper().getJsonFactory().createJsonParser(content);
		}catch (IOException ioe){
			return null;
		}
	}
	
	/**
	 * 创建JSON生成器的静态方法, 使用标准输出
	 * @return
	 */
	private static JsonGenerator getGenerator(StringWriter sw){
		try{
			return getMapper().getJsonFactory().createJsonGenerator(sw);
		}catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * JSON对象序列化
	 */
	public static String toJSON(Object obj){
		StringWriter sw= new StringWriter();
		JsonGenerator jsonGen= getGenerator(sw);
		if (jsonGen== null){
			try {
				sw.close();
			} catch (IOException e) {
			}
			return null;
		}		
		try {
			//由于在getGenerator方法中指定了OutputStream为sw
			//因此调用writeObject会将数据输出到sw
			jsonGen.writeObject(obj);
			//由于采用流式输出 在输出完毕后务必清空缓冲区并关闭输出流
			jsonGen.flush();
			jsonGen.close();
			String resp = sw.toString();
//			logger.info(resp);
			return resp;
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
			logger.error("JSON生成错误" + jge.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error("JSON输入输出错误" + ioe.getMessage());
		}
		return null;		
	}
	
	/**
	 * JSON对象反序列化
	 */
	public static <T> T fromJSON(String json, Class<T> clazz) {
		try {
			JsonParser jp= getParser(json);
			return jp.readValueAs(clazz);
		} catch (JsonParseException jpe){
			logger.error(String.format("反序列化失败, 错误原因:%s", jpe.getMessage()));
		} catch (JsonMappingException jme){
			logger.error(String.format("反序列化失败, 错误原因:%s", jme.getMessage()));	
		} catch (IOException ioe){
			logger.error(String.format("反序列化失败, 错误原因:%s", ioe.getMessage()));
		}
		return null;
	}
}
