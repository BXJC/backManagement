package org.csu.mypetstore.domain;

/***
*  @Author: zzy
*  @date: 2020-6-21
*  @description: 统一返回格式的定义
***/

public class AppResult<T> {
    private int code;
    private String message;
    private T data;

    public AppResult() {
    }

    public AppResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



}
