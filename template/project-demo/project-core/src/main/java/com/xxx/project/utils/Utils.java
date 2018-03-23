/*
 * @(#)Utils.java	2017年9月4日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.utils;

import java.util.Collection;
import java.util.List;

import com.github.javaclub.sword.core.Strings;
import com.google.common.collect.Lists;

/**
 * Utils
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: Utils.java 2017年9月4日 17:17:43 Exp $
 */
public class Utils {
	
	public static int intValue(Object obj) {
		if(null == obj) {
			return 0;
		}
		
		try {
			return Integer.valueOf(obj.toString());
		} catch (NumberFormatException e) {
		}
		
		return 0;
	}

	/**
	 * 性别：0-未知 1-男 2-女
	 *
	 */
	public static int toSexValue(String sex) {
		if(Strings.equalsIgnoreCase("MALE", sex)
				|| Strings.equals("男", sex) 
				|| Strings.equals("男性", sex)) {
			return 1;
		}
		if(Strings.equalsIgnoreCase("FEMAIL", sex) 
				|| Strings.equals("女", sex) 
				|| Strings.equals("女性", sex)) {
			return 2;
		}
		
		return 0;
	}
	
	/** 
     * 根据身份证号码获取生日信息
     * 
     * @param idCard 
     * @return String 
     */ 
    public static String getBirthdayByIdCard(String idCard) { 
        String idCardNumber = idCard.trim(); 
        int idCardLength = idCardNumber.length(); 
        String birthday = null; 
        if (idCardNumber == null || "".equals(idCardNumber)) { 
            return null; 
        } 
        if (idCardLength == 18) { 
            birthday = idCardNumber.substring(6, 10) + "-" 
                    + idCardNumber.substring(10, 12) + "-" 
                    + idCardNumber.substring(12, 14); 
        } 
        if (idCardLength == 15) { 
            birthday = "19" + idCardNumber.substring(6, 7) + "-" 
                    + idCardNumber.substring(8, 10) + "-" 
                    + idCardNumber.substring(10, 12); 
        } 
        
        return birthday; 
    } 
    
    public static List<String> toStringList(Collection<?> numbers) {
	    	List<String> list = Lists.newArrayList();
	    	for (Object number : numbers) {
				if(null == number) {
					continue;
				}
				list.add(number.toString());
			}
	    	return list;
    }
    
    public static void main(String[] args) {
		String id = "421121198508312017";
		System.out.println(getBirthdayByIdCard(id));
	}
}
