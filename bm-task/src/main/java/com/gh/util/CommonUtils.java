package com.gh.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.multipart.MultipartFile;

import com.gh.util.constant.Constant;
import com.gh.util.constant.Message;

public class CommonUtils {
	
	private static final Logger logger = Logger.getLogger(CommonUtils.class);
	public static Object obj;
	public static Method method;
	
	/**
	 * 写文件到当前Servlet容器中,并返回下载路径
	 * @param file
	 * @param folderParam
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String writeFile(MultipartFile mulFile,String folderParam,HttpServletRequest request) throws Exception {
		try{
			String realPath = request.getSession().getServletContext().getRealPath("/");
			if (realPath.endsWith("/") || realPath.endsWith("\\")) {
				realPath = realPath.substring(0, realPath.length()-1);
			}
			int index = realPath.lastIndexOf("\\");
			if (index == -1) {
				index = realPath.lastIndexOf("/");
			}
			realPath = realPath.substring(0, index)+File.separator+folderParam;
			File folder = new File(realPath) ;
			// 创建文件夹
			if(!folder.exists() || !folder.isDirectory()) {
				folder.mkdirs();
			}

			File file = new File(folder,mulFile.getOriginalFilename());
			// 在文件夹下创建文件
			if(!file.exists()){
				file.createNewFile();
			}
			mulFile.transferTo(file);
			String webFilePath = Constant.DOMAIN_NAME + File.separator + folderParam + File.separator
					+ mulFile.getOriginalFilename();
		    logger.info("文件地址:" + webFilePath);
		    return webFilePath;
		}catch(Exception e){
			logger.error(Message.FILE_UPLOAD_FAILD, e);
			throw e;
		}
	}
	
	/**
	 * 通过文件内容写文件到服务器(生成文件攻略html文件)
	 * 
	 * @param req
	 * @param folderParam 文件夹名称
	 * @param content 文件内容
	 * @return
	 * @throws Exception
	 */
	public static String writeFileByContent(HttpServletRequest req, String folderParam, String content) throws Exception {
		FileWriter fw = null;
		try{
			// 项目文件路径
			String realPath = req.getSession().getServletContext().getRealPath("/");
			if (realPath.endsWith("/") || realPath.endsWith("\\")) {
				realPath = realPath.substring(0, realPath.length()-1);
			}
			int index = realPath.lastIndexOf("\\");
			if (index == -1) {
				index = realPath.lastIndexOf("/");
			}
			// 文件夹路径
			String folderPath = realPath.substring(0, index)+File.separator + folderParam;
			File folder = new File(folderPath) ;
			// 创建文件夹
			if(!folder.exists() || !folder.isDirectory()) {
				folder.mkdirs();
			}
			// 创建文件
			String fileName = nextId() + ".html";
			fw = new FileWriter(folderPath + File.separator + fileName);
			fw.write(content);
			String webFilePath = Constant.DOMAIN_NAME + File.separator + folderParam + File.separator + fileName;
		    logger.info("文件地址:" + webFilePath);
		    return webFilePath;
		}catch(Exception e){
			logger.error(Message.FILE_UPLOAD_FAILD, e);
			throw e;
		}finally{
			if(fw != null){
				fw.close();
			}
		}
	}
	
	/**
	 * 通过uri获取目标html的精简html内容
	 * @param uri
	 * @return
	 * @throws IOException 
	 */
	public static String getMajorHtmlByUri(String uri) throws IOException{
		String htmlText = StringUtils.EMPTY;
		if(StringUtils.isBlank(uri)){
			return StringUtils.EMPTY;
		}
		Document doc = Jsoup.connect(uri).timeout(1000).get();
		// 设定utf-8字符集
		doc.select("meta[http-equiv=Content-Type]").attr("content", "text/html; charset=utf-8");
		// 如果来自百度图库(hiphotos.baidu.com) 则转交服务器代为处理
		for(Element foo : doc.getElementsByTag("img")){
			String src = foo.attr("src");
			if(StringUtils.isNotEmpty(src) && src.indexOf("hiphotos.baidu.com") != -1 && src.indexOf("game-hot.tv") == -1){
				foo.attr("src", Constant.DOMAIN_NAME + "/file/read.shtml?url=" + src);
			}
		}
		String html = doc.html();
		Readability ra = new Readability(html);
		ra.init();
		// 植入手机端通用css样式
		String temHtml = ra.html();
		// 正则遍历图片 判断若为二维码 则忽略该图片		
		Pattern pattern = Pattern.compile("<p.*?<img.*?src=\"(.*?)\"", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(temHtml);
		// 记录图片数量
		int pictureAmount = 0;
		while (matcher.find()) {
			pictureAmount++;
			String url = matcher.group(1).trim();
			if(url.startsWith("http")){
				if(!isNotQrcodePic(url)){
					temHtml = temHtml.replace(url, StringUtils.EMPTY).replaceAll("<img.*?src=\"\".*?/>", StringUtils.EMPTY);
				}
			}
		}
		
		// 如果没有图片则忽略该条新闻
		if(pictureAmount == 0){
			return StringUtils.EMPTY;
		}
		int pos = temHtml.indexOf("</head>");
		if(StringUtils.isNotEmpty(temHtml) && pos != -1){
			String cssUri = " <link href=\"" + Constant.COMMON_MOBILE_CSS + "\" rel=\"stylesheet\">"
					+ " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"
					+ " <script src=\""+ Constant.JQUERY_2_1_4_MIN_JS +"\"></script>"
					+ " <script src=\""+ Constant.JQUERY_LAZYLOAD_JS +"\"></script>"
					+ " <script src=\""+ Constant.JQUERY_IMGAUTOSIZE_JS +"\"></script>"
					+ " <script type=\"text/javascript\">"
					+ " $(function(){"
					+ 	" $(\"img\").lazyload({"
					+ 		" placeholder : \""+ Constant.DEFAULT_IMAGE +"\","
					+ 		" effect : \"fadeIn\""
					+ 	" });"
					+ 	" $(\"body\").imgAutoSize();"
					+ " });"
					+ " </script>";
			htmlText = temHtml.substring(0,pos) + cssUri + temHtml.subSequence(pos, temHtml.length());
		}
		return htmlText;
	}
	
	/**
	 * 通过主项目的序列号生成方法拿到同步的序列号
	 */
	public static String nextId(){
		try {
			return String.valueOf(method.invoke(obj));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("序列号生成异常!", e);
		}
		return null;
	}
	
	/**
	 * 读取远程url图片,得到宽高(因为只是计划任务项目,所以该方法配合二维码类,判断图片的合法性)
	 * 
	 * @param imgurl
	 * @return
	 */
	public static int[] returnImgWH(String imgurl) {
		int[] a = new int[2];
		BufferedImage bi = null;
		InputStream is = null;
		BufferedInputStream in = null;
		boolean imgwrong = false;
		try {
			// 读取图片
			URL url = new URL(imgurl);
			is = url.openStream();
            in = new BufferedInputStream(is); 
			bi = ImageIO.read(in);
			// 判断文件图片是否能正常显示,有些图片编码不正确
			bi.getType();
			// 判断图片是否为二维码 如果是的话 忽略该图
			if(QRCodeUtils.qrCodeDecode(bi) == null){
				imgwrong = true;
			}
			if (imgwrong) {
				a[0] = bi.getWidth(); // 获得 宽度
				a[1] = bi.getHeight(); // 获得 高度
			} else {
				a[0] = 0;
				a[1] = 0;
			}
		} catch (Exception ex) {
			imgwrong = false;
			logger.error("读取图片异常", ex);
		} finally {
			try {
				bi.flush();
				in.close();
				is.close();
			} catch (Exception e) {
				logger.error("流关闭异常");
			}
		}
		return a;
	}
	
	/**
	 * 读取远程url图片,判断是否是二维码,
	 * 
	 * @param imgurl
	 * 
	 * @return 如果是图片并且不是二维码   或者路径 不以http开头(即相对路径,暂不做处理,默认为正常图片)  
	 *         返回true
	 */
	public static boolean isNotQrcodePic(String imgurl) {
		BufferedImage bi = null;
		InputStream is = null;
		BufferedInputStream in = null;
		boolean imgwrong = false;
		try {
			if(!imgurl.startsWith("http")){
				return true;
			}
			// 读取图片
			URL url = new URL(imgurl);
			is = url.openStream();
            in = new BufferedInputStream(is); 
         	bi = ImageIO.read(in);
				// 判断文件图片是否能正常显示,有些图片编码不正确
				bi.getType();
				// 判断图片是否为二维码 如果是的话 忽略该图
				if(QRCodeUtils.qrCodeDecode(bi) == null){
					imgwrong = true;
				}
		} catch (Exception ex) {
			imgwrong = false;
			logger.error("二维码判断,读取远程图片:"+imgurl+"发生异常");
		} finally {
			try {
				bi.flush();
				in.close();
				is.close();
			} catch (Exception e) {
				logger.error("流关闭异常");
			}
		}
		return imgwrong;
	}
	
}
