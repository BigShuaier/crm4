package com.bjpowernode.crm.workbench.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service.impl
 * Desciption：
 * Date：2022/1/12
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *谷宏帅
 *2022/1/12
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    UserService userService;
    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public PageinationVO<Activity> queryActivityListForPageByCondition(Map<String, Object> pramMap) {

        PageinationVO<Activity> pageinationVO=new PageinationVO<>();

        List<Activity> activityList=activityMapper.selectActivityListForPageByCondition(pramMap);

        Integer totals=activityMapper.selectTotal(pramMap);

        pageinationVO.setDataList(activityList);

        pageinationVO.setTotal(totals);
        return  pageinationVO;
    }

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertSelective(activity);
    }

    @Override
    public Activity queryActivit(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteActivityById(String[] id) {
        return activityMapper.deleteActivityByIds(id);
    }

    @Override
    public List<Activity> queryAllActList() {
         return activityMapper.selectAllAcitivity();
    }

    @Override
    public int saveCreateActivityList(List<Activity> activityList) {
        return activityMapper.insertActivityList(activityList);
    }

    @Override
    public Activity queryActivitById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int saveActivityById(Activity activity) {
        return activityMapper.updateByPrimaryKey(activity);
    }

    @Override
    public List<Activity> queryActivitList(String[] id) {
        return activityMapper.selectListByPrimaryKey(id);
    }


}
