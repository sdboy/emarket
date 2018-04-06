package com.emarket.dao;

import com.emarket.bean.Product;

public interface ProductMapper {
    int insert(Product record);

    int insertSelective(Product record);
}