package com.bjpowernode.crm.workbench.service.impl;/**
 * ClassName:${Name}
 * Package：com.bjpowernode.crm.workbench.service.impl
 * Desciption：
 * Date：2022/1/17
 * author:gu@15840026792
 */

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.PageinationVO;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *谷宏帅
 *2022/1/17
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    ClueRemarkMapper clueRemarkMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ContactsMapper contactsMapper;
    @Autowired
    ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    TranMapper tranMapper;
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

    @Override
    public int deleteClue(String[] id) {
        return clueMapper.deleteByIds(id);
    }

    @Override
    public Clue queryDetalClueById(String id) {
        return clueMapper.selectDetailClueById(id);
    }

    @Override
    public List<ClueRemark> queryClueRemarkById(String clueId) {
        return clueRemarkMapper.selectClueRemarkById(clueId);
    }

    @Override
    public int saveClueRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.insertSelective(clueRemark);
    }

    @Override
    public int saveUpdateRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.updateByPrimaryKeySelective(clueRemark);
    }

    @Override
    public int deleteClueRemark(String id) {
        return clueRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Activity> queryClueRemarkRelation(String clueId) {
        return activityMapper.selectClueRemarkRelation(clueId);
    }

    @Override
    public int deleteClueRemarkRelation(String clueId, String id) {
        Map<String,Object>pramMap=new HashMap<>();
        pramMap.put("clueId", clueId);
        pramMap.put("id", id);
        return clueRemarkMapper.deleteClueRemarkRelation(pramMap);
    }

    @Override
    public List<Activity> queryActivityByName(String clueId, String name) {
        Map<String,Object>pramMap=new HashMap<>();
        pramMap.put("clueId", clueId);
        pramMap.put("name",name);
        return activityMapper.selectActivityByName(pramMap);
    }

    @Override
    public int saveBundActivity(List<ClueActivityRelation> clueActivityRelations) {
        return clueActivityRelationMapper.insertBundActivity(clueActivityRelations);
    }

    @Override
    public void doconvert(Map<String, Object> pramMap) {
        //获取当前用户

        //获取当前线索对象
        Clue clue = clueMapper.selectByPrimaryKey((String) pramMap.get("clueId"));
        //创建客户对象
        Customer customer = new Customer();
        //完成客户对象信息

        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(clue.getOwner());
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        User user = (User) pramMap.get("sessionUser");
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setDescription(clue.getDescription());
        customer.setName(clue.getFullName());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());


        //将客户信息插入到客户表当中
        customerMapper.insertSelective(customer);

        //创建联系人对象
        Contacts contacts =new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setAddress(clue.getAddress());
        contacts.setCustomerId(customer.getId());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullName(clue.getFullName());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());

        //将联系人信息插入到联系人表中
        contactsMapper.insertSelective(contacts);

        //获取线索备注表信息
        List<ClueRemark> clueRemarks = clueRemarkMapper.selectClueRemarkById((String) pramMap.get("clueId"));
        //创建联系人备注
        List<ContactsRemark>contactsRemarkList=new ArrayList<>();
        ContactsRemark contactsRemark=null;
        for (ClueRemark clueRemark : clueRemarks) {
            contactsRemark=new ContactsRemark();
            contactsRemark.setId(UUIDUtils.getUUID());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setCreateBy(user.getId());
            contactsRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemarkList.add(contactsRemark);
        }


        contactsRemarkMapper.insertcontactsRemarkList(contactsRemarkList);

        //客户备注表
        List<CustomerRemark>customerRemarkList=new ArrayList<>();
        CustomerRemark customerRemark=null;
        for (ClueRemark clueRemark : clueRemarks) {
            customerRemark=new CustomerRemark();
            customerRemark.setId(UUIDUtils.getUUID());
            customerRemark.setCreateBy(user.getId());
            customerRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setCustomerId(customer.getId());
            customerRemarkList.add(customerRemark);
        }
        //将客户备注信息添加到客户信息备注表中
        customerRemarkMapper.insertcustomerRemarkList(customerRemarkList);

        //获取线索市场活动关系表
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.selectByClueId((String) pramMap.get("clueId"));
        List<ContactsActivityRelation>contactsActivityRelations=new ArrayList<>();
        ContactsActivityRelation contactsActivityRelation=null;
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtils.getUUID());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelations.add(contactsActivityRelation);
        }
        //将联系人市场活动关系信息插入表中
       contactsActivityRelationMapper.insertcontactsActivityRelations(contactsActivityRelations);

        //判断是否产生交易
        if((Boolean) pramMap.get("isCreateTransaction")==true){
            //创建交易对象
            Tran tran = new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setActivityId((String)pramMap.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setDescription(clue.getDescription());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setExpectedDate((String)pramMap.get("expectedClosingDate"));
            tran.setMoney((String)pramMap.get("amountOfMoney"));
            tran.setName((String)pramMap.get("tradeName"));
            tran.setOrderNo(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            tran.setOwner(clue.getOwner());
            tran.setSource(clue.getSource());
            tran.setStage(clue.getState());
            tranMapper.insertSelective(tran);
        }

        //删除线索数据
        clueMapper.deleteByPrimaryKey((String)pramMap.get("clueId"));
        //删除线索备注数据
        clueRemarkMapper.deleteByClueId((String)pramMap.get("clueId"));
        //删除线索市场活动关系表数据
        clueActivityRelationMapper.deleteCatmByClueId((String)pramMap.get("clueId"));
    }
}
