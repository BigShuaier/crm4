package com.bjpowernode.crm.workbench.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service.impl
 * Desciption：
 * Date：2022/1/17
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.mapper.ClueRemarkMapper;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *谷宏帅
 *2022/1/17
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    ClueRemarkMapper clueRemarkMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public PageinationVO <Clue>queryqueryClueListForPageByCondition(Map<String, Object> pramMap) {
        List<Clue> clues = clueMapper.selectClueListForPageByCondition(pramMap);
        PageinationVO<Clue> pageinationVO = new PageinationVO<>();
        pageinationVO.setDataList(clues);
        Integer totals=clueMapper.selectClueTotals();
        pageinationVO.setTotal(totals);
        return pageinationVO;
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insert(clue);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveUpdateClue(Clue clue) {
        return clueMapper.updateByPrimaryKey(clue);
    }

    @Override
    public int deleteClue(String[] id) {
        return clueMapper.deleteByIds(id);
    }

    @Override
    public Clue queryDetalClueById(String id) {
        return clueMapper.selectDetailClueById(id);
    }

    @Override
    public List<ClueRemark> queryClueRemarkById(String clueId) {
        return clueRemarkMapper.selectClueRemarkById(clueId);
    }

    @Override
    public int saveClueRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.insertSelective(clueRemark);
    }

    @Override
    public int saveUpdateRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.updateByPrimaryKeySelective(clueRemark);
    }

    @Override
    public int deleteClueRemark(String id) {
        return clueRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Activity> queryClueRemarkRelation(String clueId) {
        return activityMapper.selectClueRemarkRelation(clueId);
    }

    @Override
    public int deleteClueRemarkRelation(String clueId, String id) {
        Map<String,Object>pramMap=new HashMap<>();
        pramMap.put("clueId", clueId);
        pramMap.put("id", id);
        return clueRemarkMapper.deleteClueRemarkRelation(pramMap);
    }

    @Override
    public List<Activity> queryActivityByName(String clueId, String name) {
        Map<String,Object>pramMap=new HashMap<>();
        pramMap.put("clueId", clueId);
        pramMap.put("name",name);
        return activityMapper.selectActivityByName(pramMap);
    }

    @Override
    public int saveBundActivity(List<ClueActivityRelation> clueActivityRelations) {
        return clueActivityRelationMapper.insertBundActivity(clueActivityRelations);
    }


}
