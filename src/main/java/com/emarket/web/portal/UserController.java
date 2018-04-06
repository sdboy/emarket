package com.emarket.web.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "user")
public class UserController {
  @RequestMapping (value = "login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
  @ResponseBody
  public Object login(String username, String password, HttpSession session) {
    return null;
  }
}
