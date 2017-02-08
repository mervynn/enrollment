package com.xinyin.baoming.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.multipart.MultipartFile;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;
import com.xinyin.baoming.util.constant.Constant;
import com.xinyin.baoming.util.constant.Message;

public class CommonUtils {

	private static final Logger logger = Logger.getLogger(CommonUtils.class);

	/**
	 * 写文件到当前Servlet容器中,并返回下载路径
	 * 
	 * @param file
	 * @param folderParam
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String writeFile(MultipartFile mulFile, String folderParam,
			HttpServletRequest request, String name) throws Exception {
		try {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/");
			String newFileName = StringUtils.EMPTY;
			if (StringUtils.isNotEmpty(name)) {
				String suffix = mulFile.getOriginalFilename().substring(
						mulFile.getOriginalFilename().lastIndexOf("."));
				newFileName = name + suffix;
			} else {
				newFileName = mulFile.getOriginalFilename();
			}
			if (realPath.endsWith("/") || realPath.endsWith("\\")) {
				realPath = realPath.substring(0, realPath.length() - 1);
			}
			int index = realPath.lastIndexOf("\\");
			if (index == -1) {
				index = realPath.lastIndexOf("/");
			}
			realPath = realPath.substring(0, index) + File.separator
					+ folderParam;
			File folder = new File(realPath);
			// 创建文件夹
			if (!folder.exists() || !folder.isDirectory()) {
				folder.mkdirs();
			}

			File file = new File(folder, newFileName);
			// 在文件夹下创建文件
			if (!file.exists()) {
				file.createNewFile();
			}
			mulFile.transferTo(file);
			String webFilePath = Constant.DOMAIN_NAME + File.separator
					+ folderParam + File.separator + newFileName;
			logger.info("文件地址:" + webFilePath);
			return webFilePath;
		} catch (Exception e) {
			logger.error(Message.FILE_UPLOAD_FAILD, e);
			throw e;
		}
	}

	/**
	 * 通过文件内容写文件到服务器(生成文件Html5文件)
	 * 
	 * @param req
	 * @param folderParam
	 *            文件夹名称
	 * @param content
	 *            文件内容
	 * @return
	 * @throws Exception
	 */
	public static String writeFileByContent(HttpServletRequest req,
			String folderParam, String content) throws Exception {
		FileWriter fw = null;
		try {
			// 项目文件路径
			String realPath = req.getSession().getServletContext()
					.getRealPath("/");
			if (realPath.endsWith("/") || realPath.endsWith("\\")) {
				realPath = realPath.substring(0, realPath.length() - 1);
			}
			int index = realPath.lastIndexOf("\\");
			if (index == -1) {
				index = realPath.lastIndexOf("/");
			}
			// 文件夹路径
			String folderPath = realPath.substring(0, index) + File.separator
					+ folderParam;
			File folder = new File(folderPath);
			// 创建文件夹
			if (!folder.exists() || !folder.isDirectory()) {
				folder.mkdirs();
			}
			// 创建文件
			String fileName = Sequence.nextId() + ".html";
			fw = new FileWriter(folderPath + File.separator + fileName);
			fw.write(content);
			String webFilePath = Constant.DOMAIN_NAME + Constant.WEB_SEPARATOR
					+ folderParam.replace(File.separator, Constant.WEB_SEPARATOR) + Constant.WEB_SEPARATOR + fileName;
			logger.info("文件地址:" + webFilePath);
			return webFilePath;
		} catch (Exception e) {
			logger.error(Message.FILE_UPLOAD_FAILD, e);
			throw e;
		} finally {
			if (fw != null) {
				fw.close();
			}
		}
	}

	/**
	 * 通过uri获取目标html的精简html内容
	 * 
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static String getMajorHtmlByUri(String uri) throws IOException {
		String htmlText = StringUtils.EMPTY;
		if (StringUtils.isBlank(uri)) {
			return StringUtils.EMPTY;
		}
		Document doc = Jsoup.connect(uri).timeout(1000).get();
		// 设定utf-8字符集
		// doc.select("meta[http-equiv=Content-Type]").attr("content",
		// "text/html; charset=utf-8");
		// 如果来自百度图库(hiphotos.baidu.com) 则转交服务器代为处理
		for (Element foo : doc.getElementsByTag("img")) {
			String src = foo.attr("src");
			if (StringUtils.isNotEmpty(src)
					&& src.indexOf("hiphotos.baidu.com") != -1
					&& src.indexOf("game-hot.tv") == -1) {
				foo.attr("src", Constant.DOMAIN_NAME + "/file/read.shtml?url="
						+ src);
			}
		}
		String html = doc.html();
		Readability ra = new Readability(html);
		ra.init();
		// 植入手机端通用css样式
		String temHtml = ra.html();
		int pos = temHtml.indexOf("</head>");
		if (StringUtils.isNotEmpty(temHtml) && pos != -1) {
			String cssUri = " <link href=\""
					+ Constant.COMMON_MOBILE_CSS
					+ "\" rel=\"stylesheet\">"
					+ " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"
					+ " <script src=\"" + Constant.JQUERY_2_1_4_MIN_JS
					+ "\"></script>" + " <script src=\""
					+ Constant.JQUERY_LAZYLOAD_JS + "\"></script>"
					+ " <script src=\"" + Constant.JQUERY_IMGAUTOSIZE_JS
					+ "\"></script>" + " <script type=\"text/javascript\">"
					+ " $(function(){" + " $(\"img\").lazyload({"
					+ " placeholder : \"" + Constant.DEFAULT_IMAGE + "\","
					+ " effect : \"fadeIn\"" + " });"
					+ " $(\"body\").imgAutoSize();" + " });" + " </script>";
			htmlText = temHtml.substring(0, pos) + cssUri
					+ temHtml.subSequence(pos, temHtml.length());
		}
		return htmlText;
	}

	/**
	 * 读取远程url图片,得到宽高
	 * 
	 * @param imgurl
	 * @return
	 */
	public static int[] returnImgWH(String imgurl) {
		int[] a = new int[2];
		BufferedImage bi = null;
		boolean imgwrong = false;
		try {
			// 读取图片
			bi = ImageIO.read(new URL(imgurl));
			try {
				// 判断文件图片是否能正常显示,有些图片编码不正确
				bi.getType();
				imgwrong = true;
			} catch (Exception e) {
				imgwrong = false;
			}
		} catch (IOException ex) {
			logger.error("读取图片异常", ex);
		}
		if (imgwrong) {
			a[0] = bi.getWidth(); // 获得 宽度
			a[1] = bi.getHeight(); // 获得 高度
		} else {
			a = null;
		}
		return a;
	}

	/**
	 * 读取远程图片保存图片到本地
	 * 
	 * @param destUrl
	 */
	public static void saveToFile(String destUrl) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream("C:\\haha.gif");
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * 读取远程图片返回图片流
	 * 
	 * @param destUrl
	 * @param destUrl
	 * 
	 */
	public static void readPicture(String destUrl, HttpServletResponse res) {
		if (StringUtils.isBlank(destUrl)) {
			return;
		}
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		OutputStream os = null;
		ByteArrayOutputStream outStream = null;
		try {
			os = res.getOutputStream();
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			outStream = new ByteArrayOutputStream();
			while ((size = bis.read(buf)) != -1) {
				outStream.write(buf, 0, size);
			}
			res.setContentType("image/jpg");
			os.write(outStream.toByteArray());
			os.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				outStream.close();
				bis.close();
				httpUrl.disconnect();
				os.close();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 非空键值对 封装到Map中
	 * 
	 * @param map
	 * @param param
	 */
	public static void putinMap(Map<String, String> map, String key,
			String value) {
		if (StringUtils.isNotEmpty(value)) {
			map.put(key, value);
		}
	}

	/**
	 * 非空数据 URLDecode
	 * 
	 * @param map
	 * @param param
	 */
	public static String urldecode(String param) {
		if (StringUtils.isNotEmpty(param)) {
			try {
				return URLDecoder.decode(param, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("参数url解密异常", e);
			}
		}
		return param;
	}

	/**
	 * 
	 * @param length
	 * @return
	 */
	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = "0123456789".toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String encodePhonenumber(String s) {
		return s == null || s.length() != 11 ? "" :s.substring(0, 3) + "****" + s.substring(7, 11);
	}
	
	/**
	 * 图片转化成base64字符串  
	 * 
	 * @return
	 */
    public static String GetImageStr()  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        String imgFile = "C:\\Users\\Administrator.WIN-GRKKKEG6R2A\\Desktop\\QQ截图20160818125502.jpg";//待处理的图片  
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }
	
	/**
	 * base64字符串转化成图片  
	 * @param imgStr
	 * @return
	 */
    public static boolean GenerateImage(String imgFilePath, String imgStr)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    }  

}
