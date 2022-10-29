package com.kmd.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kmd.reggie.common.R;
import com.kmd.reggie.entity.User;
import com.kmd.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登录功能（待完善）
     *
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody User user, HttpSession session) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, user.getPhone());
        if (userService.getOne(wrapper) == null) {
            userService.save(user);
        } else {
            user = userService.getOne(wrapper);
        }
        session.setAttribute("user", user.getId());
        return R.success("登录成功");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session) {
        session.setAttribute("user", null);
        return R.success("退出成功");
    }
}
