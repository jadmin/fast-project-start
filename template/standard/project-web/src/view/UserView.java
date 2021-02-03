package ${packagePrefix}.view;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "小程序用户展示")
public class UserView {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("微信名称")
    private String wxName;

    @ApiModelProperty("性别: 0-未知 1-男 2-女")
    private Integer gender;

    @ApiModelProperty("用户头像地址")
    private String avatar;
    
    @ApiModelProperty("0-普通用户 1-付费用户")
    private Integer userType;

    @ApiModelProperty("是否有效期内的Vip付费用户: true-是 false-否")
    private boolean vipInValidity;

    @ApiModelProperty("Vip付费用户失效时间")
    private Date expiredTime;
    
    @ApiModelProperty("Vip付费首次开通时间")
    private Date vipOpenTime;

}
