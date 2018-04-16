package com.emarket.service.impl;

import com.emarket.bean.User;
import com.emarket.common.Const;
import com.emarket.common.ServerResponse;
import com.emarket.common.TokenCache;
import com.emarket.dao.UserMapper;
import com.emarket.service.IUserService;
import com.emarket.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IUserServiceImpl implements IUserService{

  @Autowired
  private UserMapper userMapper;

  @Override
  /**
   * 用户登录
   * @method login
   * @author jiaoguang
   * @create 2018/4/7 23:09
   * @modifier jiaoguang
   * @modify 2018/4/7 23:09
   * @param [username, password]
   * @return com.emarket.common.ServerResponse<com.emarket.bean.User>
   * @since 1.0
   * @version 1.0
   */
  public ServerResponse<User> login(String username, String password) {
    int resultCount = userMapper.checkUsername(username);
    if(resultCount == 0){
      return ServerResponse.createByErrorMessage("用户名不存在");
    }
    String md5Password = MD5Util.MD5EncodeUtf8(password);
    User user = userMapper.selectLogin(username, md5Password);
    if(user == null) {
      return ServerResponse.createByErrorMessage("密码错误");
    }
    user.setPassword(StringUtils.EMPTY);
    return ServerResponse.createBySuccess("登录成功", user);
  }

  @Override
  /**
   * 用户注册
   * @method register
   * @author jiaoguang
   * @create 2018/4/8 22:48
   * @modifier jiaoguang
   * @modify 2018/4/8 22:48
   * @param [user]
   * @return com.emarket.common.ServerResponse<java.lang.String>
   * @since V1.0.0
   * @version V1.0.0
   */
  public ServerResponse<String> register(User user) {
    ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
    if(!validResponse.isSuccess()) {
      return validResponse;
    }
    validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
    if(!validResponse.isSuccess()) {
      return validResponse;
    }
    user.setRole(Const.Role.ROLE_CUSTOMER);
    user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
    int resultCount = userMapper.insert(user);
    if(resultCount == 0) {
      return ServerResponse.createByErrorMessage("注册失败");
    }
    return ServerResponse.createBySuccessMessage("注册成功");
  }

  @Override
  /**
   * 用户名和邮箱校验
   * @method checkValid
   * @author jiaoguang
   * @create 2018/4/8 23:38
   * @modifier jiaoguang
   * @modify 2018/4/8 23:38
   * @param [str, type]
   * @return com.emarket.common.ServerResponse<java.lang.String>
   * @since V1.0.0
   * @version V1.0.0
   */
  public ServerResponse<String> checkValid(String str, String type) {
    // 如果不是空字符串，开始进行校验
    if(org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
      if(Const.USERNAME.equals(type)) {
        int resultCount = userMapper.checkUsername(str);
        if(resultCount > 0) {
          return ServerResponse.createByErrorMessage("用户名已存在");
        }
      }
      if(Const.EMAIL.equals(type)) {
        int resultCount = userMapper.checkEmail(str);
        if(resultCount > 0) {
          return ServerResponse.createByErrorMessage("邮箱已存在");
        }
      }
    }else{
      return ServerResponse.createByErrorMessage("校验错误");
    }
    return ServerResponse.createBySuccessMessage("校验成功");
  }

  public ServerResponse<String> selectQuestion(String username) {
    ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
    if(validResponse.isSuccess()) {
      return ServerResponse.createByErrorMessage("用户不存在");
    }
    String question = userMapper.selectQuestionByUsername(username);
    if(org.apache.commons.lang3.StringUtils.isNotBlank(question)) {
      return ServerResponse.createBySuccess(question);
    }
    return ServerResponse.createByErrorMessage("找回密码的问题是空的");
  }

  @Override
  public ServerResponse<String> checkAnswer(String username, String question, String answer) {
    int resultCount = userMapper.checkAnswer(username, question, answer);
    if(resultCount > 0) {
      String forgetToken = UUID.randomUUID().toString();
      TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
      return ServerResponse.createBySuccess(forgetToken);
    }
    return ServerResponse.createByErrorMessage("问题答案不正确");
  }

  @Override
  public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
    if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)) {
      return ServerResponse.createByErrorMessage("参数错误，token需要传递");
    }
    ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
    if(validResponse.isSuccess()) {
      return ServerResponse.createByErrorMessage("用户不存在");
    }
    String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
    if(org.apache.commons.lang3.StringUtils.isBlank(token)) {
      return ServerResponse.createByErrorMessage("token无效或过期");
    }
    if(StringUtils.equals(forgetToken, token)) {
      String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
      int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
      if(rowCount > 0) {
        return ServerResponse.createBySuccessMessage("修改密码成功");
      }
    }else {
      return ServerResponse.createByErrorMessage("token错误，请重新获取token");
    }
    return ServerResponse.createByErrorMessage("修改密码失败");
  }

  @Override
  public ServerResponse<String> resetPassword(User user, String oldPassword, String newPassword) {
    int resultCount = userMapper.checkPassword(user.getId(), MD5Util.MD5EncodeUtf8(oldPassword));
    if(resultCount < 1) {
      return ServerResponse.createByErrorMessage("密码不正确");
    }
    resultCount = userMapper.updatePasswordByUserId(user.getId(), MD5Util.MD5EncodeUtf8(newPassword));
    if(resultCount > 1) {
      return ServerResponse.createBySuccessMessage("重置密码成功");
    }
    return ServerResponse.createByErrorMessage("密码更新失败");
  }

  @Override
  public ServerResponse<User> updateUserInfo(User user) {
    int resultCount = userMapper.checkEmailByUserId(user.getId(), user.getEmail());
    if(resultCount > 0) {
      return ServerResponse.createByErrorMessage("邮箱已被使用");
    }
    User updateUser = new User();
    updateUser.setId(user.getId());
    updateUser.setEmail(user.getEmail());
    updateUser.setPhone(user.getPhone());
    updateUser.setQuestion(user.getQuestion());
    updateUser.setAnswer(user.getAnswer());
    if(!StringUtils.isNotBlank(updateUser.getQuestion()) && StringUtils.isNotBlank(updateUser.getAnswer())) {
      return ServerResponse.createByErrorMessage("问题不能为空");
    }
    if(!StringUtils.isNotBlank(updateUser.getQuestion()) && !StringUtils.isNotBlank(updateUser.getAnswer())) {
      return ServerResponse.createByErrorMessage("答案不能为空");
    }
    resultCount = userMapper.updateByPrimaryKey(updateUser);
    if(resultCount > 0) {
      return ServerResponse.createBySuccess("更新成功", updateUser);
    }
    return ServerResponse.createByErrorMessage("更新个人信息失败");
  }

  @Override
  public ServerResponse<User> getUserInfo(int userId) {
    User user = userMapper.selectByPrimaryKey(userId);
    if(user == null) {
      return ServerResponse.createByErrorMessage("找不到当前用户信息");
    }
    user.setPassword(StringUtils.EMPTY);
    return ServerResponse.createBySuccess(user);
  }

  public ServerResponse checkAdminRole (User user) {
    if(user != null && user.getRole() == Const.Role.ROLE_ADMIN) {
      return ServerResponse.createBySuccess();
    }
    return ServerResponse.createByError();
  }
}
