package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 谷宏帅
 * 2022/1/6
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserByloginActAndPwd(String loginAct, String MD5) {
        Map<String,Object>paramMap=new HashMap<String,Object>();
        paramMap.put("loginAct", loginAct);
        paramMap.put("MD5", MD5);
        return userMapper.queryUserByloginActAndPwd(paramMap);
    }
}
