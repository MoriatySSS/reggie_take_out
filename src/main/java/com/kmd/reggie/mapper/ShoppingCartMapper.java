package com.kmd.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmd.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
