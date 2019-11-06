package com.peter.miaosha.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;


/**
 * 验证码图片
 * 
 * @author 0092397
 *
 */
@Component
public class CaptchaImage {

	/**
	 * 生成图片
	 * @param code 验证码
	 * @return
	 */
	public BufferedImage generatePhote(String code) {
		return generatePhote(code, getDefaultOpt());
	}

	/**
	 * 生成验证码图片
	 * 
	 * @param code
	 *            验证码
	 * @param opt
	 *            图片参数
	 * @return
	 */
	public BufferedImage generatePhote(String code, ImageOpt opt) {
		int fontHeight = opt.height - 10;

		BufferedImage buffImg = new BufferedImage(opt.width, opt.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, opt.width, opt.height);
		// 边框设置为黑色
		g.setColor(Color.black);
		g.drawRect(0, 0, opt.width - 1, opt.height - 1);
		// 创建字体
		Font font = ImageFont.getFont(fontHeight);
		g.setFont(font);
		
		// 填充随机线段
		randomLine(g, opt);

		// 填充验证码
		randomCode(opt, g, code);

		return buffImg;
	}

	/**
	 * 对code 展示 随机处理
	 * @param opt
	 * @param g
	 * @param code
	 */
	private void randomCode(ImageOpt opt, Graphics2D g, String code) {
		int codeWidth = opt.width / (code.length() + 2);
		Random random = new Random();
		char[] codeSequence = code.toCharArray();
		// 随机产生验证码字符
		for (int i = 0; i < code.length(); i++) {
			String strRand = String.valueOf(codeSequence[i]);
			// 设置字体颜色
			g.setColor(getRandomColor());
			// 设置字体位置
			int codeY = random.nextInt(opt.height / 2) + opt.height / 2;
			int codeX = (i + 1) * codeWidth;
			g.drawString(strRand, codeX, codeY);
		}
	}

	/**
	 * 行数随机
	 * 
	 * @param g
	 */
	private void randomLine(Graphics2D g, ImageOpt opt) {
		Random random = new Random();
		for (int i = 0; i < opt.lineCount; i++) {
			int xs = random.nextInt(opt.width);
			int ys = random.nextInt(opt.height);
			int xe = xs + random.nextInt(opt.width / 8);
			int ye = ys + random.nextInt(opt.height / 8);
			g.setColor(getRandomColor());
			g.drawLine(xs, ys, xe, ye);
		}
	}

	/** 获取随机颜色 */
	private Color getRandomColor() {
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r, g, b);
	}

	/**
	 * 获取默认配置
	 * 
	 * @return
	 */
	private ImageOpt getDefaultOpt() {
		return new ImageOpt(170, 60, 50);
	}

	/**
	 * 
	 * @author 0092397
	 *
	 */
	public static class ImageOpt {
		int width;
		int height;
		int lineCount;

		public ImageOpt(int width, int height, int lineCount) {
			this.width = width;
			this.height = height;
			this.lineCount = lineCount;
		}
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		CaptchaImage p = new CaptchaImage();
		BufferedImage buffImg = p.generatePhote("ABCD");
		OutputStream os = new FileOutputStream("d:/1234.png");
		ImageIO.write(buffImg, "png", os);
		os.close();
	}
}
