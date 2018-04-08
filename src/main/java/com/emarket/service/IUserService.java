package com.emarket.service;

import com.emarket.bean.User;
import com.emarket.common.ServerResponse;

public interface IUserService {
  ServerResponse<User> login(String username, String password);
  ServerResponse<String> register(User user);
  ServerResponse<String> checkValid(String str, String type);
}
