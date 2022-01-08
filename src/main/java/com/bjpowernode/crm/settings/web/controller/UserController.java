package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contant;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.commons.utils.Result;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * ClassName:
 * Package:com.bjpowernode.crm.settings.web.controller
 * author:郭鑫
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        return "settings/qx/user/login";
    }
    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, HttpServletRequest request,boolean isRemPwd,HttpServletResponse response) throws ParseException {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        User user1 = userService.queryUserByloginAct(loginAct);
        if(user1==null){
            return Result.fail("账号或者密码错误");
        }
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(loginAct);
        Integer errocount =(Integer) boundValueOperations.get();
        if(errocount!=null&&                errocount==3){
            return Result.fail("输入次数已超过三次，账号已经被锁定，请练习管理员");
        }
        User user=userService.queryUserByloginActAndPwd(loginAct, MD5Util.getMD5(loginPwd));
        if(user==null){

            //提示用户：账号或密码有误
            boundValueOperations.increment(1);
            boundValueOperations.expire(DateUtils.getRemainSecondsOneDay(new Date()), TimeUnit.SECONDS);
           return Result.fail("用户名或密码有误");
        }
        //验证账号是否失效
        if((!"".equals(user.getExpireTime()))&&new Date().compareTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.getExpireTime()))>0){
           return Result.fail("账号已失效");
        }
        //验证状态码
        if((!"".equals(user.getLockState()))&&Contant.STATE_LOCK.equals(user.getLockState())){
            return Result.fail("账号已锁定");
        }
        //验证是否为允许访问的ip
        String remoteHost = request.getRemoteHost();
        if((!"".equals(user.getAllowIps()))&&remoteHost.contains(user.getAllowIps())){
            return Result.fail("您的ip无法访问");
        }
        //记住我
        if(isRemPwd==true){
            Cookie loginAct1 = new Cookie("loginAct", loginAct);
            Cookie loginPwd1= new Cookie("loginPwd", loginPwd);
            response.addCookie(loginAct1);
            response.addCookie(loginPwd1);
        }
        //将用户信息存放在session当中
        request.getSession().setAttribute("sessionUser", user);
        //返回正确的信息
        return Result.success();
    }
   @RequestMapping("settings/qx/user/logout.do")
    public String loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }

}
