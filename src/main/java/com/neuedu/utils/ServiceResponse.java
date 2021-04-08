package com.neuedu.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 封装前端返回的统一实体类
 * ***/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> {
    private int status;//状态：0：接口调用成功
    private T data;//当status为0时，将返回的数据封装到data中
    private String msg;//提示信息

    private ServiceResponse() {
    }

    private ServiceResponse(int status) {
        this.status = status;
    }

    private ServiceResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }
    @JsonIgnore
    public boolean isSuccess() {
        return this.status==0;
    }

    public static ServiceResponse createServiceResponseBySuccess(){
        return new ServiceResponse(0);
    }
    public static <T> ServiceResponse createServiceResponseBySuccess(T data){
        return new ServiceResponse(0, data);
    }
    public static <T> ServiceResponse createServiceResponseBySuccess(T data, String msg){
        return new ServiceResponse(0, data, msg);
    }

    public static ServiceResponse createServiceResponseByFail(int status){
        return new ServiceResponse(status);
    }
    public static ServiceResponse createServiceResponseByFail(int status, String msg){
        return new ServiceResponse(status,null , msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
