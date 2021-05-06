/*
 * @(#)Generator.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.dao;

import com.github.javaclub.jorm.Jorm;
import com.github.javaclub.sword.util.code.CrudCodeGenerator;

/**
 * Generator
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: Generator.java ${currentTime} Exp $
 */
public class Generator {

	public static void main(String[] args) throws Exception {

		String[] entityNames = new String[] {
			"MyTest"
		};
		generateOneDomain(entityNames);
	}
	
	/**
	 * 单个实体DAO层生成，overwritten=true慎用!!
	 */
	static void generateOneDomain(String[] entityNames) throws Exception {
		CrudCodeGenerator ccg = new CrudCodeGenerator();
		ccg.setConnection(Jorm.getConnection());
		
		for (String entity : entityNames) {
			ccg.setGenerateController(false).setGenerateIdFieldSet(false)
						.generateOneEntityDomain(entity, true);
		}
	}
	

}
