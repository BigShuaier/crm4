package com.bjpowernode.crm.settings.web.controller;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.web.controller
 * Desciption：
 * Date：2022/1/8
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.Result;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.impl.DicTypeServiceImpl;
import com.sun.tools.javac.jvm.Code;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *谷宏帅
 *2022/1/8
 */
@Controller
public class DicTypeController {
    //业务层对象
    @Autowired
    DicTypeService dicTypeService;
    //数据字典表功能首页
    @RequestMapping("settings/dictionary/index.do")
    public String index(){
    return "settings/dictionary/index";
    }
    //字典类型
    @RequestMapping("settings/dictionary/type/index.do")
    public String typeIndex(){

        return "settings/dictionary/type/index";
    }
    //查询数据字典中值
    @ResponseBody
   @RequestMapping("settings/dictionary/type/index/queryAllDicType.do")
    public List<DicType> queryAllDicType(){
        List<DicType> dicTypes = dicTypeService.queryAllDicType();
        return dicTypes;
    }
    //创建页面跳转

    @RequestMapping("settings/dictionary/type/index/save.do")
    public String save(){
        return "settings/dictionary/type/save";
    }
    //验证编码是否存在
    @ResponseBody
    @RequestMapping("settings/dictionary/type/index/save/iscode.do")
    public Object iscode(@RequestParam(value ="code1",required = true)String code1){
        DicType dicType = dicTypeService.checkCode(code1);
        if(dicType!=null){
            return Result.fail("编号已经存在");
        }
        return Result.success();
    }
    //创建功能实现
    @ResponseBody
    @RequestMapping("settings/dictionary/type/index/save/saveDicType.do")
    public Object saveDicType(DicType dicType) {
        try {
             dicTypeService.saveDicType(dicType);
             return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加失败");
        }
    }
    @RequestMapping("settings/dictionary/type/index/edit.do")
    public String edit(String code,HttpServletRequest request){
        DicType dicType = dicTypeService.checkCode(code);
        request.getSession().setAttribute("dicType",dicType);
        return "settings/dictionary/type/edit";
    }

    @ResponseBody
    @RequestMapping("settings/dictionary/type/index/edit/saveEditDicType.do")
    public Object saveEditDicType(DicType dicType){
        try {

            int count = dicTypeService.saveEditDicType(dicType);
            if(count!=1){
                return Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.fail("更新失败");
        }
        return Result.success();
    }
    //删除字段操作
    @ResponseBody
    @RequestMapping("settings/dictionary/type/index/delete.do")
    public Object delete(String []code){
        int count=0;
        try {
            count = dicTypeService.deleteDicType(code);
            if(count!=code.length){
                Result.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
        return Result.success(count);
    }

}
