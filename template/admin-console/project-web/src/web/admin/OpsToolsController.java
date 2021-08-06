package ${packagePrefix}.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.util.DateUtil;
import com.github.javaclub.sword.util.Utils;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ${packagePrefix}.dataobject.BasicComponentDO;
import ${packagePrefix}.query.BasicComponentQuery;
import ${packagePrefix}.service.BasicComponentService;
import ${packagePrefix}.support.RedisCacheManager;
import ${packagePrefix}.view.BlackWhiteListView;

@RestController
@RequestMapping("/admin/ops")
public class OpsToolsController extends AdminControllerBase {
	
	@Autowired
	private RedisCacheManager redisCacheManager;
	
	@Autowired
	private BasicComponentService basicComponentService;
	
	@PostMapping(value = "/tools/saveRequest")
    public HttpResult<Boolean> saveRequest(HttpServletRequest request) throws Exception {
		BasicComponentDO bcd = WebUtil.parseObject(request, BasicComponentDO.class);
		try {
			BizObjects.requireNotNull(bcd, "数据异常，请稍后重试！");
            BizObjects.requireNotEmpty(bcd.getWidgetName(), "组件名称不能为空");
            BizObjects.requireNotNull(bcd.getWidgetType(), "请选择组件类型");
            BizObjects.requireNotEmpty(bcd.getWidgetHtml(), "Html代码不能为空");
            if(null == bcd.getId() || 0 >= bcd.getId()) {
            	bcd.setCreator(getLoginUsername());
            	bcd.setModifier(getLoginUsername());
            	bcd.setStatus(0);
            } else {
            	bcd.setModifier(getLoginUsername());
            }
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
		
        ResultDO<Boolean> r = basicComponentService.save(bcd);
        if(!r.isSuccess()) {
            return HttpResult.failure(r.getMessage());
        }
        
        return HttpResult.success(r.getEntry());
	}
	
	@RequestMapping(value = "/tools/listData", method = RequestMethod.GET)
    public Map<String, Object> toolsListData(HttpServletRequest request, Model model) {
		BasicComponentQuery query = WebUtil.parseObject(request, BasicComponentQuery.class);
        int count = basicComponentService.count(query);
        List<BasicComponentDO> list = basicComponentService.findList(query);
        Map<String, Object> map = Maps.newHashMap();
        map.put("rows", null == list ? new ArrayList<BlackWhiteListView>() : list);
        map.put("total", count);
        return map;
    }
	
	@PostMapping(value = "/toolsDisable")
    public HttpResult<Boolean> toolsDisable(@RequestParam Long id) {
		BasicComponentDO bcd = new BasicComponentDO();
		bcd.setId(id);
		bcd.setStatus(-1);
		boolean suc = basicComponentService.changeStatus(bcd);
		if(!suc) {
			return HttpResult.failure("更新失败");
		}
		return HttpResult.success();
	}
	
	@PostMapping(value = "/toolsEnable")
    public HttpResult<Boolean> toolsEnable(@RequestParam Long id) {
		BasicComponentDO bcd = new BasicComponentDO();
		bcd.setId(id);
		bcd.setStatus(1);
		boolean suc = basicComponentService.changeStatus(bcd);
		if(!suc) {
			return HttpResult.failure("更新失败");
		}
		return HttpResult.success();
	}
	
	@PostMapping(value = "/toolsIng")
    public HttpResult<Boolean> toolsIng(@RequestParam Long id) {
		BasicComponentDO bcd = new BasicComponentDO();
		bcd.setId(id);
		bcd.setStatus(0);
		boolean suc = basicComponentService.changeStatus(bcd);
		if(!suc) {
			return HttpResult.failure("更新失败");
		}
		return HttpResult.success();
	}
	
	@PostMapping(value = "/toolsDelete")
    public HttpResult<Boolean> toolsDelete(@RequestParam Long id) {
		BizObjects.requireNotNullGtZero(id, "id参数缺失");
		ResultDO<Boolean> rd = basicComponentService.deleteById(id);
		if(!rd.isSuccess()) {
			return HttpResult.failure("更新失败");
		}
		return HttpResult.success();
	}
    
	@PostMapping(value = "/redisKeyQuery")
    public HttpResult<String> redisKeyQuery(@RequestParam String redisKey,
    										@RequestParam String residQueryType) {
    		BizObjects.requireNotEmpty(redisKey, "查询的redisKey不可为空");
    		BizObjects.requireNotEmpty(residQueryType, "请选择查询方式");
    		switch (residQueryType) {
    		
				case "single":
					Object val = redisCacheManager.get(redisKey);
					return HttpResult.success(Objects.toString(val, Strings.EMPTY_STRING));
					
				case "set":
					Set set = redisCacheManager.querySetWithString(redisKey);
					if(null == set || set.isEmpty()) {
						return HttpResult.success("查询数据为空");
					}
					String str = Strings.join("\n", set.toArray());
					return HttpResult.success(str);
					
				case "zset":
					Date now = DateUtil.now();
					
					Set<TypedTuple<Object>> vals = redisCacheManager.querySetWithScore(redisKey, Utils.dateScore(DateUtil.plusDay(now, -10)), 
							Utils.dateScore(now));
					if(null == vals || vals.isEmpty()) {
						return HttpResult.success("查询数据为空");
					}
					List<String> list = vals.stream().map((s) -> {
						return Strings.concat(s.getValue(), ",", s.getScore().longValue());
					}).collect(Collectors.toList());
					
					String strR = Strings.join("\n", list.toArray());
					return HttpResult.success(strR);
					
				case "hash":
					Map map = redisCacheManager.queryHashWithString(redisKey);
					if(null == map || map.isEmpty()) {
						return HttpResult.success("查询数据为空");
					}
					
					List<String> strList = Lists.newArrayList();
					map.forEach((k, v) -> {
						String lineText = Strings.concat(k, ":", v);
						strList.add(lineText);
					});
				
				return HttpResult.success(Strings.join("\n", strList.toArray()));
		}
    		
    	return HttpResult.failure("暂不支持当前查询方式");
    }


}
