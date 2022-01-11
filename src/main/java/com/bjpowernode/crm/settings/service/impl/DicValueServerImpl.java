package com.bjpowernode.crm.settings.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.service.impl
 * Desciption：
 * Date：2022/1/10
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *谷宏帅
 *2022/1/10
 */
@Service
public class DicValueServerImpl implements DicValueServer {
    @Autowired
    DicValueMapper dicValueMapper;
    @Override
    public List<DicValue> queryAllDicValue() {
        return dicValueMapper.queryAllDicValue();
    }

    @Override
    public int saveDicValue(DicValue dicValue) {
        dicValue.setId(UUIDUtils.getUUID());
        return dicValueMapper.insertSelective(dicValue);
    }

    @Override
    public DicValue qureyDicType(String id) {

        return dicValueMapper.selectByPrimaryKey1(id);
    }

    @Override
    public int saveEditDicValue(DicValue dicValue) {
        return dicValueMapper.updateByPrimaryKeySelective(dicValue);
    }

    @Override
    public int deleteDicValue(String[] id) {

        return dicValueMapper.deleteCheckedId(id);
    }
}
