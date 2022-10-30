package com.kmd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmd.reggie.entity.Orders;
import com.kmd.reggie.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    void again(Orders orders);
}
