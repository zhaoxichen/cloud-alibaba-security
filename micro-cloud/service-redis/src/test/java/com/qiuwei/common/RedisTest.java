package com.qiuwei.common;

import com.qiuwei.common.demo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Version 1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void redisTest() {
        // redis存储数据
        String key = "name";
        redisTemplate.opsForValue().set(key, "qiuwei");
        // 获取数据
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("key:" + key + " ,value：" + value);

        User user = new User("qw",12);

        String userKey = "qiuwei";
        redisTemplate.opsForValue().set(userKey, user);
        User newUser = (User) redisTemplate.opsForValue().get(userKey);
        System.out.println("key:" + userKey + " ,value：" + newUser);

    }

}
