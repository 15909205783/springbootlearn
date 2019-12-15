package com.neo.springbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheOperationService {
//    @Autowired
//    private StringRedisTemplate redisTemplate;
@Autowired
private RedisTemplate redisTemplate;
    public void setValues() {
        try {
            redisTemplate.opsForValue().set("aaa", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得制定格式的所有的key键
     *
     * @param patens 匹配的表达式
     * @return key的集合
     */
    public Set getKeys(String patens) {
        try {
            return redisTemplate.keys(patens);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0L) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
