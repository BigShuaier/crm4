package com.bjpowernode.crm.workbench.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service.impl
 * Desciption：
 * Date：2022/1/17
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
