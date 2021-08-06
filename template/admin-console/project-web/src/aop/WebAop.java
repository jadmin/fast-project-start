package ${packagePrefix}.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.github.javaclub.monitor.component.alarm.AlarmMonitor;
import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.web.HttpController;
import com.google.common.base.Joiner;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@Order(20)
public class WebAop {

    @Autowired
    private AlarmMonitor alarmMonitor;

    @Around(("execution(public * ${packagePrefix}.web..*.*(..))"))
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    	long start = System.currentTimeMillis();
    	String desc = "Normal";
    	String methodString = getMethodString(joinPoint);
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            Date triggerTime = new Date();
            String paramsString = getParamsString(joinPoint);
            if (!(e instanceof BizException)) {

                String ex = ExceptionUtils.getStackTrace(e);
                log.error(methodString + ", params = {}, throwable = {}", paramsString, ex);
                
                // 钉钉报警
                alarmMonitor.sendDingTalk("ERROR", triggerTime, Maps.createStringMap(
                        "调用服务", methodString,
                        "调用参数", paramsString,
                        "异常堆栈", Strings.lessText(ex, 10)
                ));
            }
            desc = "Exception";
            throw e;
        } finally {
        	log.info("{}, cost={}, desc={}", methodString, System.currentTimeMillis() - start, desc);
		}
    }
    

    private String getMethodString(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getDeclaringClass().getSimpleName() + "#" + method.getName();
    }

    private String getParamsString(ProceedingJoinPoint joinPoint) {
    	MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getDeclaringClass().getSimpleName();
        if(method.getDeclaringClass().equals(HttpController.class) 
        		|| Strings.endsWithIgnoreCase(methodName, "Controller")) {
        	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        	Map<String, String[]> parameterMap = request.getParameterMap();
        	if(null != parameterMap && !parameterMap.isEmpty()) {
        		return JSON.toJSONString(toPlainMap(parameterMap));
        	}
        }
        Object[] args = joinPoint.getArgs();
        if(!BizObjects.isEmpty(args)) {
        	List<String> strings = Arrays.stream(args).map(JSON::toJSONString).collect(Collectors.toList());
            return Joiner.on(",").join(strings);
        }
            
        return "";
    }

	private Map<String, Object> toPlainMap(Map<String, String[]> parameterMap) {
		Map<String, Object> map = com.google.common.collect.Maps.newHashMap();
		for (Map.Entry<String, String[]> e : parameterMap.entrySet()) {
			map.put(e.getKey(), e.getValue());
			if(null != e.getValue()) {
				int size = e.getValue().length;
				map.put(e.getKey(), size == 1 ? e.getValue()[0] : e.getValue());
			}
		}
		return map;
	}


}

