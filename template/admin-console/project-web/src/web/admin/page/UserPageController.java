package ${packagePrefix}.web.admin.page;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Numbers;
import com.google.common.base.Joiner;
import ${packagePrefix}.common.AppConstants.UserRoles;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.dataobject.RoleDO;
import ${packagePrefix}.query.RoleQuery;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.web.admin.AdminControllerBase;

@Controller
@RequestMapping("/console")
public class UserPageController extends AdminControllerBase {
	
	@Autowired
	private LoginUserService loginUserService;
	
	/**
     * 用户管理
     */
    @GetMapping("/user")
    public String userList(HttpServletRequest request, Model model) {
        return "/user/list";
    }
    
    @GetMapping("/onlineUserList")
    public String onlineUserList(HttpServletRequest request, Model model) {
        return "/user/onlineUserList";
    }
    
    /**
     * 用户重置密码
     */
    @GetMapping("/user/forgetPassword")
    public String userForgetPassword(HttpServletRequest request, Model model) {
        return "/user/forgetPassword";
    }
    
    /**
     * 个人详情页
     */
    @GetMapping("/profile")
    public String me(HttpServletRequest request, Model model) {
        return "/user/currentUser";
    }
    
	@GetMapping(value = "/user/modal")
    public String entityModal(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        RoleQuery query = new RoleQuery();
    	query.setPageable(1, 500);
    	query.setStatus(1); // 有效的角色数据
    	query.setRoleCodeNotEuqal(UserRoles.SUPER_ADMIN);
    	List<RoleDO> roleList = roleService.findList(query);
    	model.addAttribute("roleList", roleList);
        
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        if (0 >= id) {
        	model.addAttribute("id", 0);
        	return "/user/modalAdd";
        }
        
        LoginUserDO entity = loginUserService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("entity", entity);
        
        if(null != entity.getAttributesMap() 
        		&& null != entity.getAttributesMap().get(LoginUserDO.ATTR_USER_ROLES)) {
        	String str = Objects.toString(entity.getAttributesMap().get(LoginUserDO.ATTR_USER_ROLES), "");
        	List<Long> longArr = Numbers.splitAsLongList(str, ",");
        	String txt = "";
        	if(BizObjects.length(longArr) > 0) {
        		txt = Joiner.on(",").join(longArr);
        	}
        	model.addAttribute("userRoles", txt);
        }
        
        return "/user/modalUpdate";
	}
	
	@GetMapping(value = "/user/userRoleModal")
    public String userRoleModal(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        RoleQuery query = new RoleQuery();
    	query.setPageable(1, 500);
    	query.setStatus(1); // 有效的角色数据
    	query.setRoleCodeNotEuqal(UserRoles.SUPER_ADMIN);
    	List<RoleDO> roleList = roleService.findList(query);
    	model.addAttribute("roleList", roleList);
        
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        LoginUserDO entity = loginUserService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("entity", entity);
        
        return "/user/modalUserRoles";
	}
    

}
