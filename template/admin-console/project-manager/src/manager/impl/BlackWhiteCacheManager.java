/*
 * @(#)BlackWhiteCacheManager.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.manager.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import ${packagePrefix}.common.AppConstants;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.enums.BlackWhiteEnum;
import ${packagePrefix}.dao.BlackWhiteListDAO;
import ${packagePrefix}.dao.BwlEnumDAO;
import ${packagePrefix}.dataobject.BlackWhiteListDO;
import ${packagePrefix}.dataobject.BwlEnumDO;
import ${packagePrefix}.query.BlackWhiteListQuery;
import ${packagePrefix}.query.BwlEnumQuery;
import ${packagePrefix}.support.SystemConfigs;

import lombok.extern.slf4j.Slf4j;

/**
 * BlackWhiteCacheManager
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BlackWhiteCacheManager.java ${currentTime} Exp $
 */
@Slf4j
@Component
public class BlackWhiteCacheManager {

    public static final ConcurrentMap<String, Set<String>> innerGroupMap = new ConcurrentHashMap<>();

    @Resource
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private BlackWhiteListDAO blackWhiteDAO;
    @Autowired
    private BwlEnumDAO bwlEnumDAO;

    @Autowired
    private ThreadPoolTaskExecutor threadTaskExecutor;
    
    @Autowired
    private SystemConfigs systemConfigs;

    @PostConstruct
    public void init() {
        Timer cacheRefreshTimer = new Timer("bwlCacheRefresher", true);
        try {
            cacheRefreshTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    log.warn("Start refresh BlackWhiteList cache routine");
                    try {
                        doFreshCache(true);
                    } catch (Throwable throwable) {
                        log.warn("error when refresh ", throwable);
                    }
                }
            }, 30 * 1000L, systemConfigs.getBwlCacheRefreshInterval() * 1000L);
        } catch (Throwable throwable) {
            log.warn("error when schedule cache refresh timer");
            throw throwable;
        }
    }

    protected void doFreshCache(boolean checkSharedLock) {
        if(checkSharedLock) {
            // 获取共享锁、没取到、就返回、取到了就刷新。
            if (!aquireRefreshToken()) {
                log.warn("No token get to fresh BlackWhiteList Cache");
                return;
            }
        }
        // 查询为空的分组记录
        BwlEnumQuery beQuery = new BwlEnumQuery();
        beQuery.setPageSize(1);
        beQuery.setPageSize(500);
        List<BwlEnumDO> bwlist = bwlEnumDAO.queryList(beQuery);
        for (BwlEnumDO bizEnumDO : bwlist) {
            BlackWhiteListQuery query = new BlackWhiteListQuery();
            query.setBizCode(bizEnumDO.getBizCode());
            int num = blackWhiteDAO.count(query);
            if(0 >= num) {
                cleanGroupByCodeWithAllTypes(bizEnumDO.getBizCode());
            }
        }

        // 重新加载
        List<BlackWhiteListDO> list = Lists.newArrayList();
        BlackWhiteListQuery query = new BlackWhiteListQuery();
        int total = blackWhiteDAO.count(query);

        int PER_PAGE = 1000; // 1000每页
        int pages = total%PER_PAGE > 0 ? (total/PER_PAGE + 1) : (total/PER_PAGE);
        query.setPageSize(PER_PAGE);
        query.setPageNo(1);
        query.setForceIndex(BlackWhiteListQuery.IDX_CREATETIME);
        query.addOrderBy("gmt_create", true);
        query.addOrderBy("id", true);
        long maxIdOfPage = 0;
        Date lastGmtCreate = null;
        for (int i = 1; i <= pages; i++) {
            if(maxIdOfPage > 0 && null != lastGmtCreate) {
                query.setMaxPageId(maxIdOfPage);
                query.setLastGmtCreate(lastGmtCreate);
            }
            List<BlackWhiteListDO> queryR = blackWhiteDAO.queryList(query);
            if(null != queryR && queryR.size() > 0) {
                maxIdOfPage = queryR.get(queryR.size() - 1).getId();
                lastGmtCreate = queryR.get(queryR.size() - 1).getGmtCreate();
                list.addAll(queryR);
            }
        }
        log.warn("refreshAllCache BlackWhiteList, totalNum=" + list.size());

        // key : valueList
        Multimap<String, String> multimap = ArrayListMultimap.create();
        for (BlackWhiteListDO param : list) {
            multimap.put(cacheKey(param.getBizCode(), param.getType()), param.getIdentity());
        }
        Map<String, Collection<String>> listMap = multimap.asMap();
        for (Map.Entry<String, Collection<String>> e : listMap.entrySet()) {
            refreshOneGroupList(e.getKey(), e.getValue());
        }

    }

    public void cleanGroupByCodeWithAllTypes(String bizCode) {
        int[] typeArr = new int[] {
            AppConstants.BlackWhiteList.BLACK_LIST, 
            AppConstants.BlackWhiteList.WHITE_LIST, 
            AppConstants.BlackWhiteList.CONFIG_LIST
        };

        for (int i = 0; i < typeArr.length; i++) {
            String cacheKey = cacheKey(bizCode, typeArr[i]);
            log.warn("Clean BlackWhiteList groups, cacheKey={}", cacheKey);
            refreshOneGroupList(cacheKey, Collections.emptyList());
        }
    }

    protected void refreshOneGroupList(String cacheKey, Collection<String> list) {
        if(Strings.isBlank(cacheKey)) {
            return;
        }
        stringRedisTemplate.delete(cacheKey);
        Set<String> set = null == list ? new HashSet<String>() : Sets.newHashSet(list);
        innerGroupMap.put(cacheKey, set);

        if(set.size() > 0) {
            Long count = stringRedisTemplate.opsForSet().add(cacheKey, Lists.newArrayList(set).toArray(new String[0]));
            log.warn("refreshOneGroupList BlackWhiteList key:{}, count:{}", cacheKey, count);
        }
    }

    public void deleteCacheElement(String cacheKey, Object ... elem) {
        if(Strings.isBlank(cacheKey) || 0 >= BizObjects.length(elem)) {
            return;
        }
        Long count = stringRedisTemplate.opsForSet().remove(cacheKey, elem);
        log.warn("deleteCacheElement BlackWhiteList key:{}, elements:{}, successCount={}", cacheKey, Lists.newArrayList(elem), count);
        if(null != innerGroupMap.get(cacheKey)) {
            List<String> elemList = Lists.newArrayList();
            for(Object obj : elem) {
                if(null == obj) {
                    continue;
                }
                elemList.add(obj.toString());
            }
            innerGroupMap.get(cacheKey).removeAll(elemList);
        }
    }

    public void addCacheElement(String cacheKey, Object... elem) {
        if(Strings.isBlank(cacheKey) || 0 >= BizObjects.length(elem)) {
            return;
        }
        List<String> elemList = Lists.newArrayList();
        for(Object obj : elem) {
            if(null == obj) {
                continue;
            }
            elemList.add(obj.toString());
        }

        Set<String> set = stringRedisTemplate.opsForSet().members(cacheKey);
        if (set == null) {
            set = Sets.newHashSet();
        }
        set.addAll(elemList);
        stringRedisTemplate.opsForSet().add(cacheKey, set.toArray(new String[]{}));

        innerGroupMap.computeIfAbsent(cacheKey, k -> Sets.newHashSet());
        innerGroupMap.get(cacheKey).addAll(elemList);
        log.info("addCacheElement BlackWhiteList key:{}, elements:{}", cacheKey, Lists.newArrayList(elemList));

    }
    
    public boolean aquireRefreshToken() {
        //生成每小时唯一Key
        long time = System.currentTimeMillis() / 1000;
        time = time - time % systemConfigs.getBwlCacheRefreshInterval();
        String tokenKey = RedisKeyConstants.generateBwlRefreshTokenKey(time);
        log.info("refreshToken key={}", tokenKey);
        //设置共享锁
        boolean lockSuccess = stringRedisTemplate.opsForValue().setIfAbsent(tokenKey, "true");
        //设置不成功、没拿到锁权限
        if (!lockSuccess) {
            return false;
        }
        //设置成功拿到锁权限
        stringRedisTemplate.expire(tokenKey, systemConfigs.getBwlCacheRefreshInterval(), TimeUnit.SECONDS);
        return true;
    }

    public Set<String> members(String bizCode, int type) {
        String key = cacheKey(bizCode, type);
        if(null != innerGroupMap.get(key) && !innerGroupMap.get(key).isEmpty()) {
            return innerGroupMap.get(key);
        }
        try {
            return stringRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            String msg = Strings.format("Redis查询SET集合key={}", key);
            log.error(msg, e);
        }

        return Sets.newHashSet();
    }

    /**
     * 集合中是否存在某值
     */
    public boolean has(String bizCode, int type, String value) {
        if(null == value) {
            return false;
        }
        String key = cacheKey(bizCode, type);
        if(null != innerGroupMap.get(key) && innerGroupMap.get(key).contains(value)) {
            return true;
        }
        try {
            Boolean has = stringRedisTemplate.opsForSet().isMember(key, value);
            return (null != has && has);
        } catch (Exception e) {
            String msg = Strings.format("Redis查询value={}是否存在于SET集合key={}", value, key);
            log.error(msg, e);
        }

        return false;
    }

    public boolean isMember(String setKey, String value) {
        if(null == value) {
            return false;
        }
        if(null != innerGroupMap.get(setKey) && innerGroupMap.get(setKey).contains(value)) {
            return true;
        }
        try {
            Boolean has = stringRedisTemplate.opsForSet().isMember(setKey, value);
            return (null != has && has);
        } catch (Exception e) {
            String msg = Strings.format("Redis查询value={}是否存在于SET集合key={}", value, setKey);
            log.error(msg, e);
        }

        return false;
    }

    /**
     * 放入一个有失效期的key
     */
    public void put(String key, int seconds) {
        try {
            stringRedisTemplate.opsForValue().set(key, "1", seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    public boolean has(String key) {
        if(null == key) {
            return false;
        }
        String val = stringRedisTemplate.opsForValue().get(key);
        return Objects.equals(val, "1");
    }

    public synchronized boolean rebuildCache() {

        threadTaskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                doFreshCache(false);
            }
        });

        return true;
    }

    public synchronized boolean rebuildOneGroupCache(final String bizCode) {

        threadTaskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                BlackWhiteListQuery query = new BlackWhiteListQuery();
                query.setBizCode(bizCode);
                int total = blackWhiteDAO.count(query);
                int PER_PAGE = 500; // 500每页
                int pages = total%PER_PAGE > 0 ? (total/PER_PAGE + 1) : (total/PER_PAGE);
                query.setPageSize(PER_PAGE);
                query.setPageNo(1);
                // query.setForceIndex(BlackWhiteListQuery.IDX_BIZCODE_CREATETIME);
                query.addOrderBy("gmt_create", true);
                query.addOrderBy("id", true);
                List<BlackWhiteListDO> list = Lists.newArrayList();
                long maxIdOfPage = 0;
                Date lastGmtCreate = null;
                for (int i = 1; i <= pages; i++) {
                    if(maxIdOfPage > 0 && null != lastGmtCreate) {
                        query.setMaxPageId(maxIdOfPage);
                        query.setLastGmtCreate(lastGmtCreate);
                    }
                    List<BlackWhiteListDO> queryR = blackWhiteDAO.queryList(query);
                    if(null != queryR && queryR.size() > 0) {
                        maxIdOfPage = queryR.get(queryR.size() - 1).getId();
                        lastGmtCreate = queryR.get(queryR.size() - 1).getGmtCreate();
                        list.addAll(queryR);
                    }
                }
                log.warn("rebuildOneGroupCache BlackWhiteList, bizCode={}, totalNum={}", bizCode, list.size());

                Multimap<String, String> multimap = ArrayListMultimap.create();
                for (BlackWhiteListDO param : list) {
                    multimap.put(cacheKey(param.getBizCode(), param.getType()), param.getIdentity());
                }
                Map<String, Collection<String>> listMap = multimap.asMap();
                for (Map.Entry<String, Collection<String>> e : listMap.entrySet()) {
                    refreshOneGroupList(e.getKey(), e.getValue());
                }
            }
        });

        return true;
    }

    public String cacheKey(String bizCode, int type) {
        return BlackWhiteEnum.groupCacheKey(bizCode, type);
    }

    public ConcurrentMap<String, Set<String>> getInnerMap() {
        return innerGroupMap;
    }

}
