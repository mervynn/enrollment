package com.gh.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import org.apache.log4j.Logger;

import com.swetake.util.Qrcode;

public class QRCodeUtils {
	private static final Logger logger = Logger.getLogger(QRCodeUtils.class);

	/**
	 * 编码字符串内容到目标File对象中
	 * 
	 * @param encodeddata
	 * @param destFile
	 * @throws IOException
	 */
	public static void qrCodeEncode(String encodeddata, File destFile)
			throws IOException {
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		byte[] d = encodeddata.getBytes("GBK");
		BufferedImage bi = new BufferedImage(139, 139,
				BufferedImage.TYPE_INT_RGB);
		// createGraphics
		Graphics2D g = bi.createGraphics();
		// set background
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, 139, 139);
		g.setColor(Color.BLACK);

		if (d.length > 0 && d.length < 123) {
			boolean[][] b = qrcode.calQrcode(d);
			for (int i = 0; i < b.length; i++) {
				for (int j = 0; j < b.length; j++) {
					if (b[j][i]) {
						g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		}

		g.dispose();
		bi.flush();

		ImageIO.write(bi, "png", destFile);
		logger.info("Input Encoded data is：" + encodeddata);
	}

	/**
	 * 解析二维码，返回解析内容
	 * 
	 * @param imageFile
	 * @return
	 */
	public static String qrCodeDecode(File imageFile) {
		String decodedData = null;
		QRCodeDecoder decoder = new QRCodeDecoder();
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			logger.error("Error: " + e.getMessage());
		}

		try {
			decodedData = new String(decoder.decode(new J2SEImage(image)), "GBK");
			logger.info("Output Decoded Data is：" + decodedData);
		} catch (DecodingFailedException dfe) {
			logger.info("Error: " + dfe.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return decodedData;
	}

	/**
	 * 解析二维码，返回解析内容(通过BufferedImage)
	 * 
	 * @param imageFile
	 * @return
	 */
	public static String qrCodeDecode(BufferedImage bi) {
//		logger.info("jvm监听:maxMemory:" + Runtime.getRuntime().maxMemory()/1048576 
//				+ "M,totalMemory:" + Runtime.getRuntime().totalMemory()/1048576
//				+ "M,freeMemory:" + Runtime.getRuntime().freeMemory()/1048576 + "M");
		String decodedData = null;
		QRCodeDecoder decoder = new QRCodeDecoder();
		try {
			byte[] bytes = decoder.decode(new J2SEImage(bi));
			decodedData = new String(bytes, "utf-8");
			bytes = null;
			//logger.info("Output Decoded Data is：" + decodedData);
		} catch (Exception e) {
			// 非二维码都会异常,该处不做任何处理,不打log
			//logger.error("解析二维码:");
		}finally{
			decoder = null;
		}
		return decodedData;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String FilePath = "E:\\Demo\\bt_yunyu_3x_on.png";
		File qrFile = new File(FilePath);
		// 编码
		int i = 0;
		while(true){
			try {
				BufferedImage image = ImageIO.read(qrFile);
				// 解码
				String reText = QRCodeUtils.qrCodeDecode(image);
				logger.info(i++ +reText);
			} catch (IOException e) {
				logger.error(e);
			}
			
		}
	}
}

class J2SEImage implements QRCodeImage {
	BufferedImage image;

	public J2SEImage(BufferedImage image) {
		this.image = image;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}
}