package com.sun.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**  
 * ClassName:RedisTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午5:50:59 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
    @Autowired
    private RedisTemplate<String , String > redisTemplate;
    
    @Test
    public void test(){
        //redisTemplate.opsForValue().set("aaa", "aassssssss");
       // redisTemplate.opsForValue().set("bbb", "4444444", 10, TimeUnit.SECONDS);
        redisTemplate.delete("aaa");
    }
}
  
