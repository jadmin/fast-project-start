package ${packagePrefix}.view;

import org.springframework.beans.BeanUtils;

import com.github.javaclub.sword.core.Strings;
import ${packagePrefix}.dataobject.BlackWhiteListDO;

import lombok.Data;


@Data
public class BlackWhiteListView extends BlackWhiteListDO {

    private static final long serialVersionUID = 1L;

    private String bizDesc;

    public BlackWhiteListView(BlackWhiteListDO bwd, String bizDesc) {
        BeanUtils.copyProperties(bwd, this);
        this.bizDesc = bizDesc;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

    public String getBizName() {
        if(Strings.isBlank(getBizCode())) {
            return "";
        }
        String desc = getBizDesc();
        if(Strings.isBlank(desc)) {
            return getBizCode();
        }
        return Strings.concat(desc, "（", getBizCode(), "）");
    }
}
