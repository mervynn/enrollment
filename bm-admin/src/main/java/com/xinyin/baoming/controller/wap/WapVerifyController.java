package com.xinyin.baoming.controller.wap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinyin.baoming.util.CommonUtils;
import com.xinyin.baoming.util.constant.Constant;

@Controller
@RequestMapping(value = "/wap")
public class WapVerifyController {

	//private static final Logger logger = Logger.getLogger(WapVerifyController.class);

	/**
	 * 返回验证码图片
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @throws IOException
	 */
	@RequestMapping({ "/verify" })
	public void verify(HttpServletRequest request,
			HttpServletResponse response, String name) throws IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		HttpSession session = request.getSession(false);

		int width = 73;
		int height = 27;
		BufferedImage image = new BufferedImage(width, height, 1);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("Times New Roman", 0, 24));

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = CommonUtils.randomInt(1).toUpperCase();
			sRand = sRand + rand;

			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 24);
		}

		if (CommonUtils.null2String(name).equals(""))
			session.setAttribute(Constant.WAP_VERIFY_CODE_KEY, sRand);
		else {
			session.setAttribute(name, sRand);
		}

		g.dispose();
		ServletOutputStream responseOutputStream = response.getOutputStream();
		ImageIO.write(image, "JPEG", responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
