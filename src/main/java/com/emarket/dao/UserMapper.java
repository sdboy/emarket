package com.emarket.dao;

import com.emarket.bean.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}