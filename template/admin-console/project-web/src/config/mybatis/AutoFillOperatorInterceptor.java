package ${packagePrefix}.config.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.github.javaclub.sword.core.Strings;
import ${packagePrefix}.common.utils.LoginUserHolder;
import ${packagePrefix}.view.AdminUserView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Properties;

@Component
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class AutoFillOperatorInterceptor implements Interceptor {
	
    private static final String CREATOR = "creator";
    private static final String MODIFIER = "modifier";
    private static final String SYSTEM = "system";

    @Override
    public Object intercept(Invocation invocation) throws IllegalAccessException, InvocationTargetException {
        fillField(invocation);
        return invocation.proceed();
    }

    private void fillField(Invocation invocation) {
        Object[] args = invocation.getArgs();
        SqlCommandType sqlCommandType = null;
        for (Object arg : args) {
            // 第一个参数处理。根据它判断是否给“操作属性”赋值。
            if (arg instanceof MappedStatement) {
                // 如果是第一个参数 MappedStatement
                MappedStatement ms = (MappedStatement) arg;
                sqlCommandType = ms.getSqlCommandType();
                if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE) {
                    // 如果是“增加”或“更新”操作，则继续进行默认操作信息赋值。否则，则退出
                    continue;
                } else {
                    break;
                }
            }
            if (sqlCommandType == SqlCommandType.INSERT) {
                String userId = currentUser();
                for (Field f : arg.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    switch (f.getName()) {
                        case CREATOR:
                            ReflectionUtils.setField(f, arg, userId);
                            break;
                        case MODIFIER:
                            ReflectionUtils.setField(f, arg, userId);
                            break;
                        default:
                            break;
                    }
                }
            } else if (sqlCommandType == SqlCommandType.UPDATE) {
                String userId = currentUser();
                for (Field f : arg.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    if (MODIFIER.equals(f.getName())) {
                        ReflectionUtils.setField(f, arg, userId);
                    }
                }
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    String currentUser() {
        AdminUserView adminUser = (AdminUserView) LoginUserHolder.get();
        if (Objects.isNull(adminUser)) {
            return SYSTEM;
        }
        String userName = adminUser.getName();
        if (Strings.isBlank(userName)) {
            return SYSTEM;
        }
        return userName;
    }
}