package com.ncu.chatserver.config;

import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.MD5;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zmy
 * @Date: 2024年06月17日 11:07
 */
@Configuration
public class MD5Config {
    public static final byte[] SALT = {1, 0, 1};

    @Bean
    public Digester digester(){
        return MD5.create().setSalt(SALT);
    }
}
