package com.kmd.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmd.reggie.common.BaseContext;
import com.kmd.reggie.common.R;
import com.kmd.reggie.dto.OrdersDto;
import com.kmd.reggie.entity.OrderDetail;
import com.kmd.reggie.entity.Orders;
import com.kmd.reggie.service.OrderDetailService;
import com.kmd.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        orderService.submit(orders);
        return R.success("添加成功");
    }

    @GetMapping("/userPage")
    public R<Page<OrdersDto>> page(int page, int pageSize) {
        Page<Orders> orderPage = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        wrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(orderPage, wrapper);
        BeanUtils.copyProperties(orderPage, ordersDtoPage, "records");
        List<Orders> orders = orderPage.getRecords();
        List<OrdersDto> ordersDtos = new ArrayList<>();
        for (Orders order : orders) {
            LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderId, order.getId());
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(order, ordersDto);
            ordersDto.setOrderDetails(orderDetailService.list(orderDetailLambdaQueryWrapper));
            ordersDtos.add(ordersDto);
        }
        ordersDtoPage.setRecords(ordersDtos);
        return R.success(ordersDtoPage);
    }

    @GetMapping("/page")
    public R<Page<OrdersDto>> orderPage(int page, int pageSize, Long number, String beginTime, String endTime) {
        Page<Orders> orderPage = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(number != null, Orders::getNumber, number);
        if (beginTime != null && endTime != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime begintime = LocalDateTime.parse(beginTime, dateTimeFormatter);
            LocalDateTime endtime = LocalDateTime.parse(endTime, dateTimeFormatter);
            wrapper.between(Orders::getOrderTime, begintime, endtime);
        }
        wrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(orderPage, wrapper);
        BeanUtils.copyProperties(orderPage, ordersDtoPage, "records");
        List<Orders> orders = orderPage.getRecords();
        List<OrdersDto> ordersDtos = new ArrayList<>();
        for (Orders order : orders) {
            LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderId, order.getId());
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(order, ordersDto);
            ordersDto.setOrderDetails(orderDetailService.list(orderDetailLambdaQueryWrapper));
            ordersDtos.add(ordersDto);
        }
        ordersDtoPage.setRecords(ordersDtos);
        return R.success(ordersDtoPage);
    }

    @PutMapping
    public R<String> update(@RequestBody Orders orders) {
        orderService.updateById(orders);
        return R.success("修改订单状态成功");
    }
}
