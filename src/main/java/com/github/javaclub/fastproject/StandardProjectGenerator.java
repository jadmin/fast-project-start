/*
 * @(#)StandardProjectGenerator.java	2021-2-3
 *
 * Copyright (c) 2021. All Rights Reserved.
 *
 */

package com.github.javaclub.fastproject;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.github.javaclub.fastproject.utils.GeneratorUtils;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.DateUtil;
import com.github.javaclub.sword.util.FileUtil;
import com.github.javaclub.sword.util.PropUtil;

/**
 * StandardProjectGenerator
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: StandardProjectGenerator.java 2021-2-3 15:50:55 Exp $
 */
public class StandardProjectGenerator {
	
	static final String APP_STANDARD = "admin-console";
	
	static final String GROUP_ID = "groupId";
	static final String APP_NAME = "appName";
	static final String PACKAGE_PREFIX = "packagePrefix";

	static Map<String, String> paramsMap = new HashMap<String, String>();
	
	static {
		Properties server = PropUtil.getProperties("server.properties", StandardProjectGenerator.class);
		paramsMap = toMap(server);
		
		paramsMap.put("${date}", DateUtil.currentDate());
		paramsMap.put("${year}", DateUtil.get(new Date(), DateUtil.YEAR) + "");
		paramsMap.put("${currentTime}", DateUtil.currentTimestamp());
	}
	
	public static void main(String[] args) throws Exception {
		String path = FileUtil.getClasspathFile(".").getParentFile().getParent();
		String tplRootPath = path + "/template/" + APP_STANDARD;
		String destRootPath = path + "/work/" + paramsMap.get(keyFormat(APP_NAME));
		
		copyRootFiles(new String[] {
			"pom.xml", ".gitignore", 
			"package-dev.sh", "bootstart-local.sh", "bootone.sh"
		}, tplRootPath, destRootPath);
		
		String[] modules = new String[] {
			"api", "common", "dao", "manager", "service", "web"
		};
		
		for (int i = 0; i < modules.length; i++) {
			copyModule(tplRootPath, destRootPath, modules[i]);
		}
	}
	
	static void copyModule(String tplRootPath, String destRootPath, String moduleName) throws Exception {
		String appName = paramsMap.get(keyFormat(APP_NAME));
		String module = Strings.concat(appName, "-", moduleName);
		String modulePath = destRootPath + "/" + module;
		FileUtil.createDir(modulePath);
		
		// module pom.xml
		copyRootFiles(new String[] { "pom.xml" }, tplRootPath + "/project-" + moduleName, modulePath);
		
		String tplBasePackage = tplRootPath + "/project-" + moduleName + "/src";
		String tplResources = tplRootPath + "/project-" + moduleName + "/resources";
		File tplBasePackageDir = new File(tplBasePackage);
		File tplResourcesDir = new File(tplResources);
		
		String basePackage = modulePath + "/src/main/java/" + paramsMap.get(keyFormat(PACKAGE_PREFIX)).replace(".", "/");
		String resources = modulePath + "/src/main/resources";
		
		// src
		if(tplBasePackageDir.exists() && tplBasePackageDir.isDirectory()) {
			GeneratorUtils.copyDir(tplBasePackageDir, new File(basePackage), paramsMap);
		}
		
		// resources
		if(tplResourcesDir.exists() && tplResourcesDir.isDirectory()) {
			GeneratorUtils.copyDir(tplResourcesDir, new File(resources), paramsMap);
		}
		
		// 测试目录
		String testSrcPackage = tplRootPath + "/project-" + moduleName + "/test-src";
		String testResources = tplRootPath + "/project-" + moduleName + "/test-resources";
		File testSrcPackageDir = new File(testSrcPackage);
		File testResourcesDir = new File(testResources);
		
		String testPackageDest = modulePath + "/src/test/java/" + paramsMap.get(keyFormat(PACKAGE_PREFIX)).replace(".", "/");
		String testResourcesDest = modulePath + "/src/test/resources";
		
		// test/src
		if(testSrcPackageDir.exists() && testSrcPackageDir.isDirectory()) {
			GeneratorUtils.copyDir(testSrcPackageDir, new File(testPackageDest), paramsMap);
		}
		
		// test/resources
		if(testResourcesDir.exists() && testResourcesDir.isDirectory()) {
			GeneratorUtils.copyDir(testResourcesDir, new File(testResourcesDest), paramsMap);
		}
		
	}
	
	static void copyRootFiles(String[] filenames, String tplRootPath, String destRootPath) throws Exception {
		for (int i = 0; i < filenames.length; i++) {
			GeneratorUtils.copyFile(new File(tplRootPath + "/" + filenames[i]), new File(destRootPath + "/" + filenames[i]), paramsMap);
		}
	}
	
	static String keyFormat(String key) {
		return Strings.concat("${", key, "}");
	}
	
	static Map<String, String> toMap(Properties p) {
		Map<String, String> map = new HashMap<String, String>();
		
		for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements(); ) {
			  String key =  (String) e.nextElement() ;
		      String keyFormat = keyFormat(key);
		      map.put(keyFormat, p.getProperty(key));
		}
		
		return map;
	}
	
	
}
