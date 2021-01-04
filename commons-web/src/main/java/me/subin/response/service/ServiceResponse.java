package me.subin.response.service;

import lombok.Data;

/**
 * @author bin
 * @ClassName ServiceResponse
 * @Description TODO
 * @date 2020/11/8 13:50
 */
@Data
public class ServiceResponse<T> {
    private Boolean success;
    private String message;
    private T data;
}
