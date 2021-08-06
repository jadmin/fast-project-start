/*
 * @(#)BaseGenerator.java	2021-2-3
 *
 * Copyright (c) 2021. All Rights Reserved.
 *
 */

package com.github.javaclub.fastproject.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.core.Systems;
import com.github.javaclub.sword.util.FileUtil;

/**
 * BaseGenerator
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BaseGenerator.java 2021-2-3 15:31:13 Exp $
 */
public class GeneratorUtils {
	
	public static void copyFile(File src, File dest, Map<String, String> paramMap) throws IOException {
		String lowerFilename = src.getName().toLowerCase();
		if (Strings.endsWith(lowerFilename, new String[] { 
				".woff", ".woff2", ".eot", ".ttf", ".svg",
				".jpg", ".jpeg", ".png", ".gif" })) {
			FileUtil.copyFile(src, dest);
		} else {
			String text = FileUtil.readAsString(src, "UTF-8");
			if(!dest.exists()) {
				FileUtil.createFile(dest);
			}
			String content = Strings.parseTemplateText(text, paramMap);
			FileUtil.writeText(dest.getAbsolutePath(), content, "UTF-8");
		}
		
		
		Systems.out("OK \t dest=" + dest.getAbsolutePath());
	}

	public static void copyDir(File src, File dest, Map<String, String> paramMap) throws IOException {
		doCopyDir(src, dest, paramMap);
	}

	static void doCopyDir(File src, File dest, Map<String, String> paramMap) throws IOException {
		dest = ensureReady(dest, src.isDirectory());
		if (src.isDirectory() && !Strings.equals(src.getName(), ".settings")) {
			File[] entries = src.listFiles();
			if (entries == null) {
				throw new IOException("Could not list files in directory: " + src);
			}
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				doCopyDir(file, new File(dest, file.getName()), paramMap);
			}
		} else if (src.isFile()) {
			String filename = src.getName();
			if(!Strings.equals(".DS_Store", filename) 
					&& !Strings.equals(".classpath", filename)
					&& !Strings.equals(".project", filename)) {
				copyFile(src, dest, paramMap);
			}
		} else {
			// Special File handle: neither a file not a directory.
			// Simply skip it when contained in nested directory...
		}
	}
	
	static File ensureReady(File destFile, boolean isDirectory) throws IOException {
		File newFile = destFile;
		String path = destFile.getAbsolutePath();
		boolean needCreateDir = true;
		
		String filename = newFile.getName();
		if(isDirectory) {
			if(needCreateDir) {
				FileUtil.createDir(newFile);
			}
		} else {
			if(!Strings.equals(".DS_Store", filename) 
					&& !Strings.equals(".classpath", filename)
					&& !Strings.equals(".project", filename)) {
				FileUtil.createFile(newFile);
			}
		}
		
		return newFile;
	}

	
}
