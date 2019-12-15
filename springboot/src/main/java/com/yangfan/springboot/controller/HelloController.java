package com.yangfan.springboot.controller;

import com.yangfan.springboot.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User start() {
        User user = new User();
        user.setNickName("小明");
        user.setPassWord("xxx");
        return user;
    }
}
