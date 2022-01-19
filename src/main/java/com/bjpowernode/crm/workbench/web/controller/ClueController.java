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
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



}
