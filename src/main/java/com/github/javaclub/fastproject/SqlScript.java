/*
 * @(#)SqlScript.java	2021-8-6
 *
 * Copyright (c) 2021. All Rights Reserved.
 *
 */

package com.github.javaclub.fastproject;

import java.io.File;
import java.io.IOException;

import com.github.javaclub.sword.algorithm.crypt.MiscCryptor;
import com.github.javaclub.sword.util.FileUtil;

/**
 * SqlScript
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: SqlScript.java 2021-8-6 16:59:59 Exp $
 */
public class SqlScript {

	static final String APP_TPL = "admin-console";
	
	public static void main(String[] args) throws IOException {
		String pwd = "xxx";
		doDecrypt(pwd);
	}
	
	static void doDecrypt(String pwd) throws IOException {
		
		String classpath = FileUtil.getClasspathFile(".").getParentFile().getParent();
		String tplRootPath = classpath + "/template/" + APP_TPL;
		
		// 读取加密sql文件内容
		String content = FileUtil.readAsString(new File(tplRootPath + "/dbs.sql"), "UTF-8");
		String sqlText = MiscCryptor.decrypt(content.trim(), pwd);
		
		// 解密出可执行的sql文件 => /template/sql.sql
		String path = tplRootPath + "/sql.sql"; 
		FileUtil.writeText(path, sqlText, "UTF-8");
		
		System.out.println("OK\t" + path);
	}
	
}
