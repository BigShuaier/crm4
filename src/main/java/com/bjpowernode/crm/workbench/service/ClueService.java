package com.bjpowernode.crm.workbench.service;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service
 * Desciption：
 * Date：2022/1/17
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Map;

/**
 *谷宏帅
 *2022/1/17
 */
public interface ClueService {

    PageinationVO <Clue>queryqueryClueListForPageByCondition(Map<String,Object>pramMap);

    int saveCreateClue(Clue clue);

    Clue queryClueById(String id);

    int saveUpdateClue(Clue clue);

    /**
     * 批量删除线索记录
     * @param id
     * @return
     */
    int deleteClue(String[] id);

    Clue queryDetalClueById(String id);

    List<ClueRemark> queryClueRemarkById(String clueId);

    int saveClueRemark(ClueRemark clueRemark);

    /**
     * 根据id更新线索备注
     * @param clueRemark
     * @return
     */
    int saveUpdateRemark(ClueRemark clueRemark);

    /**
     * 根据主键id删除备注信息
     * @param id
     * @return
     */
    int deleteClueRemark(String id);

    List<Activity> queryClueRemarkRelation(String clueId);

    int deleteClueRemarkRelation(String clueId, String id);

    List<Activity> queryActivityByName(String clueId, String name);



    int saveBundActivity(List<ClueActivityRelation> clueActivityRelations);

    void doconvert(Map<String, Object> pramMap);
}
