package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {





    /**
     * 根据市场活动标识查询备注信息
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectAcivityRemarkListByRemarkId(String activityId);



    int deleteByPrimaryKey(String id);

    int insert(ActivityRemark record);

    int insertSelective(ActivityRemark record);

    ActivityRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityRemark record);

    int updateByPrimaryKey(ActivityRemark record);
}
