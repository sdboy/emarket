package com.emarket.dao;

import com.emarket.bean.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}