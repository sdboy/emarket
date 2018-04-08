package com.emarket.service.impl;

import com.emarket.bean.User;
import com.emarket.common.Const;
import com.emarket.common.ServerResponse;
import com.emarket.dao.UserMapper;
import com.emarket.service.IUserService;
import com.emarket.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * $modify 2018/4/7 23:09
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
    // TODO md5密码加密
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
   * $modify 2018/4/8 22:48
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
   * $modify 2018/4/8 23:38
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
}
