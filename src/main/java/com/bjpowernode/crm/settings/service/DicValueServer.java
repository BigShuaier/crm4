package com.bjpowernode.crm.settings.service;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.service
 * Desciption：
 * Date：2022/1/10
 * author:gu@15840026792
 */


import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.DicValueExt;

import java.util.List;

/**
 *谷宏帅
 *2022/1/10
 */
public interface DicValueServer {

    List<DicValue> queryAllDicValue();

    int saveDicValue(DicValue dicValue);

    DicValueExt qureyDicType(String id);

    int saveEditDicValue(DicValue dicValue);

    int deleteDicValue( String[] id,String[] code);

    /**
     * 查询数据字典值中的的线索状态
     * @param typecode
     * @return
     */
    List<DicValue> queryAllDicValueclueStateByTypeCode(String typecode);


}
