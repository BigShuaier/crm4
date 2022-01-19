package com.bjpowernode.crm.settings.web.controller;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.web.controller
 * Desciption：
 * Date：2022/1/10
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.Result;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.DicValueExt;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 谷宏帅
 * 2022/1/10
 */
@Controller
public class DicTypeValueController {
    @Autowired
    DicValueServer dicValueServer;

    //
    @RequestMapping("settings/dictionary/value/index.do")
    public String index() {
        return "settings/dictionary/value/index";
    }

    //查询字典值表所有字段
    @ResponseBody
    @RequestMapping("settings/dictionary/value/index/queryAllDicValue.do")
    public List<DicValue> queryDicValue() {
        List<DicValue> dicValues = dicValueServer.queryAllDicValue();
        return dicValues;
    }

    //跳转save界面
    @RequestMapping("settings/dictionary/value/index/save.do")
    public String save() {
        return "settings/dictionary/value/save";
    }

    //创建字典值
    @ResponseBody
    @RequestMapping("settings/dictionary/type/save/saveCreateDicValue.do")
    public Object saveCreateDicValue(DicValue dicValue, HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            return Result.fail("请登录后操作");
        }
        try {
            int count = dicValueServer.saveDicValue(dicValue);
            if (count != 1) {
                return Result.fail("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加失败");
        }
        return Result.success();
    }

    //修改字典值
    @RequestMapping("settings/dictionary/value/index/editDicValue.do")
    public String editDicValue(String id, HttpServletRequest request) {
        DicValueExt dicValue = dicValueServer.qureyDicType(id);
        request.getSession().setAttribute("dicValue", dicValue);
        return "settings/dictionary/value/edit";
    }

    @ResponseBody
    @RequestMapping("settings/dictionary/value/edit/saveEditDicValue.do")
    public Object saveEditDicValue(DicValue dicValue) {
        try {
            int count = dicValueServer.saveEditDicValue(dicValue);
            if(count!=1){
                Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.fail("更新失败");
        }

        return Result.success();
    }

    @ResponseBody
    @RequestMapping("settings/dictionary/value/index/deleteDicValue.do")
    public Object deleteDicValue(@RequestParam(value = "id",required = true)String []id,
    @RequestParam( value="typeCode", required =true)String []code){
            int count=0;
            try {
                count=dicValueServer.deleteDicValue(id,code);
                if(count==0){
                    return Result.fail("删除失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("删除失败");
            }
       return Result.success(count);
    }
}
