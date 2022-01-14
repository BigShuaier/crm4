package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;

import java.util.List;

/**
 * 谷宏帅
 * 2022/1/6
 */
public interface UserService {
   User queryUserByloginActAndPwd(String loginAct, String MD5);

    User queryUserByloginAct(String loginAct);

    List<User> queryAllUserList();
}
