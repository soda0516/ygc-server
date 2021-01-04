package me.subin.response.service;

/**
 * @author bin
 * @ClassName Service层调用，返回结果用的
 * @Description TODO
 * @date 2020/11/8 13:50
 */
public class ServiceResponseBuilder {
    public static <T> ServiceResponse<T> success(){
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.setSuccess(true);
        serviceResponse.setMessage("调用成功");
        return serviceResponse;
    }
    public static <T> ServiceResponse<T> success(T data){
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.setSuccess(true);
        serviceResponse.setMessage("调用成功");
        serviceResponse.setData(data);
        return serviceResponse;
    }
    public static <T> ServiceResponse<T> error(String message){
        ServiceResponse<T> serviceResponse = new ServiceResponse<>();
        serviceResponse.setSuccess(false);
        serviceResponse.setMessage(message);
        return serviceResponse;
    }
}
