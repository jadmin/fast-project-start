/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)RoleMenuController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${packagePrefix}.dataobject.RoleMenuDO;
import ${packagePrefix}.query.RoleMenuQuery;
import ${packagePrefix}.service.RoleMenuService;
import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import com.github.javaclub.sword.web.PageResultSet;
import com.google.common.collect.Maps;

/**
 * RoleMenuController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RoleMenuController.java ${currentTime} Exp $
 */
@RestController
@RequestMapping("/admin/rmenu")
public class RoleMenuController extends AdminControllerBase {
	
	static final Logger log = LoggerFactory.getLogger(RoleMenuController.class);
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@PostMapping(value = "/saveRequest")
	public HttpResult<Long> saveRequest(HttpServletRequest request) throws Exception {
		RoleMenuDO post = WebUtil.parseObject(request, RoleMenuDO.class);
		try {
			// validate check for RoleMenuDO
			 if(null == post.getId() || 0 >= post.getId()) {
            	post.setCreator(getLoginUsername());
            	post.setModifier(getLoginUsername());
            } else {
            	post.setModifier(getLoginUsername());
            }
		} catch (BizException e) {
			return HttpResult.failure(e.getMessage());
		}
		
		ResultDO<Boolean> r = roleMenuService.save(post);
        if(!r.isSuccess()) {
            return HttpResult.failure(r.getMessage());
        }
        
        return HttpResult.success(post.getId());
	}
	
	@GetMapping(value = "/detail")
	public HttpResult<RoleMenuDO> detail(Long id) {
		return doDetail(id);
	}
	
	@GetMapping(value = "/detail/{id}")
	public HttpResult<RoleMenuDO> _detail(@PathVariable Long id) {
		return doDetail(id);
	}

	@PostMapping(value = "/delete")
	public HttpResult<Boolean> delete(Long id) {
		return doDelete(id);
	}

	@PostMapping(value = "/delete/{id}")
	public HttpResult<Boolean> _delete(@PathVariable Long id) {
		return doDelete(id);
	}
	
	@GetMapping(value = "/pagelist")
	public HttpResult<PageResultSet<RoleMenuDO>> list(@RequestBody RoleMenuQuery request) {
		QueryResult<RoleMenuDO> queryResult = roleMenuService.findListWithCount(request);
		PageResultSet<RoleMenuDO> sets = new PageResultSet<>(request.getPageNo(), request.getPageSize());
		sets.setAllRow(queryResult.getTotalCount());
		sets.setList(queryResult.getEntry());
		return HttpResult.success(sets);
	}
	
	/**
	 * BootstrapTable Data Api
	 */
	@GetMapping(value = "/bootstrapTableList")
    public Map<String, Object> listData(HttpServletRequest request, Model model) {
		RoleMenuQuery query = WebUtil.parseObject(request, RoleMenuQuery.class);
		int count = roleMenuService.count(query);
		List<RoleMenuDO> list = roleMenuService.findList(query);
        Map<String, Object> map = Maps.newHashMap();
        map.put("rows", null == list ? new ArrayList<RoleMenuDO>() : list);
        map.put("total", count);
        return map;
    }
	
	protected HttpResult<RoleMenuDO> doDetail(Long id) {
		ResultDO<RoleMenuDO> db = roleMenuService.getById(id);
		return HttpResult.success(db.getEntry());
	}
	
	protected HttpResult<Boolean> doDelete(Long id) {
		ResultDO<RoleMenuDO> db = roleMenuService.getById(id);
		if(!db.isSuccess() || null == db.getEntry()) { // 不存在
			return HttpResult.failure("数据不存在或已被删除");
		}
		ResultDO<Boolean> operate = roleMenuService.deleteById(id);
		if(!operate.isSuccess()) {
			return HttpResult.failure(operate.getMessage());
		}
		return HttpResult.success();
	}
	
}
