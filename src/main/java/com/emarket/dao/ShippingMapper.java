package com.emarket.dao;

import com.emarket.bean.Shipping;

public interface ShippingMapper {
    int insert(Shipping record);

    int insertSelective(Shipping record);
}