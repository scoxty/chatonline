package com.ncu.chatserver.controller;

import com.ncu.chatserver.entity.HyyzVo;
import com.ncu.chatserver.utils.JwtTokenUtils;
import com.ncu.chatserver.common.Result;
import com.ncu.chatserver.entity.Hy;
import com.ncu.chatserver.entity.Params;
import com.ncu.chatserver.entity.User;
import com.ncu.chatserver.service.HyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hy")
public class HyController {

    @Resource
    private HyService hyService;

    @GetMapping("/list")
    public Result list(Params params){
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        Integer userId = JwtTokenUtils.getCurrentUser().getId();
        List<HyyzVo> list = hyService.selectHyList(userId);
        System.out.println(list);
        PageInfo<HyyzVo> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    @GetMapping("/hyyzlist")
    public Result hyyzList(Params params){
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        Integer userId = JwtTokenUtils.getCurrentUser().getId();
        List<HyyzVo> list = hyService.selectHyyzList(userId);
        PageInfo<HyyzVo> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Hy hy){
        User user = JwtTokenUtils.getCurrentUser();
        hy.setUserId(user.getId());
        if (hy.getUserId().equals(hy.getHyUserId())) {
            return Result.error("不能添加自己为好友");
        }
        if (hyService.isHy(hy) > 0) {
            return Result.error("你已经申请过了或者此人已是你的好友");
        }
        if (hyService.insertSelective(hy) < 1) {
            return Result.error("提交失败");
        }
        return Result.success("提交成功!");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Hy hy){
        if (hyService.updateByPrimaryKeySelective(hy) < 1) {
            return Result.error("提交失败");
        }
        return Result.success("提交成功!");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        if (hyService.deleteByPrimaryKey(id) < 1) {
            return Result.error("提交失败");
        }
        return Result.success("提交成功!");
    }

    // 获取分组列表
    @GetMapping("/groups")
    public Result getGroups(){
        Integer userId = JwtTokenUtils.getCurrentUser().getId();
        List<String> groups = hyService.selectGroupList(userId);
        return Result.success(groups);
    }

    // 添加或更新分组
    @PostMapping("/updateGroup")
    public Result updateGroup(@RequestBody Hy hy){
        Integer userId = JwtTokenUtils.getCurrentUser().getId();
        hy.setUserId(userId);
        if (hyService.isHy(hy) > 0) {
            hyService.updateByPrimaryKeySelective(hy);
            return Result.success("分组更新成功!");
        } else {
            hyService.insertSelective(hy);
            return Result.success("分组添加成功!");
        }
    }

    // 删除分组
    @PostMapping("/deleteGroup")
    public Result deleteGroup(@RequestBody Map<String, String> request){
        Integer userId = JwtTokenUtils.getCurrentUser().getId();
        String groupName = request.get("groupName");
        hyService.updateGroupToDefault(userId, groupName);
        return Result.success("分组删除成功!");
    }
}
