package com.bjpowernode.crm.interceptor;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.interceptor
 * Desciption：
 * Date：2022/1/19
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.contants.Contant;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *谷宏帅
 *2022/1/19
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user = (User)request.getSession().getAttribute(Contant.SESSION_USER);
        if(user !=null){
            return true;
        }
        /*request.setAttribute("msg", "您还没有登录，请先登录");*/
    /*  request.getRequestDispatcher("/WEB-INF/pages/settings/qx/user/login.jsp").forward(request, response);*/
        response.sendRedirect(request.getContextPath() + "/");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
