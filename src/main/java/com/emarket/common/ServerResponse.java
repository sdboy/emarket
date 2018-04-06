package com.emarket.common;

import java.io.Serializable;

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
}
