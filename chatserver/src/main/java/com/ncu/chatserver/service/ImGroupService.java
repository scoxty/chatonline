package com.ncu.chatserver.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncu.chatserver.dao.ImGroupDao;
import com.ncu.chatserver.entity.ImGroup;
import com.ncu.chatserver.entity.User;
import com.ncu.chatserver.exception.CustomException;
import com.ncu.chatserver.utils.JwtTokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ImGroupService {

    @Resource
    private ImGroupDao imGroupDao;

    public ImGroup add(ImGroup imGroup) {
        imGroupDao.insertSelective(imGroup);
        return imGroup;
    }

    public void delete(Integer id) {
        imGroupDao.deleteByPrimaryKey(id);
    }

    public void update(ImGroup imGroup) {
        imGroupDao.updateByPrimaryKeySelective(imGroup);
    }

    public ImGroup findById(Integer id) {
        return imGroupDao.selectByPrimaryKey(id);
    }

    public List<ImGroup> findAll() {
        return imGroupDao.findBySearch(null);
    }

    public PageInfo<ImGroup> findPage(ImGroup search, Integer pageNum, Integer pageSize) {
        User user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException("token验证失效");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ImGroup> all = findByCondition(user, search);

        return PageInfo.of(all);
    }

    public List<ImGroup> findByCondition(User user, ImGroup search) {
		return imGroupDao.findBySearch(search);
    }
}