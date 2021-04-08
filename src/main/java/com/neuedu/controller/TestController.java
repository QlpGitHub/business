package com.neuedu.controller;

import com.neuedu.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
//类下的所有方法都返回Json
@RestController
public class TestController {
    //登录login.do?username=xxx&password=xxx
    @RequestMapping(value = "/login.do")
    //返回的数据成json
    @ResponseBody
    public UserInfo login(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        return userInfo;
    }

//    restful风格
//    @RequestMapping(value = {"/restful/login/{username}/{password}"})
//    public UserInfo login(@PathVariable("username") String username,
//                          @PathVariable("password") String password){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(1);
//        userInfo.setUsername(username);
//        userInfo.setPassword(password);
//        return userInfo;
//    }
}
