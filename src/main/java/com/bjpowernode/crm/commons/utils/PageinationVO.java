package com.bjpowernode.crm.commons.utils;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.commons.utils
 * Desciption：
 * Date：2022/1/12
 * author:gu@15840026792
 */

import java.util.List;

/**
 *谷宏帅
 *2022/1/12
 */
public class PageinationVO<T> {
    private List<T> dataList;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    private Integer total;
}
