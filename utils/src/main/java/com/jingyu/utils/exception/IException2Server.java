package com.jingyu.utils.exception;

/**
 * @author  fengjingyu@foxmail.com
 * @description
 */
public interface IException2Server {
    /**
     * 回调接口
     */
    void uploadException2Server(String info, Throwable ex, Thread thread, ExceptionInfo model, ExceptionDb exceptionModelDb);
}
