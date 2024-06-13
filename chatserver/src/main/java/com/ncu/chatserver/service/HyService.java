package com.ncu.chatserver.service;

import com.ncu.chatserver.entity.HyyzVo;
import com.ncu.chatserver.dao.HyDao;
import com.ncu.chatserver.entity.Hy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class HyService{

    @Resource
    private HyDao hyDao;


    public int deleteByPrimaryKey(Integer id) {
        return hyDao.deleteByPrimaryKey(id);
    }


    public int insert(Hy record) {
        return hyDao.insert(record);
    }


    public int insertOrUpdate(Hy record) {
        return hyDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Hy record) {
        return hyDao.insertOrUpdateSelective(record);
    }


    public int insertSelective(Hy record) {
        return hyDao.insertSelective(record);
    }


    public Hy selectByPrimaryKey(Integer id) {
        return hyDao.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(Hy record) {
        return hyDao.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Hy record) {
        return hyDao.updateByPrimaryKey(record);
    }


    public int updateBatch(List<Hy> list) {
        return hyDao.updateBatch(list);
    }


    public int updateBatchSelective(List<Hy> list) {
        return hyDao.updateBatchSelective(list);
    }


    public int batchInsert(List<Hy> list) {
        return hyDao.batchInsert(list);
    }

    /**
     * 查询申请我为好友的列表
     * @param userId
     * @return
     */
    public List<HyyzVo> selectHyyzList(Integer userId) {
        return hyDao.selectHyyzList(userId);
    }

    /**
     * 查询好友
     * @param userId
     * @return
     */
    public List<HyyzVo> selectHyList(Integer userId) {
        return hyDao.selectHyList(userId);
    }

    public int isHy(Hy hy) {
        return hyDao.isHy(hy);
    }

    public List<String> selectGroupList(Integer userId) {
        return hyDao.selectGroupList(userId);
    }

    public int updateGroupToDefault(Integer userId, String groupName) {
        return hyDao.updateGroupToDefault(userId, groupName);
    }
}
