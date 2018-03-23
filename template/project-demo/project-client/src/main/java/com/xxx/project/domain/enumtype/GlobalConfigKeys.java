/*
 * @(#)GlobalConfigKeys.java	2017年9月19日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.domain.enumtype;

/**
 * GlobalConfigKeys ConfigCenter配置项global.config.values的子key集
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: GlobalConfigKeys.java 2017年9月19日 15:37:14 Exp $
 */
public enum GlobalConfigKeys {
	
	/**
	 * 微信公众号对应的测试集团ID
	 */
	WX_GZH_TEST_ORG_ID("wx.gzh.test.orgId"),
	
	/**
	 * 联华卡绑定测试时需要绑定的集团ID 
	 */
	LIANHUA_CARD_SYNC_ORGID("lianhua.card.sync.orgId"),
	
	/**
	 * 线上积分是否往联华卡的积分同步 
	 */
	LIANHUA_CARD_POINTS_SYNC_SWITCH("lianhua.card.points.sync.enabled"),
	
	/**
	 * 联华线上积分首次同步，是否把以往累计的积分初始化进入联华卡内（默认不用，慎重开启！！！）
	 */
	LIANHUA_CARD_POINTS_FIRST_SYNC_INIT("lianhua.card.points.1st.sync.init"),
	
	/**
	 * 有权限创建会员的KA集团ID(多个以,分隔的)
	 */
	HAS_OPEN_CARD_KA("has_open_card_ka"),
	
	LIANHUA_CRM_API_USERNAME("lianhua.crm.api.username"), // 联华知而行CRM Api用户
	LIANHUA_CRM_API_PASSWORD("lianhua.crm.api.password"), // 联华知而行CRM Api用户密码
	
	LIANHUA_API_CARD("lianhua.api.card"), // 联华卡绑定Api开关

	;
	
	private GlobalConfigKeys(String key) {
		this.key = key;
	}
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
