package ${packagePrefix}.web.admin.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javaclub.sword.core.Numbers;
import ${packagePrefix}.dataobject.ResourceDO;
import ${packagePrefix}.service.ResourceService;
import ${packagePrefix}.web.admin.AdminControllerBase;

@Controller
@RequestMapping("/console")
public class ResourcePageController extends AdminControllerBase {
	
	@Autowired
	private ResourceService resourceService;
	
	@GetMapping(value = "/resource")
    public String viewlist(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        return "/resource/list";
	}
	
	@GetMapping(value = "/resource/modal")
    public String entityModal(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        if (0 >= id) {
        	model.addAttribute("id", 0);
        	return "/resource/modalAdd";
        }
        
        ResourceDO entity = resourceService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("entity", entity);
        
        return "/resource/modalUpdate";
	}
    

}
