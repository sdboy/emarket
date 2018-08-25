package com.emarket.dao;

import com.emarket.bean.Cart;

public interface CartMapper {
  /**
   * <p>插入商品记录</p>
   * @method insert
   * @author jiaoguang
   * @date 2018/8/26 0:06
   * @param record 记录
   * @return int
   * @since V1.0.0
   * @version V1.0.0
   */
  int insert(Cart record);

  /**
   * <p>插入商品记录</p>
   * @method insertSelective
   * @author jiaoguang
   * @date 2018/8/26 0:13
   * @param record 记录
   * @return int
   * @since V1.0.0
   * @version V1.0.0
   */
  int insertSelective(Cart record);
}