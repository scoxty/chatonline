package com.ncu.chatserver.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.Digester;
import com.github.pagehelper.PageInfo;
import com.ncu.chatserver.common.Result;
import com.ncu.chatserver.config.CaptureConfig;
import com.ncu.chatserver.entity.Params;
import com.ncu.chatserver.entity.User;
import com.ncu.chatserver.service.UserService;
import com.ncu.chatserver.utils.JwtTokenUtils;
import com.wf.captcha.utils.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private Digester digester;

    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestParam String key, HttpServletRequest request) {
        String value = CaptureConfig.CAPTURE_MAP.get(key);
        if (ObjectUtil.isEmpty(user.getVerCode())) {
            return Result.error("请输入验证码");
        }
        if (!user.getVerCode().toLowerCase().equals(value)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return Result.error("验证码不正确");
        }
        User loginUser = userService.login(user);
        return Result.success(loginUser);
    }

    @PostMapping("/password")
    public Result updatePassword(@RequestBody User user) {
        User currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            return Result.error("token验证失效，请重新登陆");
        }
        if (!digester.digestHex(user.getPassword()).equals(currentUser.getPassword())) {
            return Result.error("原密码输入错误");
        }
        user.setPassword(user.getNewPassword());
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getId() == null) {
            userService.add(user);
        } else {
            userService.update(user);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<User> list = userService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<User> info = userService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }
}
