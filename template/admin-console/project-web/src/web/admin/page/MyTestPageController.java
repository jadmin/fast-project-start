package ${packagePrefix}.web.admin.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javaclub.sword.core.Numbers;
import ${packagePrefix}.dataobject.MyTestDO;
import ${packagePrefix}.service.MyTestService;
import ${packagePrefix}.web.admin.AdminControllerBase;

@Controller
@RequestMapping("/console")
public class MyTestPageController extends AdminControllerBase {
	
	@Autowired
	private MyTestService myTestService;
	
	@GetMapping(value = "/test")
    public String viewlist(Model model) {
		
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        return "/test/list";
	}
	
	@GetMapping(value = "/test/modal")
    public String entityModal(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        if (0 >= id) {
        	model.addAttribute("id", 0);
        	return "/test/modalAdd";
        }
        
        MyTestDO entity = myTestService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("entity", entity);
        
        return "/test/modalUpdate";
	}
    

}
