package com.emarket.dao;

import com.emarket.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
  int insert(User record);

  int insertSelective(User record);
  int checkUsername(String username);
  User selectLogin(@Param("username") String username, @Param("password") String password);
  int checkEmail(String email);
  String selectQuestionByUsername(String username);
  int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);
  int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);
}