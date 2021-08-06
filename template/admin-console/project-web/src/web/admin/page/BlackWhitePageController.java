package ${packagePrefix}.web.admin.page;

import com.github.javaclub.sword.core.Entry;
import ${packagePrefix}.web.admin.AdminControllerBase;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/console")
public class BlackWhitePageController extends AdminControllerBase {

    @RequestMapping(value = "/bwl/blackWhiteBizEnum", method = RequestMethod.GET)
    public String blackWhiteBizEnum(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("blackWhiteListAdmin", isAdmin);

        return "/bwl/blackWhiteBizEnum";
    }

    @RequestMapping(value = "/bwl/blackWhiteList", method = RequestMethod.GET)
    public String blackWhiteList(Model model,
                                 @RequestParam(defaultValue = "") String bizCode) {
        model.addAttribute("bizCode", bizCode);

        List<Entry<String, String>> groupList = buildBizEnums();
        model.addAttribute("groupList", groupList);
        
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("blackWhiteListAdmin", isAdmin);

        return "/bwl/blackWhiteList";
    }


}
