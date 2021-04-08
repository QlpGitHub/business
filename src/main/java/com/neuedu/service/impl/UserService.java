package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.DateUtils;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.ServiceResponse;
import com.neuedu.vo.UserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

@Service
public class UserService implements IUserService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public ServiceResponse loginLogic(String username, String password) {


//        if(username == null || username.equals("")){
//            return ServiceResponse.createServiceResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(), ResponseCode.USERNAME_NOT_EMPTY.getMsg());
//        }

        //1、用户名和密码的非空判断

        //API判断字符串是否为空
        if(StringUtils.isBlank(username)){//null length=0 含有空格 tab制表符
            return ServiceResponse.createServiceResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(), ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(password)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(), ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }

        //2、查看用户名是否存在
        Integer count = userInfoMapper.findByUsername(username);
        if(count == 0){
            //用户名不存在
            return ServiceResponse.createServiceResponseByFail(ResponseCode.USERNAME_NOT_EXITS.getCode(), ResponseCode.USERNAME_NOT_EXITS.getMsg());
        }

        //3、根据用户名和密码查询
        UserInfo userInfo = userInfoMapper.findByUsernameAndPassword(username, MD5Utils.getMD5Code(password));
        if(userInfo == null){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getMsg());
        }

        //4、返回结果
        return ServiceResponse.createServiceResponseBySuccess(convert(userInfo));
    }

    private UserInfoVO convert(UserInfo userInfo){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUsername(userInfo.getUsername());
        userInfoVO.setEmail(userInfo.getEmail());
        userInfoVO.setPhone(userInfo.getPhone());
        userInfoVO.setCreateTime(DateUtils.dateToString(userInfo.getCreateTime()));
        userInfoVO.setUpdateTime(DateUtils.dateToString(userInfo.getUpdateTime()));
        return userInfoVO;
    }

    @Override
    public ServiceResponse registerLogic(UserInfo userInfo) {

        //1、非空校验
        if(userInfo == null){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }

        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        String email = userInfo.getEmail();
        String phone = userInfo.getPhone();
        String question = userInfo.getQuestion();
        String answer = userInfo.getAnswer();

        if(StringUtils.isBlank(username)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(), ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(password)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(), ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(email)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.EMAIL_NOT_EMPTY.getCode(), ResponseCode.EMAIL_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(phone)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.PHONE_NOT_EMPTY.getCode(), ResponseCode.PHONE_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(question)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.QUESTION_NOT_EMPTY.getCode(), ResponseCode.QUESTION_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(answer)){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.ANSWER_NOT_EMPTY.getCode(), ResponseCode.ANSWER_NOT_EMPTY.getMsg());
        }

        //2、判断用户名是否存在
        Integer count  = userInfoMapper.findByUsername(username);
        if (count > 0){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.USERNAME_EXITS.getCode(), ResponseCode.USERNAME_EXITS.getMsg());
        }

        //3、邮箱是否存在
        Integer email_count = userInfoMapper.findByEmail(email);
        if(email_count > 0){
            return ServiceResponse.createServiceResponseByFail(ResponseCode.EMAIL_EXITS.getCode(), ResponseCode.EMAIL_EXITS.getMsg());
        }

        //4、注册
        userInfo.setRole(Const.NORMAL_USER);
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        Integer result = userInfoMapper.insert(userInfo);
        if (result == 0){
            //注册失败
            return ServiceResponse.createServiceResponseByFail(ResponseCode.REGISTER_FAIL.getCode(), ResponseCode.REGISTER_FAIL.getMsg());
        }
        return ServiceResponse.createServiceResponseBySuccess();
    }
}
