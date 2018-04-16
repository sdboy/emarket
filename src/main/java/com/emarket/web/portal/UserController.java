package com.emarket.web.portal;

import com.emarket.bean.User;
import com.emarket.common.Const;
import com.emarket.common.ServerResponse;
import com.emarket.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "user")
public class UserController {

  @Autowired
  private IUserService iUserService;

  @RequestMapping (value = "login.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  /**
   * 用户登录
   * @method login
   * @author jiaoguang
   * @create 2018/4/7 23:08
   * @modifier jiaoguang
   * @modify 2018/4/7 23:08
   * @param [username, password, session]
   * @return com.emarket.common.ServerResponse<com.emarket.bean.User>
   * @since 1.0
   * @version 1.0
   */
  public ServerResponse<User> login(String username, String password, HttpSession session) {
    ServerResponse<User> response = iUserService.login(username, password);
    if(response.isSuccess()) {
      session.setAttribute(Const.CURRENT_USER, response.getData());
    }
    return response;
  }
  @RequestMapping (value = "logout.do", method = RequestMethod.POST)
  @ResponseBody
  /**
   * 用户登出
   * @method logout
   * @author jiaoguang
   * @create 2018/4/8 22:47
   * @modifier jiaoguang
   * @modify 2018/4/8 22:47
   * @param [session]
   * @return com.emarket.common.ServerResponse<java.lang.String>
   * @since V1.0.0
   * @version V1.0.0
   */
  public ServerResponse<String> logout(HttpSession session) {
    session.removeAttribute(Const.CURRENT_USER);
    return ServerResponse.createBySuccess();
  }
  @RequestMapping (value = "register.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  /**
   * 用户注册
   * @method register
   * @author jiaoguang
   * @create 2018/4/8 22:47
   * @modifier jiaoguang
   * @modify 2018/4/8 22:47
   * @param [user]
   * @return com.emarket.common.ServerResponse<java.lang.String>
   * @since V1.0.0
   * @version V1.0.0
   */
  public ServerResponse<String> register(User user) {
    return  iUserService.register(user);
  }
  @RequestMapping (value = "checkValid.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  /**
   * 检查
   * @method checkValid
   * @author jiaoguang
   * @create 2018/4/8 23:33
   * @modifier jiaoguang
   * @modify 2018/4/8 23:33
   * @param [str, type]
   * @return com.emarket.common.ServerResponse<java.lang.String>
   * @since V1.0.0
   * @version V1.0.0
   */
  public ServerResponse<String> checkValid(String str, String type) {
    return iUserService.checkValid(str, type);
  }
  @RequestMapping (value = "forgetGetQuestion.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  public ServerResponse<String> forgetGetQuestion(String username) {
    return iUserService.selectQuestion(username);
  }
  @RequestMapping (value = "forgetCheckAnswer.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
    return iUserService.checkAnswer(username, question, answer);
  }
  @RequestMapping (value = "forgetResetPassword.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  /**
   * <p>忘记密码状态下重置密码</p>
   * @method forgetResetPassword
   * @author jiaoguang
   * @create 2018/4/12 21:56
   * @modifier jiaoguang
   * @modify 2018/4/12 21:56
   * @param [username, passwordNew, forgetToken]
   * @return com.emarket.common.ServerResponse<java.lang.String>
   * @since V1.0.0
   * @version V1.0.0
   */
  public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
    return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
  }
  @RequestMapping (value = "resetPassowrd.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  public ServerResponse<String> resetPassowrd(HttpSession session, String oldPassword, String newPassword) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if(user == null) {
      return ServerResponse.createByErrorMessage("请先登录");
    }
    return iUserService.resetPassword(user, oldPassword, newPassword);
  }

  @RequestMapping (value = "updateUserInfo.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  public ServerResponse<User> updateUserInfo(HttpSession session, User user) {
    User curUser = (User) session.getAttribute(Const.CURRENT_USER);
    if(curUser == null) {
      return ServerResponse.createByErrorMessage("请先登录");
    }
    user.setId(curUser.getId());
    ServerResponse<User> response = iUserService.updateUserInfo(user);
    if(response.isSuccess()) {
      response.getData().setUsername(curUser.getUsername());
      session.setAttribute(Const.CURRENT_USER, response.getData());
    }
    return response;
  }

  public ServerResponse<User> getUserInfo(HttpSession session) {
    User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
    if(currentUser == null) {
      return ServerResponse.createByErrorMessage("请先登录");
    }
    return iUserService.getUserInfo(currentUser.getId());
  }
}
