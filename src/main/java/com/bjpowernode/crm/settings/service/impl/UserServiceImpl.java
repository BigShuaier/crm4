package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 谷宏帅
 * 2022/1/6
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public User queryUserByloginActAndPwd(String loginAct, String MD5) {
        Map<String,Object>paramMap=new HashMap<String,Object>();
        paramMap.put("loginAct", loginAct);
        paramMap.put("MD5", MD5);
        return userMapper.queryUserByloginActAndPwd(paramMap);
    }

    @Override
    public User queryUserByloginAct(String loginAct) {
        return userMapper.queryUserByloginAct(loginAct);
    }

    @Override
    public List<User> queryAllUserList() {

        BoundListOperations boundListOperations = redisTemplate.boundListOps("userList");
        List<User>userList= boundListOperations.range(0, -1);
        if(userList==null || userList.size()==0){
            userList=userMapper.selectqueryAllUserList();
            for(User u:userList){
                boundListOperations.leftPush(u);
            }
        }
        //有
        return userList;
    }
}
