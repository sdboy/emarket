package com.emarket.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
  "classpath:applicationContext-datasource.xml"})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = true)
public class UserControllerTest {

  @Test
  public void login() {
  }

  @Test
  public void logout() {
  }

  @Test
  public void register() {
  }

  @Test
  public void checkValid() {
  }
}