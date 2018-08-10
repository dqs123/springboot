package com.javase.springboot1.entity;

public class BaseModel {
    /**
     * 业务数据模型的状态码
     */
    private Integer statusCode = 200;
    /**
     * 业务数据模型的信息提示
     */
    private String message = "操作成功!";
    /**
     * 业务数据模型的数据
     */
    private Object data;

    public BaseModel() {
    }

    public BaseModel(Object data1) {
        this.data = data;
    }

    /**
     * 设置系统错误提示以及状态吗
     */
    public void setSysError(){
        statusCode = 403;
        message = "系统错误!";
    }

    /**
     * 设置数据库错误以及状态吗
     */
    public void setDBError(){
        statusCode = 405;
        message = "网络错误!";
    }

    /**
     * 自定义错误以及状态吗
     * @param statusCode
     * @param message
     */
    public void setCodeMessage(Integer statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
