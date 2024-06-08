package com.ncu.chatserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.ncu.chatserver.dao")
public class ChatServerApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootApplication.class);

    public static void main(String[] args) {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        SpringApplication.run(ChatServerApplication.class, args);
    }

}
