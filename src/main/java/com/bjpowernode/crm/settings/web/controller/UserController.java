package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import org.omg.CORBA.Request;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * ClassName:
 * Package:com.bjpowernode.crm.settings.web.controller
 * author:郭鑫
 */
@Controller
public class UserController {

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        return "settings/qx/user/login";
    }
    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, HttpSession session){
        User user=userService.queryUserByloginActAndPwd(loginAct, MD5Util.getMD5(loginPwd));
        if(user==null){
            //返回错误信息
            //提示用户：账号或密码有误
        }
        //将用户信息存放在session当中
        session.setAttribute("user",user);
        //返回正确的信息
        return null;
    }

}
