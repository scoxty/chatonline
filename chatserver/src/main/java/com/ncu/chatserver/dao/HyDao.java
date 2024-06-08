package com.ncu.chatserver.dao;

import com.ncu.chatserver.entity.HyyzVo;
import com.ncu.chatserver.entity.Hy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HyDao {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(Hy record);

    int insertOrUpdate(Hy record);

    int insertOrUpdateSelective(Hy record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Hy record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    Hy selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Hy record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Hy record);

    int updateBatch(List<Hy> list);

    int updateBatchSelective(List<Hy> list);

    int batchInsert(@Param("list") List<Hy> list);

    /**
     * 查询
     * @param userId
     * @return
     */
    List<HyyzVo> selectHyyzList(Integer userId);

    /**
     * 查询好友
     * @param userId
     * @return
     */
    List<HyyzVo> selectHyList(Integer userId);


    int isHy(Hy hy);

    List<String> selectGroupList(Integer userId);

    int updateGroupToDefault(Integer userId, String groupName);
}
