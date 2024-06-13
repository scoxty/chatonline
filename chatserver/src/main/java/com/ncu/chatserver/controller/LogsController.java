package com.ncu.chatserver.controller;

import com.ncu.chatserver.common.Result;
import com.ncu.chatserver.entity.Logs;
import com.ncu.chatserver.service.LogsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
*  描述：红包记录相关接口
*/
@RestController
@RequestMapping(value = "/api/logs")
public class LogsController {

    @Resource
    private LogsService logsService;

    /**
     * 描述：新增
     */
    @PostMapping
    public Result add(@RequestBody Logs logs) {

        logsService.add(logs);
        return Result.success(logs);
    }

    /**
     * 描述：根据ID删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        logsService.delete(id);
        return Result.success();
    }

    /**
     * 描述：更新
     */
    @PutMapping
    public Result update(@RequestBody Logs logs) {

        logsService.update(logs);
        return Result.success();
    }

    /**
     * 描述：根据ID查询
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Logs logs = logsService.findById(id);
        return Result.success(logs);
    }

    /**
     * 描述：查询所有
     */
    @GetMapping
    public Result all() {
        return Result.success(logsService.findAll());
    }

    /**
     * 描述：分页查询
     */
    @PostMapping("/page")
    public Result page(@RequestBody Logs search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(logsService.findPage(search, pageNum, pageSize));
    }




}
