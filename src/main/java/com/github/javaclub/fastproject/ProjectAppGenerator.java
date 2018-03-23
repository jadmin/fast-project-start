package com.github.javaclub.fastproject;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.core.Systems;
import com.github.javaclub.sword.util.FileUtil;
import com.github.javaclub.sword.util.PropUtil;


/**
 * 工程应用自动生成器
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ProjectAppGenerator.java 2018年3月23日 16:02:18 Exp $
 */
public class ProjectAppGenerator {
	
	static String templateDirName; // 模版项目目录名
	static String projectAppName; // 要生成的应用名
	
	static String packagePrefix;
	static String packageDirPathPrefix;
	static String packageDirPathPrefixWithoutAppName;
	
	static {
		Properties server = PropUtil.getProperties("server.properties", ProjectAppGenerator.class);
		
		templateDirName = server.getProperty("project.template");
		projectAppName = server.getProperty("project.app.name");
		packagePrefix = Strings.concat(server.getProperty("project.app.package.prefix"), ".", projectAppName);
		packageDirPathPrefix = "/" + packagePrefix.replace(".", "/");
		packageDirPathPrefixWithoutAppName = "/" + server.getProperty("project.app.package.prefix").replace(".", "/");
	}
	
	public static void main(String[] args) throws IOException {
		String path = FileUtil.getClasspathFile(".").getParentFile().getParent();
		Systems.out("basePath={}", path);
		Systems.out("packagePrefix={}", packagePrefix);
		Systems.out("packageDirPathPrefix={}", packageDirPathPrefix);
		
		File src = new File(path + "/template/" + templateDirName);
		File dest = new File(path + "/work/" + projectAppName);
		
		copyDir(src, dest);
	}
	
	static void copyDir(File src, File dest) throws IOException {
		doCopyDir(src, dest);
	}

	static void doCopyDir(File src, File dest) throws IOException {
//		Systems.out("dest={}", dest);
		dest = ensureReady(dest, src.isDirectory());
		if (src.isDirectory() && !Strings.equals(src.getName(), ".settings")) {
			File[] entries = src.listFiles();
			if (entries == null) {
				throw new IOException("Could not list files in directory: " + src);
			}
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				doCopyDir(file, new File(dest, file.getName()));
			}
		} else if (src.isFile()) {
			String filename = src.getName();
			if(!Strings.equals(".DS_Store", filename) 
					&& !Strings.equals(".classpath", filename)
					&& !Strings.equals(".project", filename)) {
				copyFile(src, dest);
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
		if(path.indexOf("/com/xxx/project") > 0 
				|| path.indexOf(packageDirPathPrefixWithoutAppName + "/project") > 0
				|| path.indexOf("/com/xxx") > 0
				|| path.indexOf("/project-") > 0
				|| path.endsWith("/java/com") || path.endsWith("/java/com/")) {
			path = path.replace("/project-", "/" + projectAppName + "-");
			path = path.replace("/com/xxx", packageDirPathPrefixWithoutAppName);
			path = path.replace("/com/xxx/project", packageDirPathPrefix);
			path = path.replace(packageDirPathPrefixWithoutAppName + "/project", packageDirPathPrefix);
			newFile = new File(path);
			if((path.endsWith("/java/com") || path.endsWith("/java/com/")) 
					&& !packageDirPathPrefixWithoutAppName.startsWith("/com")) {
				needCreateDir = false;
			}
		}
		
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

	static void copyFile(File src, File dest) throws IOException {
		String text = FileUtil.readAsString(src, "UTF-8");
		
		text = Strings.replace(text, "#{appName}#", projectAppName);
		text = Strings.replace(text, "#{packagePrefix}#", packagePrefix);
		
		FileUtil.writeText(dest.getAbsolutePath(), text, "UTF-8");
		
		Systems.out("OK \t dest={}", dest.getAbsolutePath());
		
	}
	
	
}
