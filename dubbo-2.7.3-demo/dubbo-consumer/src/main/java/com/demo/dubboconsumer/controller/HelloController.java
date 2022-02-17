/*
 * Licensed Materials - Property of tenxcloud.com
 * (C) Copyright 2022 TenxCloud. All Rights Reserved.
 */
package com.demo.dubboconsumer.controller;

import com.demo.common.HelloService;
import com.demo.common.model.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Reference(version = "1.0")
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(){
        User user = new User();
        user.setName("3211312");
        return helloService.hello(user);
    }
}
