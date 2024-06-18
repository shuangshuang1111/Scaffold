package com.integration.scaffold.relationaldataaccess.mysql.common;

public class Result <T>{
    // todo 这里是在百度借鉴的，并没有在重量级的文档上看到，以后会做修改 不清楚它是否要实现序列化接口，所以未实现，并且没有重写equals和hashCode方法
    private T datas;
    private Integer respCode;
    private String respMsg;
    public static <T> Result<T> success(T model, String msg) {
        return of(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    // todo 这个of方法是做什么的？Optional建议找个时间再学习一下
    public static <T> Result<T> success(T model) {
        return of(model, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> success() {
        return of(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> of(T datas, Integer code, String msg) {
        return new Result(datas, code, msg);
    }

    public static <T> Result<T> of(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static <T> Result<T> fail(T model) {
        return of(model, CodeEnum.FAILURE.getCode(), CodeEnum.FAILURE.getMessage());
    }

    public static <T> Result<T> fail(String msg) {
        return of( CodeEnum.FAILURE.getCode(), msg);
    }
    public static <T> Result<T> fail(T model, String msg) {
        return of(model, CodeEnum.FAILURE.getCode(), msg);
    }


    public T getDatas() {
        return this.datas;
    }

    public Integer getrespCode() {
        return this.respCode;
    }

    public String getrespMsg() {
        return this.respMsg;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public void setrespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public void setrespMsg(String respMsg) {
        this.respMsg = respMsg;
    }


    public Result() {
    }

    public Result(Integer respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;

    }

    public Result(T datas, Integer respCode, String respMsg) {
        this.datas = datas;
        this.respCode = respCode;
        this.respMsg = respMsg;

    }





}
