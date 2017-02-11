package com.jingyu.utils.function.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fengjingyu@foxmail.com
 * @description 获取单例的线程池
 */
public class ExecutorManager {

    private ExecutorManager() {
    }

    private static class SinglePool {
        private static final ExecutorService instance = Executors.newSingleThreadExecutor();
    }

    private static class CachePool {
        private static final ExecutorService instance = Executors.newCachedThreadPool();
    }

    private static class FixPool {
        private static final ExecutorService instance = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public static ExecutorService getSingle() {
        return SinglePool.instance;
    }

    public static ExecutorService getCache() {
        return CachePool.instance;
    }

    public static ExecutorService getFix() {
        return FixPool.instance;
    }


}
