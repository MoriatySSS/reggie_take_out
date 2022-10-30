package com.kmd;

import com.kmd.reggie.entity.OrderDetail;
import com.kmd.reggie.entity.ShoppingCart;
import com.kmd.reggie.service.OrderDetailService;
import com.kmd.reggie.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReggieTakeOutApplicationTests {

    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    ShoppingCartService shoppingCartService;

    @Test
    public void test() {

        OrderDetail orderDetail = orderDetailService.getById(1585900957160669186L);
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(orderDetail, shoppingCart, "id", "");
        System.out.println("...");
    }

}
