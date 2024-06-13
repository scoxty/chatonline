package com.ncu.chatserver.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ncu.chatserver.common.Result;
import com.ncu.chatserver.entity.Notice;
import com.ncu.chatserver.entity.Params;
import com.ncu.chatserver.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping
    public Result update(@RequestBody Notice notice) {
        if (ObjectUtil.isEmpty(notice.getId())) {
            noticeService.add(notice);
        } else {
            noticeService.update(notice);
        }
        return Result.success();
    }

    @GetMapping
    public Result findTop() {
        List<Notice> list = noticeService.findTop();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Notice> info = noticeService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.delete(id);
        return Result.success();
    }

}
