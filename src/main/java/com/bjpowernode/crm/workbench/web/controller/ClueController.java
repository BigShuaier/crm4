package com.bjpowernode.crm.workbench.web.controller;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.web.controller
 * Desciption：
 * Date：2022/1/17
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.contants.Contant;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.commons.utils.Result;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueServer;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *谷宏帅
 *2022/1/17
 */
@Controller
public class ClueController {
    @Autowired
    DicValueServer dicValueServer;
    @Autowired
    UserService userService;
    @Autowired
    ClueService clueService;

    @RequestMapping("workbench/clue/index.do")
    public String clueIndex(HttpServletRequest request){
        List<DicValue> sourceList = dicValueServer.queryAllDicValueclueStateByTypeCode("source");
        List<DicValue> clueStateList = dicValueServer.queryAllDicValueclueStateByTypeCode("stage");
        List<DicValue> appellationList = dicValueServer.queryAllDicValueclueStateByTypeCode("appellation");
        List<User> userList = userService.queryAllUserList();
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("userList", userList);
        request.setAttribute("appellationList", appellationList);
        return "workbench/clue/index";
    }
    @RequestMapping("workbench/clue/queryClueListForPageByCondition.do")
    public @ResponseBody Object queryClueListForPageByCondition(
            @RequestParam(value = "name",required = true)String name,
            @RequestParam(value = "company",required = true)String company,
            @RequestParam(value = "phone",required = true)String phone,
            @RequestParam(value = "source",required = true)String source,
            @RequestParam(value = "owner",required = true)String owner,
            @RequestParam(value = "mphone",required = true)String mphone,
            @RequestParam(value = "clueState",required = true)String clueState,
            @RequestParam(value = "pageNo",required = true)Integer pageNo,
            @RequestParam(value = "pageSize",required = true)Integer pageSize){

        Map<String,Object> pramMap =new HashMap<>();
        pramMap.put("name", name);
        pramMap.put("phone", phone);
        pramMap.put("company", company);
        pramMap.put("source", source);
        pramMap.put("owner", owner);
        pramMap.put("mphone", mphone);
        pramMap.put("clueState", clueState);
        pramMap.put("pageNo",(pageNo-1)*pageSize);
        pramMap.put("pageSize", pageSize);

        PageinationVO<Clue> cluePageinationVO = clueService.queryqueryClueListForPageByCondition(pramMap);
        int pageTotals=cluePageinationVO.getTotal()/pageSize;
        int mod=cluePageinationVO.getTotal()%pageSize;
        if(mod>0){
           pageTotals=pageTotals +1;
        }
        Map<String,Object>retMap=new HashMap<>();
        retMap.put("clueList", cluePageinationVO.getDataList());
        retMap.put("totalPage", pageTotals);
        retMap.put("totalRows", cluePageinationVO.getTotal());
        return retMap;
    }
    @ResponseBody
    @RequestMapping("workbench/clue/saveCreateClue.do")
    public Object saveCreateClue(Clue clue,HttpServletRequest request){
        //完成对象信息
        User user=(User)request.getSession().getAttribute(Contant.SESSION_USER);

        clue.setCreateBy(user.getId());

        clue.setCreateTime(DateUtils.formatDateTime(new Date()));

        clue.setId(UUIDUtils.getUUID());

        clueService.saveCreateClue(clue);

        return Result.success();
    }

    @ResponseBody
    @RequestMapping("workbench/clue/editClueBtnShow.do")
    public Object editClueBtnShow(String id){
        Clue clue=clueService.queryClueById(id);
        return clue;
    }


    @RequestMapping("workbench/clue/saveUpdateClue.do")
    public  @ResponseBody Object saveUpdateClue(Clue clue,HttpServletRequest request){
        User user =(User) request.getSession().getAttribute(Contant.SESSION_USER);
        try {
            clue.setEditBy(user.getId());
            clue.setEditTime(DateUtils.formatDateTime(new Date()));
            int count = clueService.saveUpdateClue(clue);
            if(count!=1){
                return Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新失败");
        }
        return Result.success();
    }
    @RequestMapping("workbench/clue/deleteClue.do")
    public @ResponseBody Object deleteClue(@RequestParam(value = "id",required = true)String id[]){
        int count= 0;
        try {
            count = clueService.deleteClue(id);
            if(count==0){
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
        return Result.success(count);
    }
    @RequestMapping("workbench/clue/detailClue.do")
    public String detailClue(String id,HttpServletRequest request){
        Clue clue = clueService.queryDetalClueById(id);
        request.setAttribute("clue", clue);
        return "workbench/clue/detail";
    }
    @RequestMapping("workbench/clue/queryClueRemarkList.do")
    public @ResponseBody Object queryClueRemarkList(String clueId){
       List<ClueRemark>clueRemarks= clueService.queryClueRemarkById(clueId);
        return clueRemarks;
    }
    @RequestMapping("workbench/clue/saveClueRemark.do")
    public @ResponseBody Object saveClueRemark(ClueRemark clueRemark,HttpServletRequest request){
       User user=(User)request.getSession().getAttribute(Contant.SESSION_USER);
       clueRemark.setCreateBy(user.getId());
       clueRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
       clueRemark.setId(UUIDUtils.getUUID());
        int count= 0;
        try {
            count = clueService.saveClueRemark(clueRemark);
            if(count!=1){
                return Result.fail("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加失败");
        }

        return Result.success();
    }
    @RequestMapping("workbench/clue/updateRemark.do")
    public @ResponseBody Object updateRemark(ClueRemark clueRemark,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Contant.SESSION_USER);
        clueRemark.setEditBy(user.getId());
        clueRemark.setEditTime(DateUtils.formatDateTime(new Date()));
        try {
            int count=clueService.saveUpdateRemark(clueRemark);
            if(count!=1){
                return Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新失败");
        }
        return Result.success();
    }
    @RequestMapping("workbench/clue/deleteClueRemark.do")
    public @ResponseBody Object deleteClueRemark(String id){
        try {
            int count =clueService.deleteClueRemark(id);
            if(count!=1){
                Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.fail("更新失败");
        }
        return Result.success();
    }
    @RequestMapping("workbench/clue/ClueRemarkRelation.do")
    public @ResponseBody Object ClueRemarkRelation(String clueId){
        List<Activity>Activity=clueService.queryClueRemarkRelation(clueId);
        return Activity;
    }
    @RequestMapping("workbench/clue/deleteClueRemarkRelation.do")
    public @ResponseBody Object deleteClueRemarkRelation(String clueId,String id){
        try {
            int count= clueService.deleteClueRemarkRelation(clueId,id);
            if(count!=1){
                Result.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.fail("删除失败");
        }
        return Result.success();
    }
    @RequestMapping("workbench/clue/queryActivityByName.do")
    public @ResponseBody Object queryActivityByName(@RequestParam(value = "clueId",required = true)String clueId,@RequestParam(value = "name" ,required = false)String name){
        List<Activity>activityList=clueService.queryActivityByName(clueId,name);
        return activityList;
    }
    @RequestMapping("workbench/clue/saveBundActivity.do")
    public @ResponseBody Object saveBundActivity(@RequestParam(value = "id",required = true)String []id,@RequestParam(value = "clueId",required = false)String clueId){

        int count= 0;
        try {
            List<ClueActivityRelation> clueActivityRelations=new ArrayList<>();
            ClueActivityRelation clueActivityRelation=null;
            for (String s : id) {
                clueActivityRelation =new ClueActivityRelation();
                clueActivityRelation.setId(UUIDUtils.getUUID());
                clueActivityRelation.setActivityId(s);
                clueActivityRelation.setClueId(clueId);
                clueActivityRelations.add(clueActivityRelation);
            }
            count = clueService.saveBundActivity(clueActivityRelations);
            if(count==0){
                Result.fail("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.fail("添加失败");
        }
        return Result.success(count);
    }


    @RequestMapping("workbench/clue/convert.do")
    public String convertPage(){
        return "workbench/clue/convert";
    }
}
