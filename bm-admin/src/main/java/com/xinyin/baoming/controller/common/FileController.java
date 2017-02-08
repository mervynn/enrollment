package com.xinyin.baoming.controller.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyin.baoming.util.CommonUtils;
import com.xinyin.baoming.util.Tools;
import com.xinyin.baoming.util.constant.Constant;

@Controller
@RequestMapping(value = "/file")
public class FileController {

	private static final Logger logger = Logger.getLogger(FileController.class);
	
	/**
	 * 上传图片接口(流方式)
	 * 
	 * @param reqHeader
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/base64/upload", method = RequestMethod.POST)
	@ResponseBody
	public String base64Upload(HttpServletRequest request, String imageData) {
		//logger.info("上传图片api开始");
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMdd");
		Date date = new Date();
		String formatDate = sdf.format(date);
		String path = StringUtils.EMPTY;
		String relativeTempPath = File.separator + Constant.UPLOAD_FILE_PATH + File.separator + formatDate;
	    try {
	    	if(StringUtils.isNotEmpty(imageData) && imageData.startsWith(Constant.BASE64_PREFIX)){
	    		String realPath = request.getSession().getServletContext().getRealPath("/");
	    		if (realPath.endsWith("/") || realPath.endsWith("\\")) {
	    			realPath = realPath.substring(0, realPath.length()-1);
	    		}
	    		int index = realPath.lastIndexOf("\\");
	    		if (index == -1) {
	    			index = realPath.lastIndexOf("/");
	    		}
	    		realPath = realPath.substring(0, index) + relativeTempPath;
	    		File file = new File(realPath) ;
	    		if(!file.exists()) {
	    			file.mkdirs() ;
	    		}
	    		String filename = date.getTime() + Tools.getRandomNum(1000, 9999) + ".jpg";
	    		realPath = realPath + File.separator + filename;
	    		CommonUtils.GenerateImage(realPath, imageData.replaceFirst(Constant.BASE64_PREFIX, StringUtils.EMPTY));
	    		path = Constant.DOMAIN_NAME + relativeTempPath + File.separator + filename;
	    	}
		} catch (Exception e) {
			logger.error(Constant.SYSTEM_EXCEPTION, e);
		}
	    //logger.info("上传结束，图片地址:" + path);
	    return path;
	}

	/**
	 * 上传图片接口(流方式)
	 * 
	 * @param reqHeader
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/stream/upload", method = RequestMethod.POST)
	@ResponseBody
	public String streamUpload(@RequestHeader Map<String, String> reqHeader,HttpServletRequest request) throws IOException {
		logger.info("上传图片api开始");
		String folder = reqHeader.get("folder") ;
		if (folder==null || folder.equals("")) {
			folder = "img";
		}
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if (realPath.endsWith("/") || realPath.endsWith("\\")) {
			realPath = realPath.substring(0, realPath.length()-1);
		}
		int index = realPath.lastIndexOf("\\");
		if (index == -1) {
			index = realPath.lastIndexOf("/");
		}
		realPath = realPath.substring(0, index)+File.separator+folder;
		
		File file = new File(realPath) ;
		if(!file.exists()) {
			file.mkdirs() ;
		}
		
		String filename = new Date().getTime()+Tools.getRandomNum(1000, 9999)+reqHeader.get("filename");
		File cacheFile  = new File(realPath,filename);
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		// 压缩图片 TODO
		//		BufferedImage bi = ImageIO.read(bis);
		int len = 0;
		byte[] bt = new byte[1024];
		OutputStream out = new FileOutputStream(cacheFile);
	    while ((len = bis.read(bt)) > 0){
	    	out.write(bt, 0, len);
    	}
	    try {
		     if(bis != null)
		    	 bis.close();
		     if (out != null)
		    	 out.close();
		} catch (IOException e) {
			logger.error(Constant.SYSTEM_EXCEPTION, e);
		}
	    String path = Constant.DOMAIN_NAME + File.separator + folder + File.separator + filename;
	    logger.info("上传结束，图片地址:" + path);
	    return path;
	}
	
	/**
	 * 读取远程图片
	 * 
	 * @param reqHeader
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/read")
	public void readPicture(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String url = req.getParameter("url");
		CommonUtils.readPicture(url, res);
	}
}
