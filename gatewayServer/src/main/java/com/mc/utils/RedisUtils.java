package com.mc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author simpleMei
 * @since 2023-05-14 13:37:33
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存基本对象  Integer、String、实体类
     *
     * @param key 缓存key
     * @param value 缓存value
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本对象  Integer、String、实体类
     *
     * @param key 缓存key
     * @param value 缓存value
     * @param timeout 时间
     * @param unit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 设置有效时间
     *
     * @param key 键值
     * @param timeout 时长
     * @param unit 时间颗粒度 TimeUnit.DAYS 天 HOURS 时 MINUTES 分 SECONDS 秒 MILLISECONDS 毫秒 NANOSECONDS 毫微秒  MICROSECONDS 微秒
     * @return true or false
     */
    public boolean expire(final String key, final long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置有效时间
     *
     * @param key key
     * @param timeout 时长
     * @return true or false
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取有效时间
     *
     * @param key key
     * @return 有效时长
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key key
     * @return true or false
     */
    public boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取缓存数据
     *
     * @param key key
     * @return data
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 根据key删除缓存对象
     *
     * @param key key
     * @return true or false
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection collection
     * @return 操作数量
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存list 对象
     *
     * @param key key
     * @param list list
     * @return 数量
     */
    public <T> long setCacheList(final String key, final List<T> list) {
        Long count = redisTemplate.opsForList().rightPushAll(key, list);
        return count == null ? 0 : count;
    }

    /**
     * 获取缓存list
     *
     * @param key key
     * @return list
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存set
     *
     * @param key key
     * @param set set
     * @return 缓存的数据对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> set) {
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            setOperations.add(it.next());
        }
        return setOperations;
    }

    /**
     * 获取缓存set
     *
     * @param key key
     * @return set
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存map
     *
     * @param key key
     * @param dataMap map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获取缓存map
     *
     * @param key key
     * @return map
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 缓存hash数据
     *
     * @param key key
     * @param hashKey hashKey
     * @param value value
     */
    public <T> void setCacheMapValue(final String key, final String hashKey, final T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取hash数据
     *
     * @param key  key
     * @param hashKey hashKey
     * @return value
     */
    public <T> T getCacheMapValue(final String key, final String hashKey) {
        HashOperations<String, String, T> operations = redisTemplate.opsForHash();
        return operations.get(key, hashKey);
    }

    /**
     * 删除hash
     *
     * @param key key
     * @param hashKey hashKey
     */
    public void deleteCacheMapValue(final String key, final String hashKey) {
        HashOperations operations = redisTemplate.opsForHash();
        operations.delete(key, hashKey);
    }

    /**
     * 获取缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> getKeys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

}
