package ${packagePrefix}.support.ldap.enums;

import lombok.Getter;

@Getter
public enum LdapErrorEnum {
    
    LDAP_LOGIN_FAIL(1110010001, "LDAP登陆失败"),
    LDAP_USER_FIND_FAIL(1110010002, "LDAP用户查找失败"),
    
    ;

    int code;
    String msg;

    LdapErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public String toString() {
        return msg;
    }
}
