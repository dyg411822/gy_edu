package com.scb.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 操作工具。
 * 此工具类非静态，通过注入 RedisConfig 已经注入了此类。
 * @author R)
 */
@Slf4j
public final class RedisUtils {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // -------------------- 公共操作 -------------------- //
    /**
     * 指定键的失效时长，时间为负数则不进行设置。
     * @param key 键
     * @param time 期限(秒)
     */
    public boolean expire(String key, long time) {
        return Optional.ofNullable(redisTemplate.expire(key, time, TimeUnit.SECONDS)).orElse(false);
    }

    /**
     * 根据 key 获取过期时间。
     * @param key 键
     * @return 时间(秒) 返回 -1 代表为永久有效，-2 代表已经失效，其余为剩余有效时间。
     */
    public long getExpire(String key) {
        return Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(-2L);
    }

    /**
     * 判断 key 是否存在。
     * @param key 键
     * @return true 代表存在，false 代表不存在
     */
    public boolean exists(String key) {
        return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
    }

    /**
     * 通过模式获取匹配的键集合。
     * @param pattern 键的模式
     * @return 键 Set
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除键值（可批量）
     * @param key 可以传一个值或多个
     * @return true 代表所有删除成功或者键为空则，false 删除失败
     */
    public boolean del(String... key) {
        if (key != null) {
            long count = Optional.ofNullable(redisTemplate.delete(Arrays.asList(key))).orElse(0L);
            return count > 0;
        }
        return true;
    }

    /**
     * 获取键值
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 键值存储，如果键本身不存在则创建，否则更新内容。
     * @param key 键
     * @param value 值
     * @return true 成功 false 失败
     */
    public boolean set(String key, Object value) {
        return set(key, value, -1);
    }

    /**
     * 键值存储，如果键本身不存在则创建，否则更新内容。
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time 大于 0 则按照此时间设置期限，否则代表无限期
     * @return true 成功/false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis 存入数据失败:键:{}", key, e);
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于 0)
     * @return 递增之后的结果
     */
    public long incr(String key, long delta) {
        if (delta <= 0) {
            throw new RuntimeException("递增因子必须大于 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return 递减之后的结果
     */
    public long decr(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于 0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    // -------------------- 公共操作 结束 -------------------- //

    // -------------------- Hash 类型操作 -------------------- //
    /**
     * Hash 获取值
     * @param key 键 不能为 null
     * @param field 项 不能为 null
     * @return 值
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取键所有的项
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置键值
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hSet(String key, Map<String, Object> map) {
        return hSet(key, map, -1);
    }

    /**
     * 设置键值并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 期限(秒)，仅当期限大于 0 才进行设置，否则设置为无限制。
     * @return true成功 false失败
     */
    public boolean hSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis Hash 存储失败:键:{}", key, e);
            return false;
        }
    }

    /**
     * 向一张 hash 表中放入数据,如果不存在将创建
     * @param key 键
     * @param field 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String field, Object value) {
        return hSet(key, field, value, -1);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param field 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String field, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if(time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis 存储失败:键:{},节点:{}", key, field, e);
            return false;
        }
    }

    /**
     * 删除 hash 表中的节点
     * @param key 键 不能为null
     * @param fields 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 检查 hash 表中是否有该项
     * @param key 键 不能为null
     * @param field 项 不能为null
     * @return true 存在/false 不存在
     */
    public boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * hash 递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param field 项
     * @param value 要增加几(大于0)
     * @return 递增后的值
     */
    public long hIncrBy(String key, String field, long value) {
        return redisTemplate.opsForHash().increment(key, field, value);
    }

    /**
     * hash 递减
     * @param key 键
     * @param field 项
     * @param value 要减少记(小于0)
     * @return 递减后的值
     */
    public double hDecr(String key, String field, long value) {
        return redisTemplate.opsForHash().increment(key, field, -value);
    }

    /**
     * hash 递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param field 项
     * @param value 要增加几(大于0)
     * @return 递增后的值
     */
    public double hIncrByFloat(String key, String field, double value) {
        return redisTemplate.opsForHash().increment(key, field, value);
    }

    /**
     * hash 递减
     * @param key 键
     * @param field 项
     * @param value 要减少记(小于0)
     * @return 递减后的值
     */
    public double hDecrByFloat(String key, String field, double value) {
        return redisTemplate.opsForHash().increment(key, field, -value);
    }
    // -------------------- Hash 类型操作 结束 -------------------- //

    // -------------------- Set 类型操作 -------------------- //
    /**
     * 根据 key 获取 Set 中的所有值
     * @param key 键
     * @return 列表中的内容
     */
    public Set<Object> sMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("Redis 获取 Set 的值失败，键:{}", key, e);
            return null;
        }
    }

    /**
     * 查询一个值时候键的一个成员。
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sIsMember(String key, Object value) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().isMember(key, value)).orElse(false);
        } catch (Exception e) {
            log.error("Redis 查询一个值是否存在于 Set 中失败，键:{}", key, e);
            return false;
        }
    }

    /**
     * 将数据放入 set 缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sAdd(String key, Object... values) {
        return sAdd(key, -1, values);
    }

    /**
     * 将 Set 数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sAdd(String key, long time, Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time > 0) {
                expire(key, time);
            }
            return Optional.ofNullable(count).orElse(0L);
        } catch (Exception e) {
            log.error("Redis 将数据存入 Set 中失败，键:{}", key, e);
            return 0;
        }
    }

    /**
     * 获取 Set 中元素个数
     * @param key 键
     * @return Set 中元素的个数
     */
    public long sCard(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().size(key)).orElse(0L);
        } catch (Exception e) {
            log.error("Redis 获取 Set 中元素个数失败，键:{}", key, e);
            return 0;
        }
    }

    /**
     * Set 中移除值为 value 的数据
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sRem(String key, Object ...values) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().remove(key, values)).orElse(0L);
        } catch (Exception e) {
            log.error("Redis 从 Set 中移除成员失败，键:{}", key, e);
            return 0;
        }
    }
    // -------------------- Set 类型操作 结束 -------------------- //

    // -------------------- List 类型操作 -------------------- //
    /**
     * 获取 list 缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1 代表所有值
     * @return 截取列表的值
     */
    public List<Object> lRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis 获取列表部分内容失败，键:{}，从:{}到{}", key, start, end, e);
            return null;
        }
    }

    /**
     * 获取 list 缓存的长度
     * @param key 键
     * @return 列表的长度
     */
    public long lLen(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
        } catch (Exception e) {
            log.error("Redis 获取列表长度失败，键:{}", key, e);
            return 0;
        }
    }

    /**
     * 通过索引获取 list 中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 存在列表中的元素
     */
    public Object lIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("Redis 从列表中获取值失败，键:{}", key, e);
            return null;
        }
    }

    /**
     * 将元素放入缓存列表中
     * @param key 键
     * @param value 值
     * @return true 代表成功/false 代表失败
     */
    public boolean lSet(String key, Object value) {
        return rPush(key, value, -1);
    }

    /**
     * 将元素放入缓存列表中
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return true 代表成功/false 代表失败
     */
    public boolean rPush(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis 插入列表中的值失败，键:{}", key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return true 代表成功/false 代表失败
     */
    public boolean rPush(String key, List<Object> value) {
        return rPush(key, value, -1);
    }

    /**
     * 将列表元素放入缓存列表中，当且仅当期限大于 0 设置列表的过期时间。
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return true 代表成功/false 代表失败
     */
    public boolean rPush(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis 插入列表中的值失败，键:{}", key, e);
            return false;
        }
    }

    /**
     * 根据索引修改 list 中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return true 代表成功/false 代表失败
     */
    public boolean lSet(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("Redis 更新列表中的值失败，键:{}，索引:{}", key, index, e);
            return false;
        }
    }

    /**
     * 移除 N 个值为 value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRem(String key, long count, Object value) {
        try {
            return Optional.ofNullable(redisTemplate.opsForList().remove(key, count, value)).orElse(0L);
        } catch (Exception e) {
            log.error("Redis 批量从列表中移除值失败，键:{}", key, e);
            return 0;
        }
    }
    // -------------------- Set 类型操作 结束 -------------------- //

    /**
     * 使用 Redis 的消息队列
     * @param channel 频道
     * @param message 消息内容
     */
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    // ========= BoundListOperations start ============
    /**
     * 将数据添加到 Redis 的 list 中（表尾添加）
     * @param listKey 列表的键
     * @param expireTime 有效期(秒)
     * @param values 待添加的数据
     */
    public void addToListRight(String listKey, long expireTime, Object... values) {
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        // 插入数据
        boundValueOperations.rightPushAll(values);
        // 设置过期时间
        boundValueOperations.expire(expireTime, TimeUnit.SECONDS);
    }
    /**
     * 根据起始结束序号遍历Redis中的list
     * @param listKey 列表的键
     * @param start  起始序号
     * @param end  结束序号
     * @return 选取部分列表的值
     */
    public List<Object> rangeList(String listKey, long start, long end) {
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //查询数据
        return boundValueOperations.range(start, end);
    }
    /**
     * 弹出右边的值 --- 并且移除这个值
     * @param listKey 列表的键
     */
    public Object rightPop(String listKey) {
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }
    // ========= BoundListOperations 结束 ============
}
