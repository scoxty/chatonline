package com.ncu.chatserver.controller;

<<<<<<< HEAD
import com.ncu.chatserver.config.CaptureConfig;
=======

>>>>>>> 75a9c5c2f99b9c3ad7beebbc8b0dd7fa2ef6a908
import com.ncu.chatserver.config.CaptureConfig;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping
public class CaptureController {

    @RequestMapping("/api/captcha")
    public void captcha(@RequestParam String key, HttpServletRequest request, HttpServletResponse response) throws Exception {
<<<<<<< HEAD
//        // png类型
=======
        // png类型
>>>>>>> 75a9c5c2f99b9c3ad7beebbc8b0dd7fa2ef6a908
        SpecCaptcha captcha = new SpecCaptcha(135, 33, 4);
        captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
        CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
        CaptchaUtil.out(captcha, request, response);

<<<<<<< HEAD
        // 算术类型
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(135, 33);
//        captcha.setLen(4);  // 几位数运算，默认是两位
//        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
//        captcha.text();  // 获取运算的结果：5
//        CaptchaUtil.out(captcha, request, response);
=======
>>>>>>> 75a9c5c2f99b9c3ad7beebbc8b0dd7fa2ef6a908
    }
}
