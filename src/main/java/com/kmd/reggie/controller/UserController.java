package com.kmd.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kmd.reggie.common.R;
import com.kmd.reggie.entity.User;
import com.kmd.reggie.service.UserService;
import com.kmd.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        String subject = "瑞吉餐购登录验证码";

        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            String body = "欢迎使用瑞吉餐购，登录验证码为: " + code + ",五分钟内有效，请妥善保管!";
            log.info("code={}", code);

            // 真正地发送邮箱验证码
            userService.sendMsg(phone, subject, body);

            //  将随机生成的验证码保存到session中
            //            session.setAttribute(phone, code);

            // 验证码由保存到session 优化为 缓存到Redis中，并且设置验证码的有效时间为 5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("验证码发送成功，请及时查看!");
        }
        return R.error("验证码发送失败，请重新输入!");
    }
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
            user.setStatus(1);
            userService.save(user);
        } else {
            user = userService.getOne(wrapper);
        }
        session.setAttribute("user", user.getId());
        redisTemplate.delete(user.getPhone());
        return R.success("登录成功");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session) {
        session.setAttribute("user", null);
        return R.success("退出成功");
    }
}
