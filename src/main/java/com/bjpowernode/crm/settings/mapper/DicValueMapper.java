package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.DicValueExt;

import java.util.List;

public interface DicValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(DicValue record);

    int insertSelective(DicValue record);

    DicValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DicValue record);

    int updateByPrimaryKey(DicValue record);

    List<DicValue> queryAllDicValue();

    DicValueExt selectByPrimaryKey1(String id);

    int deleteCheckedId(String[] id);

    List<DicValue> selectAllDicValueclueStateByTypeCode(String typecode);

    String selectDicTypeCode(String id);
}
