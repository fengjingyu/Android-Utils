package com.xiaocoder.utils.exception;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface XCIException2Server {
    /**
     * 回调接口
     */
    void uploadException2Server(String info, Throwable ex, Thread thread, XCExceptionModel model, XCExceptionModelDb exceptionModelDb);
}
