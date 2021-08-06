package ${packagePrefix}.web.admin.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.github.javaclub.sword.core.BizObjects;
import ${packagePrefix}.common.enums.ResourceType;
import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.dataobject.ResourceDO;
import ${packagePrefix}.query.MenuModuleQuery;
import ${packagePrefix}.query.ResourceQuery;
import ${packagePrefix}.web.admin.AdminControllerBase;

@Controller
@RequestMapping("/console")
public class MenuPageController extends AdminControllerBase {
	
	/**
     * 菜单管理页
     */
    @GetMapping("/menu")
    public String menu(HttpServletRequest request, Model model) {
    	
    	MenuModuleQuery query = new MenuModuleQuery();
    	query.setPageable(1, 200);
    	query.addOrderBy("parent_id", false);
    	query.addOrderBy("sort", false);
    	List<MenuModuleDO> list = menuModuleService.findList(query);
    	
    	String json = JSON.toJSONString(BizObjects.isEmpty(list)? new ArrayList<MenuModuleDO>() : list);
    	model.addAttribute("menuTreeJson", json);
    	
    	ResourceQuery q = new ResourceQuery();
    	q.setPageable(1, 200);
    	q.setType(ResourceType.PAGE.getValue());
    	List<ResourceDO> pageRes = resourceService.findList(q);
    	model.addAttribute("pages", pageRes);
    	
        return "/menu/menuEdit";
    }
    

}
