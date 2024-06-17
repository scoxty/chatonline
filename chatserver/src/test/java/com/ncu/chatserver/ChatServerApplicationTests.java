package com.ncu.chatserver;

import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatServerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(MD5.create().digestHex("123456"));
    }

}
