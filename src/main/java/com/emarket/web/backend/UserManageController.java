package com.emarket.web.backend;

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

/**
 * @author jiaoguang
 * @version V1.0.0
 * @project emarket
 * @package com.emarket.web.backend
 * @class UserManageController
 * @create 2018/4/16 21:10
 * @Copyright
 * @review
 */
@Controller
@RequestMapping(value = "manage/user")
public class UserManageController {

  @Autowired
  private IUserService iUserService;
  @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  public ServerResponse<User> login(String username, String password, HttpSession session) {
    ServerResponse<User> response = iUserService.login(username, password);
    if(response.isSuccess()) {
      User user = response.getData();
      if(user.getRole() == Const.Role.ROLE_ADMIN) {
        session.setAttribute(Const.CURRENT_USER, user);
        return response;
      }else {
        return ServerResponse.createByErrorMessage("不是管理员无法登录");
      }
    }
    return response;
  }
}
