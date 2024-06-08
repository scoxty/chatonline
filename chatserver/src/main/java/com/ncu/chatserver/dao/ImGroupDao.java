package com.ncu.chatserver.dao;

import com.ncu.chatserver.entity.ImGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ImGroupDao extends Mapper<ImGroup> {

	List<ImGroup> findBySearch(@Param("search") ImGroup search);

}
