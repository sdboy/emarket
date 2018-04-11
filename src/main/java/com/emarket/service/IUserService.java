package com.emarket.service;

import com.emarket.bean.User;
import com.emarket.common.ServerResponse;

public interface IUserService {
  ServerResponse<User> login(String username, String password);
  ServerResponse<String> register(User user);
  ServerResponse<String> checkValid(String str, String type);
  ServerResponse<String> selectQuestion(String username);
  ServerResponse<String> checkAnswer(String username, String question, String answer);
  ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
}
