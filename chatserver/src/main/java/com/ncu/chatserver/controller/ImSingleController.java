package com.ncu.chatserver.controller;

import cn.hutool.core.lang.Dict;
import com.ncu.chatserver.utils.FilePortUtil;
import com.ncu.chatserver.common.Result;
import com.ncu.chatserver.entity.ImSingle;
import com.ncu.chatserver.service.ImSingleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
*  描述：单人聊天相关接口
*/
@RestController
@RequestMapping(value = "/api/imsingle")
public class ImSingleController {

    @Resource
    private ImSingleService imSingleService;

    /**
     * 查询所有消息
     */
    @GetMapping
    public Result findByFromUsername(@RequestParam String fromUser, @RequestParam String toUser) {
        List<ImSingle> all = imSingleService.findByUsername(fromUser, toUser);
        return Result.success(all);
    }

    /**
     * 查询未读消息数量
     * @return 未读消息数量
     */
    @GetMapping("/unReadNums")
    public Result findUnReadNums(@RequestParam String toUsername) {
        Dict dict = imSingleService.findUnReadNums(toUsername);
        return Result.success(dict);
    }

    /**
     * 下载聊天记录
     * @param fromUser
     * @param toUser


     * @return

     */
    @GetMapping("/don")
    public void donFromUsername(@RequestParam String fromUser, @RequestParam String toUser,HttpServletResponse response) {
        //从数据库查询出来的结果集
        List<ImSingle> all = imSingleService.findByUsername(fromUser, toUser);
        System.out.println(all);

        //导出的表格名称
        String title = "聊天记录";

        //表中第一行表头字段
        String [] headers ={"发送人","接收人","时间","内容","类型"};

        //具体需要写入excel需要的那些字段，这些字段从PprojectSalary类中拿，也就是上面的实际数据结果集的泛型
        List<String> list = Arrays.asList("fromuser","touser","time","content","type");

        try {
            FilePortUtil.exportExcel(response,title,headers,all,list);
        }catch (Exception e){

        }
    }

}
