package com.peter.miaosha.tool;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字体
 * 
 * @author 0092397
 *
 */
public class ImageFont {

	static Font baseFont;

	static Logger logger = LoggerFactory.getLogger(ImageFont.class);

	/**
	 * 获取字体
	 * 
	 * @param fontHeight
	 * @return
	 */
	public static final Font getFont(int fontHeight) {
		if (baseFont == null) {
			synchronized (ImageFont.class) {
				if (baseFont == null) {
					InputStream in = ImageFont.class.getResourceAsStream("/font");
					try {
						List<byte[]> list = new ArrayList<>();
						int size = 0;
						while (true) {
							byte[] buffer = new byte[1024 * 10];
							int index = in.read(buffer);
							if (index == -1) {
								break;
							}
							byte[] des = new byte[index];
							System.arraycopy(buffer, 0, des, 0, index);
							list.add(des);
							size = size + index;
						}
						byte[] bytes = new byte[size];
						int start = 0 ;
						for (byte[] buffer : list) {
							System.arraycopy(buffer, 0, bytes, start, buffer.length);
							start = buffer.length + start;
						}
						baseFont = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(bytes));
					} catch (NumberFormatException | IOException | FontFormatException e) {
						logger.error(e.getMessage(), e);
					} finally {
						try {
							if (in != null) {
								in.close();
							}
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		}
		if (baseFont == null) {
			return new Font("Arial", Font.PLAIN, fontHeight);
		} else {
			return baseFont.deriveFont(Font.PLAIN, fontHeight);
		}
	}

}
