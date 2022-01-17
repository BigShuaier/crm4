package com.bjpowernode.crm.workbench.service;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service
 * Desciption：
 * Date：2022/1/15
 * author:gu@15840026792
 */

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 *谷宏帅
 *2022/1/15
 */
public interface ActivityRemarkService {
    List<ActivityRemark> queryAcivityRemarkListByRemarkId(String activityId);

    int updateRemark(ActivityRemark activityRemark);

    int deleteActivityRemark(String id);

    int saveCreateRemark(ActivityRemark activityRemark);

}
