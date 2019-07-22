package com.px.myshiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

/**
 * User: gxd
 * Date: 2019/7/22
 * Time: 14:51
 * Version:V1.0
 */
public class RedisCacheManager implements CacheManager {
    @Resource
    private RedisCache redisCache;
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
