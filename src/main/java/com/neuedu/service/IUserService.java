package com.neuedu.service;

import com.neuedu.pojo.UserInfo;
import com.neuedu.utils.ServiceResponse;

public interface IUserService {
    /*
    * 登录
    * */
    public ServiceResponse loginLogic(String username, String password);


    /*
    * 注册
    * */
    public ServiceResponse registerLogic(UserInfo userInfo);
}
