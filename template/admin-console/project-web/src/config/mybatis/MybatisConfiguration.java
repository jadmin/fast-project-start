package ${packagePrefix}.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("${packagePrefix}.dao")
public class MybatisConfiguration { //i
	
	@Bean
    public String operatorInterceptor(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addInterceptor(new AutoFillOperatorInterceptor());
        return "interceptor";
    }

}
