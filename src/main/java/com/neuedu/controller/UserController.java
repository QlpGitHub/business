package com.neuedu.controller;

import com.neuedu.common.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "user/login.do")
    public ServiceResponse login(String username, String password, HttpSession session){
        ServiceResponse serviceResponse = userService.loginLogic(username, password);
        if(serviceResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, serviceResponse.getData());
        }
        return serviceResponse;
    }


    @RequestMapping(value = "user/register.do")
    public ServiceResponse register(UserInfo userInfo){
        return userService.registerLogic(userInfo);
    }
}
