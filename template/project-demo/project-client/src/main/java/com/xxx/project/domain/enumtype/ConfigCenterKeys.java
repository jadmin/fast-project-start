/*
 * @(#)ConfigCenterKeys.java	2017年8月10日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.domain.enumtype;

import java.util.ArrayList;
import java.util.List;

/**
 * ConfigCenterKeys 在本枚举类中定义的key必须需要在应用的配置中心配置，否则会报错！！！
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ConfigCenterKeys.java 2017年8月10日 15:15:33 Exp $
 */
public enum ConfigCenterKeys {
	
	TEST_CONFIG("test.players", "测试配置项"),
	
	OPS_TOOL_DATA("ops.tool.data", "开发运维数据"),
	GLOBAL_CONFIG_VALUES("global.config.values", "全局常量配置"),
       
    
	;
	
    private String code;

    private String desc;

    private ConfigCenterKeys(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code映射对应枚举
     *
     */
    public static ConfigCenterKeys from(String code) {
        for (ConfigCenterKeys t : ConfigCenterKeys.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static List<String> getKeys() {
        List<String> keys = new ArrayList<String>();
        for (ConfigCenterKeys value : ConfigCenterKeys.values()) {
            keys.add(value.getCode());
        }
        return keys;
    }
}
