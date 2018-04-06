package com.emarket.dao;

import com.emarket.bean.Category;

public interface CategoryMapper {
    int insert(Category record);

    int insertSelective(Category record);
}