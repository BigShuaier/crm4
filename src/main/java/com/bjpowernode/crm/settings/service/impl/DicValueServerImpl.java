package com.bjpowernode.crm.settings.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.settings.service.impl
 * Desciption：
 * Date：2022/1/10
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.DicValueExt;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicValueServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *谷宏帅
 *2022/1/10
 */
@Service
public class DicValueServerImpl implements DicValueServer {
    @Autowired
    DicValueMapper dicValueMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public List<DicValue> queryAllDicValue() {
        return dicValueMapper.queryAllDicValue();
    }

    @Override
    public int saveDicValue(DicValue dicValue) {
        dicValue.setId(UUIDUtils.getUUID());
        BoundListOperations boundListOperations = redisTemplate.boundListOps(dicValue.getTypeCode());
        boundListOperations.getOperations().delete(dicValue.getTypeCode());
        return dicValueMapper.insertSelective(dicValue);
    }

    @Override
    public DicValueExt qureyDicType(String id) {

        return dicValueMapper.selectByPrimaryKey1(id);
    }

    @Override
    public int saveEditDicValue(DicValue dicValue) {

        BoundListOperations boundListOperations = redisTemplate.boundListOps(dicValue.getTypeCode());

        boundListOperations.getOperations().delete(dicValue.getTypeCode());
        return dicValueMapper.updateByPrimaryKeySelective(dicValue);
    }

    @Override
    public int deleteDicValue(String[] id,String[] code) {
        Set<String> set=new HashSet<>();
        for (String c : code) {
            set.add(c);
        }
        for (Iterator<String> iterator = set.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            BoundListOperations boundListOperations = redisTemplate.boundListOps(next);
            boundListOperations.getOperations().delete(next);
        }

        return dicValueMapper.deleteCheckedId(id);
    }

    @Override
    public List<DicValue> queryAllDicValueclueStateByTypeCode(String typecode) {

        BoundListOperations boundListOperations = redisTemplate.boundListOps(typecode);

        List<DicValue> dicValues = (List<DicValue>) boundListOperations.range(0, -1);

        if(dicValues==null || dicValues.size()==0){
            dicValues = dicValueMapper.selectAllDicValueclueStateByTypeCode(typecode);

            for (DicValue dicValue : dicValues) {
                boundListOperations.leftPush(dicValue);
            }
            boundListOperations.expire(DateUtils.getRemainSecondsOneDay(new Date()), TimeUnit.SECONDS);

        }
        return dicValues;
    }


}
