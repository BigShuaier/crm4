package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<Activity> selectActivityListForPageByCondition(Map<String, Object> pramMap);

    Integer selectTotal(Map<String, Object> pramMap);


    int deleteActivityByIds(String[] id);

    List<Activity> selectAllAcitivity();

    int insertActivityList(List<Activity> activityList);

    Activity selectActivityById(String id);

    List<Activity > selectListByPrimaryKey(String[] id);

    List<Activity> selectClueRemarkRelation(String clueId);

    List<Activity> selectActivityByName(Map<String, Object> pramMap);
}
