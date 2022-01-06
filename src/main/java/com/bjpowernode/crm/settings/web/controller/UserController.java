package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:
 * Package:com.bjpowernode.crm.settings.web.controller
 * author:郭鑫
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        return "settings/qx/user/login";
    }
    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, HttpServletRequest request){
        //响应结果集合
        Map<String,Object>retMap=new HashMap<String,Object>();

        User user=userService.queryUserByloginActAndPwd(loginAct, MD5Util.getMD5(loginPwd));
        if(user==null){
            //返回错误信息
            //提示用户：账号或密码有误
           retMap.put("code", 0);
           retMap.put("message", "账号或者密码有误");
           return retMap;
        }
        //将用户信息存放在session当中
        request.getSession().setAttribute("sessionUser", user);
        //返回正确的信息
        retMap.put("code", 1);
        return retMap;
    }

}
