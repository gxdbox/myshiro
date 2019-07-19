package com.px.myshiro.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * User: gxd
 * Date: 2019/7/6
 * Time: 7:13
 * Version:V1.0
 */
@Component
public class JedisUtil {

    @Resource
    public JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }


    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try {
            jedis.set(key,value);
            return value;
        } finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        try {
            jedis.expire(key,i);
        } finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis resource = getResource();
        try {
            return resource.get(key);
        } finally {
            resource.close();
        }
    }

    public void del(byte[] key) {
        Jedis resource = getResource();
        try {
            resource.del(key);
        } finally {
            resource.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis resource = getResource();
        try {
            return resource.keys((shiro_session_prefix+"*").getBytes());
        } finally {
            resource.close();
        }
    }
}
