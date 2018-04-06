package com.emarket.dao;

import com.emarket.bean.Cart;

public interface CartMapper {
    int insert(Cart record);

    int insertSelective(Cart record);
}