package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeMapper {
    int deleteByPrimaryKey(String code);

    int insert(DicType record);

    int insertSelective(DicType record);

    DicType selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(DicType record);

    int updateByPrimaryKey(DicType record);

    List<DicType> queryAllDicType();


    int deleteByPrimaryKeyBat(String []code);
}
