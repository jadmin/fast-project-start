package ${packagePrefix}.web.admin.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Numbers;
import com.google.common.collect.Lists;
import ${packagePrefix}.dao.UserRoleDAO;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.dataobject.RoleDO;
import ${packagePrefix}.dataobject.UserRoleDO;
import ${packagePrefix}.param.MenuTreeNode;
import ${packagePrefix}.query.MenuModuleQuery;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.service.RoleMenuService;
import ${packagePrefix}.web.admin.AdminControllerBase;

@Controller
@RequestMapping("/console")
public class RolePageController extends AdminControllerBase {
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private LoginUserService loginUserService;
	
	@GetMapping(value = "/role")
    public String viewlist(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        if (0 < id) { 
        	RoleDO entity = roleService.selectById(id);
        	if(null == entity) {
        		return "/role/list";
        	}
            model.addAttribute("entity", entity);
            
            return "role/roleUserList";
        }
        
        return "/role/list";
	}
	
	
	@GetMapping(value = "/role/roleUserModal")
    public String roleUserModal(Model model) throws Exception {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        Long rId = Numbers.toLong(request.getParameter("id"), 0L);
        Long roleId = Numbers.toLong(request.getParameter("roleId"), 0L);
        if (rId > 0 && 0 <= roleId) { // 延长有效期
        	UserRoleDO urd = userRoleDAO.getById(rId);
        	if(null != urd) {
        		RoleDO role = roleService.selectById(urd.getRoleId());
                model.addAttribute("role", role);
        		model.addAttribute("urd", urd);
        		
        		LoginUserDO user = loginUserService.selectById(urd.getUserId());
        		model.addAttribute("user", user);
        	}
        	return "role/roleUserModalProlong";
        } else if (roleId > 0) { // 为角色添加用户
        	RoleDO role = roleService.selectById(roleId);
            model.addAttribute("role", role);
            return "role/roleUserModal";
        }
        
        return "";
	}
	
	@GetMapping(value = "/role/modal")
    public String entityModal(Model model) throws Exception {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        
        MenuModuleQuery query = new MenuModuleQuery();
    	query.setPageable(1, 5000);
    	query.addOrderBy("parent_id", false);
    	query.addOrderBy("sort", false);
    	List<MenuModuleDO> list = menuModuleService.findList(query);
    	
        if (0 >= id) {
        	model.addAttribute("id", 0);
        	
        	String json = JSON.toJSONString(BizObjects.isEmpty(list)? new ArrayList<MenuModuleDO>() : list);
        	model.addAttribute("menuTreeJson", json);
        	
        	return "/role/modalAdd";
        }
        
        List<Long> menuIds = roleMenuService.findRoleMenuNode(id);
        List<MenuTreeNode> treeList = Lists.newArrayList();
        for (MenuModuleDO node : list) {
			if(null != menuIds && menuIds.contains(node.getId())) {
				treeList.add(new MenuTreeNode(node, true));
			} else {
				treeList.add(new MenuTreeNode(node));
			}
		}
        
        String json = JSON.toJSONString(treeList);
    	model.addAttribute("menuTreeJson", json);
        
        RoleDO entity = roleService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("entity", entity);
        
        return "/role/modalUpdate";
	}
    

}
