package com.bjpowernode.crm.workbench.web.controller;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.web.controller
 * Desciption：
 * Date：2022/1/12
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.commons.utils.Result;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.domain.Activity;
import org.apache.commons.fileupload.FileUpload;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 *谷宏帅
 *2022/1/12
 */
@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    UserService userService;
    @Autowired
    @RequestMapping("workbench/activity/index.do")
    public String index(){
        return "workbench/activity/index";
    }
    @ResponseBody
    @RequestMapping("workbench/activity/queryActivityListForPageByCondition.do")
    public Object queryActivityListForPageByCondition(
            @RequestParam(value = "activityName" ,required = false) String activityName,
            @RequestParam(value = "ownerName" ,required = false) String ownerName,
            @RequestParam(value = "startDate" ,required = false) String startDate,
            @RequestParam(value = "endDate" ,required = false) String endDate,
            @RequestParam(value = "pageNo" ,required = false) Integer pageNo,
            @RequestParam(value = "pageSize" ,required = false) Integer pageSize
    ){
        Map<String,Object>pramMap= new HashMap<>();
        pramMap.put("activityName", activityName);
        pramMap.put("ownerName", ownerName);
        pramMap.put("startDate", startDate);
        pramMap.put("endDate", endDate);
        pramMap.put("pageNo", (pageNo-1)*pageSize);
        pramMap.put("pageSize", pageSize);
        //调用业务层的方法处理前端的请求
        //多条件查询市场活动列表数据（市场活动名称，拥有者名称，开始日期，结束日期，页码，每页显示条数）--》返回：显示的数据，总条数
        PageinationVO<Activity> pageinationVO=activityService.queryActivityListForPageByCondition(pramMap);
        int totalPage = pageinationVO.getTotal()/pageSize;
        int mod=pageinationVO.getTotal()%pageSize;
        if(mod>0){
            totalPage+=1;
        }
        //返回的数据
        Map<String,Object>retMap=new HashMap<>();
        retMap.put("activityList", pageinationVO.getDataList());
        retMap.put("totalRows",pageinationVO.getTotal());
        retMap.put("totalPage", totalPage);
        return retMap;
    }
    @ResponseBody
    @RequestMapping("workbench/activity/queryAllUserList.do")
    public Object queryAllUserList(){
        List<User> userList= userService.queryAllUserList();
        return userList;
    }
    //新增数据
    @ResponseBody
    @RequestMapping("workbench/activity/saveCreateActivity.do")
    public Object saveCreateActivity(Activity activity, HttpServletRequest request){
        User sessionUser = (User)request.getSession().getAttribute("sessionUser");
        String uuid = UUIDUtils.getUUID();
        String s = DateUtils.formatDateTime(new Date());
        activity.setCreateTime(s);
        activity.setCreateBy(sessionUser.getId());
        activity.setId(uuid);
        try {
           int count= activityService.saveCreateActivity(activity);
           if(count!=1){
               return  Result.fail("创建失败");
           }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("创建失败");
        }
        return Result.success();
    }
    @ResponseBody
    @RequestMapping("workbench/activity/editActivity.do")
    public Object editActivity(String id){
        Map<String,Object>retMap= new HashMap<>();
        //根据标识
        List<User> userList = userService.queryAllUserList();
        retMap.put("userList", userList);

        Activity activity = activityService.queryActivit(id);
        retMap.put("activity", activity);
        return retMap;
    }
    @ResponseBody
    @RequestMapping("workbench/activity/deleteActivityById.do")
    public Object deleteActivityById(String []id){
        int count;
        try {
             count=activityService.deleteActivityById(id);
            if(count==0){
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }

        return Result.success(count);
    }
    //批量下载数据
    @RequestMapping("workbench/activity/exportActivityAll.do")
    public void exportActivityAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Activity> activityList = activityService.queryAllActList();
        //创建工作簿
        HSSFWorkbook wb= new HSSFWorkbook();
        //创建工作册
        HSSFSheet sheet = wb.createSheet();
        //创建行对象
        HSSFRow row = sheet.createRow(0);
        //创建单元格

        //第一行第一列
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("市场活动名称");

        //第一行第二列
        cell = row.createCell(1);
        cell.setCellValue("成本");

        //第一行第三列
        cell = row.createCell(2);
        cell.setCellValue("开始日期");

        //第一行第四列
        cell = row.createCell(3);
        cell.setCellValue("结束日期");

        //判断数据机构和是否有值
        if(null !=activityList && activityList.size()!=0){
            //循环遍历数据集合
            for (int i = 0; i < activityList.size(); i++) {
                //创建行
                 row = sheet.createRow(i + 1);

                //创建单元格
                cell = row.createCell(0);
                cell.setCellValue(activityList.get(i).getName());

               cell= row.createCell(1);
               cell.setCellValue(activityList.get(i).getCost());

               cell= row.createCell(2);
               cell.setCellValue(activityList.get(i).getStartDate());

               cell= row.createCell(3);
               cell.setCellValue(activityList.get(i).getEndDate());
            }
        }



        //设置中文文件名称
        String fileName = URLEncoder.encode("市场活动列表","UTF-8");

        //浏览器默认服务器传过去的是html，不是excel文件
        //设置响应类型:传输内容是流，并支持中文
        response.setContentType("application/octet-stream;charset=UTF-8");

        //设置响应头信息header，下载时以文件附件下载
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xls");

        //输出流对象
        OutputStream os = response.getOutputStream();
        wb.write(os);

        //强制刷新
        os.flush();
        os.close();
        wb.close();
    }


    @ResponseBody
    @RequestMapping("workbench/activity/importActivity.do")
    public Object importActivity(HttpServletRequest request,MultipartFile activityFile) throws Exception {
        //创建工作簿
        int count = 0;
        try {
            HSSFWorkbook wb=new HSSFWorkbook(activityFile.getInputStream());
            //从工作簿中获取工作测
            HSSFSheet sheet = wb.getSheetAt(0);
            //创建行
            HSSFRow row ;
            //获取第一个单元格
            HSSFCell cell ;
            int lastRowNum=sheet.getLastRowNum();
            List<Activity> activityList=new ArrayList<>();
            Activity activity=null;
            for (int i = 1; i <=lastRowNum ; i++) {
                activity= new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(request.getSession().getId());
                //获取当前行对象
                row=sheet.getRow(i);

                //获取当前行的第一个单元格
                cell=row.getCell(0);
                //获取单元格中的内容
                String activityName=cell.getStringCellValue();
                activity.setName(activityName);
                //获取当前行的第二个单元格
                cell=row.getCell(1);
                String cost =cell.getStringCellValue();
                activity.setCost(cost);
                //获取当前行的第三个单元格
                cell=row.getCell(1);
                String startDate =cell.getStringCellValue();
                activity.setStartDate(startDate);
                //获取当前行的第四个单元格
                cell=row.getCell(1);
                String endDate=cell.getStringCellValue();
                activity.setEndDate(endDate);
                activityList.add(activity);
            }
            count = activityService.saveCreateActivityList(activityList);
            if(count==0){
                return Result.fail("添加失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("添加失败");
        }
        return Result.success(count);
    }
}
