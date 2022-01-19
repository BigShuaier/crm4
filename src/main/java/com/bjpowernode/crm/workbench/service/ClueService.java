package com.bjpowernode.crm.workbench.service;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service
 * Desciption：
 * Date：2022/1/17
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.workbench.domain.Clue;

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
}
