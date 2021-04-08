package ${packagePrefix}.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.javaclub.monitor.component.alarm.AlarmMonitor;
import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.core.Strings;
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
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            Date triggerTime = new Date();
            String paramsString = getParamsString(joinPoint);
            String methodString = getMethodString(joinPoint);
            if (!(e instanceof BizException)) {

                String ex = ExceptionUtils.getStackTrace(e);
                log.error(methodString + ", params = {}, throwable = {}", paramsString, ex);
                
                // 钉钉报警
                alarmMonitor.sendDingTalk("ERROR", triggerTime, Maps.createStringMap(
                        "调用服务", methodString,
                        "调用参数", paramsString,
                        "异常堆栈", Strings.lessText(ex, 70)
                ));
            }

            throw e;
        }
    }
    

    private static String getMethodString(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getDeclaringClass().getSimpleName() + "#" + method.getName();
    }

    private static String getParamsString(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            return "";
        } else {
            List<String> strings = Arrays.stream(args).map(JSON::toJSONString).collect(Collectors.toList());
            return Joiner.on(",").join(strings);
        }
    }


}
