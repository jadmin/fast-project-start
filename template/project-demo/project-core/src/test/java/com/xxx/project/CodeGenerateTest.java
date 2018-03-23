/*
 * @(#)CodeGenerateTest.java	2017年10月16日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.javaclub.sword.App;
import com.github.javaclub.sword.util.code.CrudCodeGenerator;
import com.github.javaclub.cdl.client.group.SGroupDataSource;

/**
 * CodeGenerateTest
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: CodeGenerateTest.java 2017年10月16日 8:38:49 Exp $
 */
public class CodeGenerateTest {
	
	static ApplicationContext context;
	static DataSource ds;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
		ds = (SGroupDataSource) context.getBean("xxxDataSource"); // TOTO: 换成你自己的 DataSource 配置名
	}
	
	/**
	 * 根据实体和表名的映射配置生成所有的表DAO层
	 */
	@Test
	public void testAllDomain() throws Exception {
		CrudCodeGenerator ccg = new CrudCodeGenerator();
		ccg.setConnection(ds.getConnection());
		ccg.generateAllEntityDomain(false);// 非覆盖生成(已经存在的直接跳过，只生成尚未生成的)
	}

	/**
	 * 单个实体DAO层生成，overwritten=true慎用!!
	 */
	@Test
	public void testOneDomain() throws Exception {
		CrudCodeGenerator ccg = new CrudCodeGenerator();
		ccg.setConnection(ds.getConnection());
		
		String entity = "KaSdgUser";
		ccg.generateOneEntityDomain(entity, true);
	}
	
	@Test
	public void testGetPomVersion() {
		String version = App.getCurrentJarVersion();
		System.out.println(version);
	}

/*	
	@Test
	public void testInsert() throws Exception {
		MyTestDAO mytestDAO = SpringContextUtil.getBean(MyTestDAO.class);
		
		MyTestDO entity = null;
		for (int i = 0; i < 100; i++) {
			entity = new MyTestDO();
			entity.setName(Strings.random(3, 20));
			entity.setUserType(Numbers.random(100));
			entity.setBirthday(DateUtil.randomDate("1985-08-31", "2001-09-28"));
			mytestDAO.insert(entity);
		}
		
	}
	
	@Test
	public void testCount() throws Exception {
		MyTestDAO mytestDAO = SpringContextUtil.getBean(MyTestDAO.class);
		
		MyTestQuery query = new MyTestQuery();
		query.setIds(Arrays.asList(new Long[] { 11891L  }));
		
		//List<MyTestDO> row = mytestDAO.queryList(query);
		int row = mytestDAO.count(query);
		
		System.out.println(row);
		
	}
	
	@Test
	public void testQuery() throws Exception {
		MyTestDAO mytestDAO = SpringContextUtil.getBean(MyTestDAO.class);
		
		MyTestQuery query = new MyTestQuery();
		query.setIds(Arrays.asList(new Long[] { 1025L, 1028L  }));
		
		List<MyTestDO> list = mytestDAO.queryList(query);
		System.out.println(list.size());
		
		System.out.println(list);
		
	}
	
	@Test
	public void testQuery2() throws Exception {
		MyTestDAO mytestDAO = SpringContextUtil.getBean(MyTestDAO.class);
		
		MyTestQuery query = new MyTestQuery();
		query.setPageSize(108); // DB中实际只有100条记录
		query.setGmtCreateRange(new DateTimeRange(DateUtil.toDate("2017-10-19 01:12:51"), DateUtil.toDate("2017-10-19 01:12:54")));
		
		List<MyTestDO> list = mytestDAO.queryList(query);
		System.out.println(list.size());
		
		System.out.println(list);
		
	}

*/
	
}
