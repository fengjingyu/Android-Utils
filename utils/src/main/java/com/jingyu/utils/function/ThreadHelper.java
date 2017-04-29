package com.jingyu.utils.function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fengjingyu@foxmail.com
 *  获取单例的线程池
 */
public class ThreadHelper {

    private ThreadHelper() {
    }

    private static class SingleThreadPool {
        private static final ExecutorService instance = Executors.newSingleThreadExecutor();
    }

    private static class CacheThreadPool {
        private static final ExecutorService instance = Executors.newCachedThreadPool();
    }

    private static class FixThreadPool {
        private static final ExecutorService instance = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public static ExecutorService getSingle() {
        return SingleThreadPool.instance;
    }

    public static ExecutorService getCache() {
        return CacheThreadPool.instance;
    }

    public static ExecutorService getFix() {
        return FixThreadPool.instance;
    }


}
