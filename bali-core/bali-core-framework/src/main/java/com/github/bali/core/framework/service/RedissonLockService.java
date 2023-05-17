package com.github.bali.core.framework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁增强服务类
 * 用于对RedissonClient进行封装，提供更加便捷的分布式锁操作
 * @author Petty
 * @version 1.0.0
 */
@Slf4j
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class RedissonLockService {

    private final RedissonClient redissonClient;

    /**
     * 上锁，看门狗机制，会自动延长锁的持有时间
     *
     * @param key key
     * @return 是否上锁成功
     */
    public boolean look(String key) {
        RLock rLock = redissonClient.getLock(key);
        return rLock.tryLock();
    }

    /**
     * 上锁
     *
     * @param waitSeconds  等待时间 秒
     * @param leaseSeconds 超时时间 秒
     * @param key          key
     * @return 是否上锁成功
     */
    public boolean look(long waitSeconds, long leaseSeconds, String key) {
        RLock rLock = redissonClient.getLock(key);
        try {
            return rLock.tryLock(waitSeconds, leaseSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("上锁失败", e);
            return false;
        }
    }

    /**
     * 上锁 ，看门狗机制，会自动延长锁的持有时间
     *
     * @param waitSeconds 等待时间 秒
     * @param key         key
     * @return 是否上锁成功
     */
    public boolean look(long waitSeconds, String key) {
        RLock rLock = redissonClient.getLock(key);
        try {
            return rLock.tryLock(waitSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("上锁失败", e);
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param key key
     */
    public void unLook(String key) {
        RLock rLock = redissonClient.getLock(key);
        rLock.unlock();
    }
}
