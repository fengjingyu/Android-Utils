package com.xiaocoder.utils.function.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaocoder 2014-10-17 下午1:52:54
 * @email fengjingyu@foxmail.com
 * @description 线程池
 */
public class XCExecutor {

    private XCExecutor() {
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
