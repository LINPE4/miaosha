package com.peter.miaosha.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {
	
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}
	
	public static void main(String[] args) {
		// 相对路径
		Path dir = Paths.get("chenmo");

		// 输出 dir 的绝对路径
		System.out.println(dir.toAbsolutePath()); // 输出：D:\program\java.git\java_demo\chenmo

		if (Files.notExists(dir)) {
			try {
				// 如果目录不存在，则创建目录
				Files.createDirectory(dir);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		// 这时候 chenmo.txt 文件并未创建
		// 通过 resolve 方法把 dir 和 chenmo.txt 链接起来
		Path file = dir.resolve("chenmo.txt");

		// 输出 file 的绝对路径
		System.out.println(file.toAbsolutePath()); // 输出：D:\program\java.git\java_demo\chenmo\chenmo.txt

		if (Files.notExists(file)) {
			try {
				// 如果文件不存在，则创建文件
				Files.createFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
			for (Path entry : stream) {
				System.out.println(entry.getFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
