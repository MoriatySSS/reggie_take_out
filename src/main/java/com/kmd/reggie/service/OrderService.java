package com.kmd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmd.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
