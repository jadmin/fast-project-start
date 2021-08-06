package ${packagePrefix}.web.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import com.github.javaclub.sword.web.PageResultSet;
import com.google.common.collect.Maps;
import ${packagePrefix}.dataobject.BwlEnumDO;
import ${packagePrefix}.query.BlackWhiteListQuery;
import ${packagePrefix}.query.BwlEnumQuery;
import ${packagePrefix}.service.BlackWhiteListService;
import ${packagePrefix}.service.BwlEnumService;


@Controller
@RequestMapping("/admin/bwlenum")
public class BwlEnumController extends AdminControllerBase {
    
    @Autowired
    private BwlEnumService bwlEnumService;

    @Autowired
    private BlackWhiteListService blackWhiteListService;


    @RequestMapping(value = "/saveRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Integer> save(HttpServletRequest request) throws Exception {
        BwlEnumDO post = WebUtil.parseObject(request, BwlEnumDO.class);
        try {
            // validate check for BwlEnumDO
            BizObjects.requireNotEmpty(post.getBizCode(), "业务代码不能为空");
            BizObjects.requireNotEmpty(post.getBizDesc(), "业务描述不能为空");

        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }

        return save(post);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Integer> save(@RequestBody BwlEnumDO post) throws Exception {
    	if(null == post.getId() || 0 >= post.getId()) {
        	post.setCreator(getLoginUsername());
        }
    	post.setModifier(getLoginUsername());
        ResultDO<Boolean> operate = bwlEnumService.save(post);
        if (!operate.isSuccess()) {
            return HttpResult.failure(operate.getMessage());
        }
        return HttpResult.success(post.getId());
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<BwlEnumDO> detail(Integer id) {
        return doDetail(id);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<BwlEnumDO> _detail(@PathVariable Integer id) {
        return doDetail(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Boolean> delete(Integer id) {
        return doDelete(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Boolean> _delete(@PathVariable Integer id) {
        return doDelete(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<PageResultSet<BwlEnumDO>> list(@RequestBody BwlEnumQuery request) {
        QueryResult<BwlEnumDO> queryResult = bwlEnumService.findListWithCount(request);
        PageResultSet<BwlEnumDO> sets = new PageResultSet<>(request.getPageNo(), request.getPageSize());
        sets.setAllRow(queryResult.getTotalCount());
        sets.setList(queryResult.getEntry());
        return HttpResult.success(sets);
    }

    /**
     * BootstrapTable Data Api
     */
    @RequestMapping(value = "/listData.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listData(HttpServletRequest request, Model model) {
        BwlEnumQuery query = WebUtil.parseObject(request, BwlEnumQuery.class);
        int count = bwlEnumService.count(query);
        List<BwlEnumDO> list = bwlEnumService.findList(query);
        Map<String, Object> map = Maps.newHashMap();
        map.put("rows", null == list ? new ArrayList<BwlEnumDO>() : list);
        map.put("total", count);
        return map;
    }

    protected HttpResult<BwlEnumDO> doDetail(Integer id) {
        ResultDO<BwlEnumDO> db = bwlEnumService.getById(id);
        return HttpResult.success(db.getEntry());
    }

    protected HttpResult<Boolean> doDelete(Integer id) {
        ResultDO<BwlEnumDO> db = bwlEnumService.getById(id);
        if (!db.isSuccess() || null == db.getEntry()) { // 不存在
            return HttpResult.failure("数据不存在或已被删除");
        }

        BlackWhiteListQuery query = new BlackWhiteListQuery();
        String bizCode = db.getEntry().getBizCode();
        query.setBizCode(bizCode);
        int count = blackWhiteListService.count(query);
        if (count > 0) {
            return HttpResult.failure(String.format("存在业务代码为[%s]的名单配置,请先删除名单配置", bizCode));
        }

        ResultDO<Boolean> operate = bwlEnumService.deleteById(id);
        if (!operate.isSuccess()) {
            return HttpResult.failure(operate.getMessage());
        }

        return HttpResult.success();
    }

}
