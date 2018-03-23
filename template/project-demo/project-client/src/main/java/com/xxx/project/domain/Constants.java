/*
 * @(#)Constants.java	2017年9月25日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.domain;

/**
 * Constants
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: Constants.java 2017年9月25日 13:47:30 Exp $
 */
public interface Constants {
	
	public class Switcher {
		public static boolean MOCK = false;
		public static boolean AUTH = true; 
	}
	
	/**
	 * 上下文扩展参数key定义
	 */
	public class ContextParamKeys {
		public static final String IS_KA_USER = "isKaUser";
		public static final String ORGANIZE_ID = "organizeId";
		public static final String USER_ACCOUNT_ID = "userAccountId";
	}
	
	/**
	 * PortraitApi 
	 */
	public class PortraitApiKey {
		/**
		 * 用户分平台占比(用户画像) 
		 */
		public static final String API_USER_IMAGE = "user_source_isp_rate";
		
		/**
		 * 用户偏爱购买的top商品 
		 * @deprecated
		 */
		public static final String API_USER_PREFER_TOP_ITEM = "sku_buy_prefer";
		
		/**
		 * 用户偏爱购买的top商品 
		 */
		public static final String API_USER_PREFER_TOP_ITEM_V2 = "sku_buy_rank_isp_top10";
		
		/**
		 * TOP商品isp选择列表 
		 */
		public static final String API_USER_PREFER_TOP_ITEM_ISP_LIST = "sku_buy_rank_isp_list";
		
		/**
		 * 用户偏爱购买的top品牌
		 */
		public static final String API_USER_PREFER_TOP_BRAND = "sku_brand_prefer";
		
		/**
		 * 用户偏爱购买(品类等) 
		 */
		public static final String API_USER_PREFER_BUY = "cat_buy_prefer_rate";
		
		/**
		 * 用户偏爱购买时间 
		 */
		public static final String API_USER_PREFER_BUY_WEEKLY_TIME = "sku_week_buy_amount_rate";


		/**
		 *重要用户价值统计
		 */
		public static final String API_USER_IMPORT_VALUABLE_STAT = "user_import_valuable_stat";

		/**
		 * 会员生命周期
		 */
		public static final String API_MEMBER_LIFE_CYCLE = "member_life_cycle_ka";

		/**
		 * 会员消费转化率
		 */
		public static final String API_MEMBER_CONSUME_TRANSFORM = "member_consume_transform_ka";
	}
	
	/**
	 * 投放页面代码
	 */
	public class DeliveryPageCode {
		/**
		 * 给新注册用户投放优惠券 
		 */
		public static final String PAGE_CODE_FOR_NEW_REG_USER = "newUserSendCouponPage";
		/**
		 * 给新绑定会员卡用户投放优惠券 
		 */
		public static final String PAGE_CODE_FOR_NEW_BINDCARD_USER = "newBindCardUserSendCouponPage";
	}
	
	/**
	 * Search AppName
	 */
	public class Search {
		public static final String KA_ORDER_SEARCH_NAME = "kaOrder"; // 订单搜索
		public static final String KA_USER_SEARCH = "ka_user_search"; // 用户搜索
	}

	public static class SearchFields {
		public static final String KA_USER_ID = "ka_user_id";
		public static final String NAME = "name";
		public static final String NICK = "nick";
		public static final String SEX = "sex";
		public static final String POINTS = "points";
		public static final String ORGANIZED_ID = "organize_id";
		public static final String FROM_CHANNEL = "from_channel";
		public static final String STATUS = "status";
		public static final String IDCARD_NO = "idcard_no";
		public static final String FIRST_SHOPPING_TIME = "first_shopping_time";
		public static final String RECENT_SHOPPING_TIME = "recent_shopping_time";
		public static final String TOTAL_SHOPPING_FEE = "total_shopping_fee";
		public static final String AVG_FEE_OF_ORDER = "avg_fee_of_order";
		public static final String TOTAL_ORDERS_NUM = "total_orders_num";
		public static final String ATTRIBUTES = "attributes";
		public static final String GMT_CREATE = "gmt_create";
		public static final String GMT_MODIFY = "gmt_modify";
		public static final String PROVINCE = "province";
		public static final String CITY = "city";
		public static final String DISTRICT = "district";
		public static final String ADDRESS = "address";
		public static final String BELONG_SHOP = "belong_shop";
		public static final String BIRTHDAY = "birthday";
		public static final String OPTIONS = "options";
		public static final String FIRST_SIGN_TIME = "first_sign_time";
		public static final String RECENT_SIGN_TIME = "recent_sign_time";
		public static final String TOTAL_SIGN_TIMES = "total_sign_times";
		public static final String CONTINUED_SIGN_TIMES = "continued_sign_times";
		public static final String SIGN_POINTS = "sign_points";
		public static final String VERSION = "version";
		public static final String USER_ID = "user_id";
		public static final String IS_BIND = "is_bind";
		public static final String MOBILE = "mobile";
		public static final String USER_TYPE = "type";

		public static final String ALL_FIELDS = "ka_user_id,name,nick,sex,points,organize_id,from_channel,status,idcard_no,"
				+ "first_shopping_time,recent_shopping_time,total_shopping_fee,avg_fee_of_order,total_orders_num,attributes,"
				+ "gmt_create,gmt_modify,province,city,district,address,belong_shop,birthday,options,first_sign_time,recent_sign_time,"
				+ "total_sign_times,continued_sign_times,sign_points,version,user_id,is_bind,mobile,type";

		public SearchFields() {
		}
	}
}
