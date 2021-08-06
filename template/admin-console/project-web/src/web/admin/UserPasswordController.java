package ${packagePrefix}.web.admin;

import static com.github.javaclub.sword.core.Strings.format;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.algorithm.crypt.Base64;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.web.HttpResult;
import ${packagePrefix}.common.AppConstants.RedisKeys;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.param.ForgetPasswordParam;
import ${packagePrefix}.param.ForgetPasswordSmsParam;
import ${packagePrefix}.param.dto.ForgetPasswordSmsDTO;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.service.utils.BizUtils;



@RestController
@RequestMapping("/admin/user/password")
public class UserPasswordController {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
    private LoginUserService loginUserService;
    
	@Autowired
    private JavaMailSender javaMailSender;
    
	@Autowired
    private MailProperties mailProperties;

    @PostMapping("/forget")
    public HttpResult<Boolean> forgetPassword(@RequestBody ForgetPasswordParam forgetPasswordParam) throws Exception {
        String password = forgetPasswordParam.getPassword();
        String confirmPassword = forgetPasswordParam.getConfirmPassword();
        String username = forgetPasswordParam.getUsername();
        String randVerificationCode = forgetPasswordParam.getRandVerificationCode();
        if (Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(confirmPassword)) {
            return HttpResult.failure(ErrorEnum.PARAM_VALID_ERROR.getCode(), "密码不能为空");
        }
        if (!Objects.equals(password, confirmPassword)) {
            return HttpResult.failure(ErrorEnum.PARAM_VALID_ERROR.getCode(), "前后两次密码不一致");
        }
        String cacheKey = RedisKeys.formatKey(RedisKeyConstants.FORGET_PASSWORD_SMS_CACHE_KEY, username);
        ForgetPasswordSmsDTO forgetPasswordSmsDTO = (ForgetPasswordSmsDTO) redisTemplate.opsForValue().get(cacheKey);
        if (Objects.isNull(forgetPasswordSmsDTO)) {
            return HttpResult.failure(ErrorEnum.USER_RESET_PASSWORD_TOKEN_INVALID.getCode(), "验证码已失效或错误");
        }
        if (!Objects.equals(forgetPasswordSmsDTO.getUsername(), username)) {
            return HttpResult.failure(ErrorEnum.PARAM_VALID_ERROR.getCode(), "用户校验失败");
        }
        if (!Objects.equals(forgetPasswordSmsDTO.getRandVerificationCode(), randVerificationCode)) {
        	return HttpResult.failure(ErrorEnum.PARAM_VALID_ERROR.getCode(), "验证码错误");
        }

        String entryPassword = Base64.decode(forgetPasswordParam.getPassword());
        LoginUserDO userDO = new LoginUserDO();
        userDO.setId(forgetPasswordSmsDTO.getUserId());
        userDO.setPassword(BizUtils.generatePasswordMD5(entryPassword));
        ResultDO<Boolean> update = loginUserService.update(userDO);
        if (!update.isSuccess()) {
            return HttpResult.failure(ErrorEnum.SYSTEM_ERROR.getCode(), "重置密码失败");
        }
        redisTemplate.delete(cacheKey);
        return HttpResult.success();
    }

    @PostMapping("/forget/sms")
    public HttpResult<Boolean> forgetPasswordSms(@RequestBody ForgetPasswordSmsParam forgetPasswordSmsParam) {
        String username = forgetPasswordSmsParam.getUsername();
        if (Strings.isNullOrEmpty(username)) {
            return HttpResult.failure(ErrorEnum.PARAM_VALID_ERROR.getCode(), "用户名不能为空");
        }
        LoginUserDO loginUserDO = loginUserService.getByUsername(username);
        if (Objects.isNull(loginUserDO)) {
            return HttpResult.failure(ErrorEnum.PARAM_VALID_ERROR.getCode(), "请输入正确的用户名");
        }
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom(mailProperties.getUsername());
        passwordResetEmail.setTo(loginUserDO.getEmail());
        passwordResetEmail.setSubject("【后台管理系统】忘记密码");

        String randVerificationCode = Strings.random(6, "1234567890");
        String format = format("账户：{} 正在申请重置密码, 重置验证码为: {} , 该验证码60秒内有效, 如非本人操作请忽略",
                loginUserDO.getUsername(), randVerificationCode);
        passwordResetEmail.setText(format);
        ForgetPasswordSmsDTO forgetPasswordSmsDTO = new ForgetPasswordSmsDTO();
        forgetPasswordSmsDTO.setUserId(loginUserDO.getId());
        forgetPasswordSmsDTO.setRandVerificationCode(randVerificationCode);
        forgetPasswordSmsDTO.setUsername(loginUserDO.getUsername());
        
        String cacheKey = RedisKeys.formatKey(RedisKeyConstants.FORGET_PASSWORD_SMS_CACHE_KEY, username);
        redisTemplate.opsForValue().set(cacheKey, forgetPasswordSmsDTO, 90, TimeUnit.SECONDS);
        javaMailSender.send(passwordResetEmail);
        return HttpResult.success();
    }
    
}
