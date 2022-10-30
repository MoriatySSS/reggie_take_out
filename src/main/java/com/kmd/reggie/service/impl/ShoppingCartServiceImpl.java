package com.kmd.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmd.reggie.common.BaseContext;
import com.kmd.reggie.entity.OrderDetail;
import com.kmd.reggie.entity.Orders;
import com.kmd.reggie.entity.ShoppingCart;
import com.kmd.reggie.mapper.ShoppingCartMapper;
import com.kmd.reggie.service.OrderDetailService;
import com.kmd.reggie.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    OrderDetailService orderDetailService;

    @Override
    @Transactional
    public void again(Orders orders) {
        Long ordersId = orders.getId();
        LambdaQueryWrapper<OrderDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.eq(OrderDetail::getOrderId, ordersId);
        List<OrderDetail> detailList = orderDetailService.list(detailWrapper);
        List<ShoppingCart> shoppingCarts = new ArrayList<>();

        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        this.remove(shoppingCartLambdaQueryWrapper);

        for (OrderDetail orderDetail : detailList) {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(orderDetail, shoppingCart, "id");
            shoppingCart.setUserId(BaseContext.getCurrentId());
            shoppingCarts.add(shoppingCart);
        }
        this.saveBatch(shoppingCarts);
    }
}
