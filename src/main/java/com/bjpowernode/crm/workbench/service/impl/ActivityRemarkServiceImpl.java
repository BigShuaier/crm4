package com.bjpowernode.crm.workbench.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service.impl
 * Desciption：
 * Date：2022/1/15
 * author:gu@15840026792
 */

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *谷宏帅
 *2022/1/15
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;
    @Override
    public List<ActivityRemark> queryAcivityRemarkListByRemarkId(String activityId) {
        return activityRemarkMapper.selectAcivityRemarkListByRemarkId(activityId);
    }

    @Override
    public int updateRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
    }

    @Override
    public int deleteActivityRemark(String id) {
        return activityRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveCreateRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertSelective(activityRemark);
    }
}
