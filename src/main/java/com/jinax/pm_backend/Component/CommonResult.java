package com.jinax.pm_backend.Component;

/**
 * @author : chara
 */
public class CommonResult<T> {
    private Integer code;
    private T data;
    private String msg;

    public CommonResult() {
    }

    public CommonResult(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public static <T> CommonResult<T> successResult(T data, String msg){
        return new CommonResult<>(200,data,msg);
    }

    public static <T> CommonResult<T> failResult(T data, String msg){
        return new CommonResult<>(500,data,msg);
    }
}
