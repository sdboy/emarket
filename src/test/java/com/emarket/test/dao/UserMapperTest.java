package com.emarket.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
  "classpath:applicationContext-datasource.xml",})
@Transactional(transactionManager = "transactionManager")
@Rollback(value = true)
public class UserMapperTest {

  @Test
  public void insert() {
  }

  @Test
  public void insertSelective() {
  }

  @Test
  public void checkUsername() {
  }

  @Test
  public void selectLogin() {
  }

  @Test
  public void checkEmail() {
  }
}