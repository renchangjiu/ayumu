package com.douqz.exception;

/**
 * 表示暂时不支持某些方法
 *
 * @author yui
 */
public class NotSupportCurrentlyException extends RuntimeException {

    public NotSupportCurrentlyException(String message) {
        super("Currently not supported this method: " + message);
    }

    public NotSupportCurrentlyException() {
        super("Currently not supported this method.");
    }

}
