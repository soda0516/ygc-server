package me.subin.response.controller;

/**
 * @Describe
 * @Author orang
 * @Create 2019/2/13 15:39
 **/
public class ResponseModel<T> {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private T data;
    private String message;
}
