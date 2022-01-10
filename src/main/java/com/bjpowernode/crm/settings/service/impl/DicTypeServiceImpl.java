package com.bjpowernode.crm.settings.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.service.impl
 * Desciption：
 * Date：2022/1/8
 * author:gu@15840026792
 */

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *谷宏帅
 *2022/1/8
 */
@Service
public class DicTypeServiceImpl implements DicTypeService {
    @Autowired
    DicTypeMapper dicTypeMapper;
    @Override
    public List<DicType> queryAllDicType() {
        return dicTypeMapper.queryAllDicType();
    }

    @Override
    public int saveDicType(DicType dicType) {
        dicTypeMapper.insert(dicType);
        return 0;
    }

    @Override
    public DicType checkCode(String code) {
        return dicTypeMapper.selectByPrimaryKey(code);
    }

    @Override
    public int saveEditDicType(DicType dicType) {
        return dicTypeMapper.updateByPrimaryKeySelective(dicType);
    }

    @Override
    public int deleteDicType(String []code){
        return dicTypeMapper.deleteByPrimaryKeyBat(code);
    }


}
