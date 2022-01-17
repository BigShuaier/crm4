package com.bjpowernode.crm.workbench.service;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.service
 * Desciption：
 * Date：2022/1/12
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 *谷宏帅
 *2022/1/12
 */
public interface ActivityService {

    PageinationVO<Activity> queryActivityListForPageByCondition(Map<String, Object> pramMap);


    int saveCreateActivity(Activity activity);

    Activity queryActivit(String id);

    int deleteActivityById(String[] id);

    List<Activity> queryAllActList();

    int saveCreateActivityList(List<Activity> activityList);

    Activity queryActivitById(String id);

    int saveActivityById(Activity activity);

    List<Activity> queryActivitList(String[] id);
}
