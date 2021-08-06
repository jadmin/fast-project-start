package ${packagePrefix}.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.dto.BatchOperationDTO;
import com.github.javaclub.sword.spring.BeanCopierUtils;
import com.github.javaclub.sword.util.DateUtil;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import com.github.javaclub.sword.web.PageResultSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import ${packagePrefix}.common.AppConstants;
import ${packagePrefix}.common.AppConstants.BlackWhiteList;
import ${packagePrefix}.common.AppConstants.RedisKeys;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.enums.RedisPublishMessageChannel;
import ${packagePrefix}.common.model.bo.BwlCacheRefreshBO;
import ${packagePrefix}.dataobject.BlackWhiteListDO;
import ${packagePrefix}.dataobject.BwlEnumDO;
import ${packagePrefix}.manager.impl.BlackWhiteCacheManager;
import ${packagePrefix}.query.BlackWhiteListQuery;
import ${packagePrefix}.query.BwlEnumQuery;
import ${packagePrefix}.service.BlackWhiteListService;
import ${packagePrefix}.service.BwlEnumService;
import ${packagePrefix}.support.RedisCacheManager;
import ${packagePrefix}.view.BlackWhiteListView;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/admin/bwl")
@Slf4j
public class BlackWhiteController extends AdminControllerBase {
    
    @Autowired
    private BlackWhiteListService blackWhiteService;

    @Autowired
    private BwlEnumService bwlEnumService;

    @Autowired
    private BlackWhiteCacheManager blackWhiteCacheManager;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;
    
    @Autowired
    private RedisCacheManager redisCacheManager;

    @RequestMapping(value = "/saveRequest.do", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Long> save(HttpServletRequest request) throws Exception {
        BlackWhiteListDO bwd = WebUtil.parseObject(request, BlackWhiteListDO.class);
        try {
            BizObjects.requireNotNullGtZero(bwd.getType(), "请设置名单类型");
            BizObjects.requireNotEmpty(bwd.getBizCode(), "请选择业务代码");
            BizObjects.requireNotEmpty(bwd.getIdentity(), "名单值不能为空");
            BizObjects.requireNotNullGtZero(bwd.getExpireType(), "有效期不能为空");
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
        String identity = StringUtils.replaceChars(bwd.getIdentity(), "，", ",");
        String[] array = Strings.splitAndTrim(identity, ",");
        Date expireTime = this.parseExpireTime(bwd);
        if (null != array && 1 == array.length) {
            BlackWhiteListQuery query = new BlackWhiteListQuery();
            query.setBizCode(bwd.getBizCode());
            query.setType(bwd.getType());
            query.setIdentity(bwd.getIdentity().trim());
            query.setIsDeleted(0);
            int count = blackWhiteService.count(query);
            if(count > 0) {
                return HttpResult.failure("该数据记录已经存在");
            }
            bwd.setExpireTime(expireTime);
            bwd.setIdentity(bwd.getIdentity().trim());
            bwd.setCreatorId(getLoginUserId());
            bwd.setCreator(getLoginUsername());
            bwd.setIsDeleted(0);
            HttpResult<Long> saveR = save(bwd);

            return saveR;
        }
        List<BlackWhiteListDO> batchList = Lists.newArrayList();
        Set<String> repeatValue = Sets.newHashSet();
        for (String param : array) {
            if(Strings.isBlank(param)) {
                continue;
            }
            boolean has = blackWhiteCacheManager.has(bwd.getBizCode(), bwd.getType(), param.trim());
            if(has || repeatValue.contains(param.trim())) { // 已经存在于缓存中，不插入DB
                continue;
            }
            repeatValue.add(param.trim());
            BlackWhiteListDO bwl = new BlackWhiteListDO();
            BeanCopierUtils.copyProperties(bwd, bwl);
            bwl.setExpireTime(expireTime);
            bwl.setIdentity(param.trim());
            bwl.setCreatorId(getLoginUserId());
            bwl.setCreator(getLoginUsername());
            bwd.setIsDeleted(0);
            batchList.add(bwl);
        }
        BatchOperationDTO<BlackWhiteListDO> batchDTO = blackWhiteService.createBatch(batchList);
        if(null != batchDTO && null != batchDTO.getSuccessList() && batchDTO.getSuccessList().size() > 0) {
        	BwlCacheRefreshBO refreshBO = new BwlCacheRefreshBO();
            refreshBO.setBizCode(bwd.getBizCode());
            refreshBO.setBwlType(bwd.getType());
            refreshBO.setActionType(BlackWhiteList.ACTION_TYPE_BATCH_ADD);
            String message = JSON.toJSONString(refreshBO);
            stringRedisTemplate.convertAndSend(RedisPublishMessageChannel.BLACK_WHITE_NAME.getCode(), message);
            log.info("发送黑白名单变更消息，bizCode={}, message={}", bwd.getBizCode(), message);
        }

        return HttpResult.success(1L);
    }

    Date parseExpireTime(BlackWhiteListDO bwd) {
        Date now = new Date();
        switch (bwd.getExpireType()) {
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_1D:
                return DateUtil.plusDay(now, 1);
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_3D:
                return DateUtil.plusDay(now, 3);
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_10D:
                return DateUtil.plusDay(now, 10);
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_1M:
                return DateUtil.plusDay(now, 30);
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_3M:
                return DateUtil.plusDay(now, 90);
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_FOREVER:
                return AppConstants.BlackWhiteList.getMaxExpireTime();
            case AppConstants.BlackWhiteList.EXPIRE_TYPE_CUSTOM:
                return bwd.getExpireTime();
        }
        return null;
    }

    @RequestMapping(value = "/refreshCache.do", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Boolean> refreshCache() {
    	
    	String refreshTokenKey = RedisKeys.formatKey(RedisKeyConstants.BWL_CAHCE_REFRESH_TOKEN_WEB_ACTION, null);
    	boolean lockAquired = redisCacheManager.weakLock(refreshTokenKey, "true", 180L); // 3min内不允许频繁操作
    	if(!lockAquired) {
    		return HttpResult.failure("当前操作过于频繁, 请稍后再试！");
    	}

        BwlCacheRefreshBO refreshBO = new BwlCacheRefreshBO();
        refreshBO.setActionType(BlackWhiteList.ACTION_TYPE_RELOAD_ALL);
        String message = JSON.toJSONString(refreshBO);
        stringRedisTemplate.convertAndSend(RedisPublishMessageChannel.BLACK_WHITE_NAME.getCode(), message);
        log.info("发送刷新黑白名单缓存消息, message={}", message);

        return HttpResult.success();

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Long> save(@RequestBody BlackWhiteListDO post) throws Exception {
        post.setCreatorId(getLoginUserId());
        ResultDO<Boolean> operate = blackWhiteService.save(post);
        if (!operate.isSuccess()) {
            return HttpResult.failure(operate.getMessage());
        }

        BwlCacheRefreshBO refreshBO = new BwlCacheRefreshBO();
        refreshBO.setBizCode(post.getBizCode());
        refreshBO.setBwlType(post.getType());
        refreshBO.setActionType(BlackWhiteList.ACTION_TYPE_ADD);
        refreshBO.setIdentity(post.getIdentity());
        String message = JSON.toJSONString(refreshBO);
        stringRedisTemplate.convertAndSend(RedisPublishMessageChannel.BLACK_WHITE_NAME.getCode(), message);

        return HttpResult.success(post.getId());
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<BlackWhiteListDO> detail(Long id) {
        return doDetail(id);
    }

    @RequestMapping(value = "/queryTest", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<Boolean> queryTest(String bizCode, Integer type, String value) {
        boolean has = blackWhiteCacheManager.has(bizCode, type, value);
        if (has) {
            return HttpResult.success();
        }
        return HttpResult.failure("没符合");
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<BlackWhiteListDO> _detail(@PathVariable Long id) {
        return doDetail(id);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Boolean> delete(Long id) {
        return doDelete(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult<Boolean> _delete(@PathVariable Long id) {
        return doDelete(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<PageResultSet<BlackWhiteListDO>> list(@RequestBody BlackWhiteListQuery request) {
        QueryResult<BlackWhiteListDO> queryResult = blackWhiteService.findListWithCount(request);
        PageResultSet<BlackWhiteListDO> sets = new PageResultSet<>(request.getPageNo(), request.getPageSize());
        sets.setAllRow(queryResult.getTotalCount());
        sets.setList(queryResult.getEntry());
        return HttpResult.success(sets);
    }

    /**
     * 黑白名单列表数据
     */
    @RequestMapping(value = "/listData.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listData(HttpServletRequest request, Model model) {
        BlackWhiteListQuery query = WebUtil.parseObject(request, BlackWhiteListQuery.class);
        int count = blackWhiteService.count(query);
        List<BlackWhiteListDO> list = blackWhiteService.findList(query);
        Map<String, Object> map = Maps.newHashMap();
        map.put("rows", null == list ? new ArrayList<BlackWhiteListView>() : buildResultList(list));
        map.put("total", count);
        return map;
    }

    protected HttpResult<BlackWhiteListDO> doDetail(Long id) {
        ResultDO<BlackWhiteListDO> db = blackWhiteService.getById(id);
        return HttpResult.success(db.getEntry());
    }

    protected HttpResult<Boolean> doDelete(Long id) {
        ResultDO<BlackWhiteListDO> db = blackWhiteService.getById(id);
        if (!db.isSuccess() || null == db.getEntry()) { // 不存在
            return HttpResult.failure("数据不存在或已被删除");
        }
        BlackWhiteListDO bwd = db.getEntry();
        ResultDO<Boolean> operate = blackWhiteService.deleteById(id);
        if (!operate.isSuccess()) {
            return HttpResult.failure(operate.getMessage());
        }
        String cacheKey = blackWhiteCacheManager.cacheKey(bwd.getBizCode(), bwd.getType());
        blackWhiteCacheManager.deleteCacheElement(cacheKey, bwd.getIdentity());

        BwlCacheRefreshBO refreshBO = new BwlCacheRefreshBO();
        refreshBO.setBizCode(bwd.getBizCode());
        refreshBO.setBwlType(bwd.getType());
        refreshBO.setActionType(BlackWhiteList.ACTION_TYPE_DELETE);
        refreshBO.setIdentity(bwd.getIdentity());
        String message = JSON.toJSONString(refreshBO);
        stringRedisTemplate.convertAndSend(RedisPublishMessageChannel.BLACK_WHITE_NAME.getCode(), message);
        log.info("发送刷新黑白名单缓存消息, message={}", message);

        HttpResult deleteR = HttpResult.success();
        deleteR.addExtraData("deleteData", bwd);

        return deleteR;
    }

    List<BlackWhiteListView> buildResultList(List<BlackWhiteListDO> list) {
        List<BlackWhiteListView> result = Lists.newArrayList();

        Map<String, BwlEnumDO> bizMaps = getBizEnumsMap();
        for (BlackWhiteListDO param : list) {
            if (null != bizMaps.get(param.getBizCode())) {
                result.add(new BlackWhiteListView(param, bizMaps.get(param.getBizCode()).getBizDesc()));
            }
        }

        return result;
    }

    Map<String, BwlEnumDO> getBizEnumsMap() {
        Map<String, BwlEnumDO> map = Maps.newHashMap();
        BwlEnumQuery query = new BwlEnumQuery();
        query.setPageSize(1);
        query.setPageSize(500);
        List<BwlEnumDO> list = bwlEnumService.findList(query);
        if (BizObjects.length(list) > 0) {
            for (BwlEnumDO bizEnumDO : list) {
                map.put(bizEnumDO.getBizCode(), bizEnumDO);
            }
        }
        return map;
    }

}
