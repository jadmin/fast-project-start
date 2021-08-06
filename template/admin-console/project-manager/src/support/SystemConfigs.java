package ${packagePrefix}.support;

import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.core.Systems;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "system.configs")
@Lazy(false)
@Data
public class SystemConfigs {
	
	public static final String ENV_DEV = "dev";
	public static final String ENV_TEST = "daily";
	public static final String ENV_PRE = "gray";
	public static final String ENV_PROD = "prod";
	
    private String env;
    private String skin;
    private boolean loginCheck;
    
    private String appName;
    private String appVersion;
    
    private String cookieKey; // admin后台用户cookieKey
    
    private int bwlCacheRefreshInterval = 24*3600; // 黑白名单缓存全量刷新间隔(sec)
    
    private String redisPublishTopics;
    
    private String localUploadPath; // 本地上传路径
    
    private String noLoginUris; // 不需要登录的
    
    public String[] getNoLoginUris() {
    	if(Strings.isBlank(noLoginUris)) {
    		return new String[0]; 
    	}
    	String[] uris = Strings.splitAndTrim(noLoginUris, ",");
    	return uris;
    }
    
    public String getLocalUploadPath() {
    	if(Strings.isBlank(localUploadPath)) {
    		return Systems.getSystemProperty("user.home");
    	}
    	return localUploadPath;
    }
    
    public String[] parseRedisPublishTopics() {
    	if(Strings.isBlank(redisPublishTopics)) {
    		return new String[0]; 
    	}
    	String[] redisTopics = Strings.splitAndTrim(redisPublishTopics, ",");
    	return redisTopics;
    }
    
    /**
     * 是否Debug模式
     */
    public boolean isDebugMode() {
    	String devDebug = Systems.getSystemProperty("devDebug");
    	if (Strings.equals("true", devDebug)) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isLocalEnv() {
		String lauchMode = Systems.getSystemProperty("lauch.mode");
		return "local".equals(getEnv()) || Objects.equals("local", lauchMode);
	}
    
    public String getEnvDesc() {
		String desc = "未知环境";
		switch (getEnv()) {
			case ENV_DEV:
				desc = "开发环境";
				break;
			case ENV_TEST:
				desc = "测试环境";
				break;
			case ENV_PRE:
				desc = "灰度环境";
				break;
			case ENV_PROD:
				desc = "生产环境";
				break;
			default:
				break;
		}
		return desc;
    }
    
	public boolean isDevEnv() {
		return Objects.equals(ENV_DEV, getEnv());
	}
	
	public boolean isDailyEnv() {
		return Objects.equals(ENV_TEST, getEnv());
	}
	
	public boolean isGrayEnv() {
		return Objects.equals(ENV_PRE, getEnv());
	}
	
	public boolean isProdEnv() {
		return Objects.equals(ENV_PROD, getEnv());
	}
	
	public boolean isDevOrTestEnv() {
		return isDevEnv() || isDailyEnv();
	}
	
}
