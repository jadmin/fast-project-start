package ${packagePrefix}.web.admin.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.javaclub.sword.core.Numbers;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.QueryResult;
import com.google.common.collect.Lists;
import ${packagePrefix}.common.enums.WidgetType;
import ${packagePrefix}.dataobject.BasicComponentDO;
import ${packagePrefix}.query.BasicComponentQuery;
import ${packagePrefix}.service.BasicComponentService;
import ${packagePrefix}.web.admin.AdminControllerBase;

@Controller
@RequestMapping("/console")
public class OpsToolsPageController extends AdminControllerBase {
	
	@Autowired
	private BasicComponentService basicComponentService;
	
	@RequestMapping(value = "/ops/toolset", method = RequestMethod.GET)
    public String toolset(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        return "/ops/toolset";
	}
	
	@RequestMapping(value = "/ops/toolset/modal", method = RequestMethod.GET)
    public String toolsetModal(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        Long id = Numbers.toLong(request.getParameter("id"), 0L);
        if (0 >= id) {
        	model.addAttribute("id", 0);
        	return "/ops/toolset/modalAdd";
        }
        
        BasicComponentDO bcd = basicComponentService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("entity", bcd);
        
        return "/ops/toolset/modalUpdate";
	}

    @RequestMapping(value = "/ops/devtools", method = RequestMethod.GET)
    public String devtools(Model model) {
        boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        String appPath = request.getContextPath();
        String queryWord = request.getParameter("q");
        int pageNo = Numbers.toInt(request.getParameter("p"), 1);
        int pageSize = 5;
        
        BasicComponentQuery query = new BasicComponentQuery();
        query.setWidgetType(WidgetType.DEV_TOOL.getValue());
        query.setStatus(1);
        if(Strings.isNotBlank(queryWord)) {
        	model.addAttribute("queryWord", queryWord.trim());
        	query.setWidgetNameLike(queryWord);
        }
        query.setPageable(pageNo, pageSize);
        QueryResult<BasicComponentDO> qr = basicComponentService.findListWithCount(query);
        
        int total = qr.getTotalCount();
        List<BasicComponentDO> list = qr.getEntry();
        
        model.addAttribute("totalRecords", total);
        model.addAttribute("totalPages", total%pageSize>0 ? (total/pageSize + 1) : total/pageSize);
        model.addAttribute("currentPage", pageNo);
        
        StringBuilder ajax = new StringBuilder(); 
        StringBuilder functions = new StringBuilder(); 
        List<BasicComponentDO> leftList = Lists.newArrayList();
        List<BasicComponentDO> rightList = Lists.newArrayList();
        
        if(null != list && list.size() > 0) {
        	for (int i = 0; i < list.size(); i++) {
        		BasicComponentDO e = list.get(i);
        		String html = e.getWidgetHtml();
            	if(Strings.isNotBlank(html)) {
            		e.setWidgetHtml(Strings.replace(html, "${appPath}", appPath));
            	}
            	ajax.append(Strings.noneBlank(e.getWidgetAjax(), "")).append("\n\n");
            	functions.append(Strings.noneBlank(e.getWidgetFunction(), "")).append("\n\n");
            	if(i%2 == 0) {
            		leftList.add(e);
            	} else {
            		rightList.add(e);
            	}
			}
        }
        
        
        model.addAttribute("leftList", leftList);
        model.addAttribute("rightList", rightList);
        
        model.addAttribute("ajax", ajax.toString().trim());
        model.addAttribute("functions", functions.toString().trim());

        return "/ops/devtools";
    }

    @RequestMapping(value = "/ops/biztools", method = RequestMethod.GET)
    public String blackWhiteList(Model model) {
    	boolean isAdmin = isSuperAdmin(); 
        model.addAttribute("developAdmin", isAdmin);
        
        String appPath = request.getContextPath();
        String queryWord = request.getParameter("q");
        int pageNo = Numbers.toInt(request.getParameter("p"), 1);
        int pageSize = 5;
        
        BasicComponentQuery query = new BasicComponentQuery();
        query.setWidgetType(WidgetType.BIZ_TOOL.getValue());
        query.setStatus(1);
        if(Strings.isNotBlank(queryWord)) {
        	model.addAttribute("queryWord", queryWord.trim());
        	query.setWidgetNameLike(queryWord);
        }
        query.setPageable(pageNo, pageSize);
        QueryResult<BasicComponentDO> qr = basicComponentService.findListWithCount(query);
        
        int total = qr.getTotalCount();
        List<BasicComponentDO> list = qr.getEntry();
        
        model.addAttribute("totalRecords", total);
        model.addAttribute("totalPages", total%pageSize>0 ? (total/pageSize + 1) : total/pageSize);
        model.addAttribute("currentPage", pageNo);
        
        StringBuilder ajax = new StringBuilder(); 
        StringBuilder functions = new StringBuilder(); 
        List<BasicComponentDO> leftList = Lists.newArrayList();
        List<BasicComponentDO> rightList = Lists.newArrayList();
        
        if(null != list && list.size() > 0) {
        	for (int i = 0; i < list.size(); i++) {
        		BasicComponentDO e = list.get(i);
        		String html = e.getWidgetHtml();
            	if(Strings.isNotBlank(html)) {
            		e.setWidgetHtml(Strings.replace(html, "${appPath}", appPath));
            	}
            	ajax.append(Strings.noneBlank(e.getWidgetAjax(), "")).append("\n\n");
            	functions.append(Strings.noneBlank(e.getWidgetFunction(), "")).append("\n\n");
            	if(i%2 == 0) {
            		leftList.add(e);
            	} else {
            		rightList.add(e);
            	}
			}
        }
        
        
        model.addAttribute("leftList", leftList);
        model.addAttribute("rightList", rightList);
        
        model.addAttribute("ajax", ajax.toString().trim());
        model.addAttribute("functions", functions.toString().trim());

        return "/ops/biztools";
    }


}
