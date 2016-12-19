package com.jingyu.utils.exception;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface IException2Server {
    /**
     * 回调接口
     */
    void uploadException2Server(String info, Throwable ex, Thread thread, ExceptionBean model, ExceptionDb exceptionModelDb);
}
