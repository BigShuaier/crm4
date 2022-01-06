package com.bjpowernode.crm.commons.utils;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.commons.utils
 * Desciption：响应结果对象
 * Date：2022/1/6
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.contants.Contant;

import java.util.HashMap;

/**
 *谷宏帅
 *2022/1/6
 */
public class Result extends HashMap<String,Object> {
    /**
     * 成功
     * @return
     */
    public static Result success(){
        Result result=new Result();
        result.put("code", Contant.SUCCESS_CODE);
        return result;
    }

    /**
     * 失败
     * @param message
     * @return
     */
    public static Result fail(String message){
        Result result = new Result();
        result.put(Contant.STATE_CODE,Contant.FAIL_CODE);
        result.put(Contant.STATE_MESSAGE, message);
        return result;
    }
}
