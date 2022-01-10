package com.bjpowernode.crm.settings.service;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.service.impl
 * Desciption：
 * Date：2022/1/8
 * author:gu@15840026792
 */

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

/**
 *谷宏帅
 *2022/1/8
 */
public interface DicTypeService {
    List<DicType>queryAllDicType();

     int saveDicType(DicType dicType);

    DicType checkCode(String code);

    int saveEditDicType(DicType dicType);

    int deleteDicType(String []code);
}
