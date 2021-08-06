package ${packagePrefix}.support.ldap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;

import ${packagePrefix}.support.ldap.provider.LdapProvider;


@Configuration
public class LdapAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LdapProvider ldapProvider(LdapTemplate ldapTemplate,
                                     LdapProperties ldapProperties) {
        return new LdapProvider(ldapTemplate, ldapProperties);
    }
}
