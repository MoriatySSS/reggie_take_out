package com.kmd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmd.reggie.entity.User;

public interface UserService extends IService<User> {
    public void sendMsg(String toEmail, String subject, String body);
}
