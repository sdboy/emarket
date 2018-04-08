package com.emarket.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
// value为空时不返回key
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer status;
  private String msg;
  private T data;

  private ServerResponse(Integer status) {
    this.status = status;
  }

  private ServerResponse(Integer status, T data) {
    this.status = status;
    this.data = data;
  }

  private ServerResponse(Integer status, String msg, T data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  private ServerResponse(Integer status, String msg) {
    this.status = status;
    this.msg = msg;
  }
  // 不在json序列化结果中
  @JsonIgnore
  public boolean isSuccess() {
    return this.status == ResponseCode.SUCCESS.getCode();
  }

  public int getStatus() {
    return status;
  }

  public T getData() {
    return data;
  }

  public String getMsg() {
    return msg;
  }

  public static <T> ServerResponse<T> createBySuccess() {
    return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
  }

  public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
    return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
  }

  public static <T> ServerResponse<T> createBySuccess(T data) {
    return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
  }

  public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
    return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
  }

  public static <T> ServerResponse<T> createByError() {
    return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
  }

  public static <T> ServerResponse<T> createByErrorMessage(String errorMsg) {
    return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
  }

  public static <T> ServerResponse<T> createByErrorCodeMessage(Integer code, String errorMsg) {
    return new ServerResponse<T>(code, errorMsg);
  }

}
