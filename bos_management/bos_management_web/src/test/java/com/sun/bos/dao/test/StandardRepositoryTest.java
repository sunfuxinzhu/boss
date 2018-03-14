package com.sun.bos.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.base.StandardRepository;
import com.sun.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月13日 下午12:43:23 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    @Autowired
    private StandardRepository standardRepository;
    @Test
    public void test1() {
        List<Standard> list = standardRepository.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void test2() {
        Standard standard = new Standard();
        standard.setName("张三三");
        standard.setMaxWeight(100);
        standardRepository.save(standard);
    }
    @Test
    public void test3() {
        Standard standard = new Standard();
        standard.setId(2L);
        standard.setName("张三三");
        standard.setMaxWeight(999);
        standardRepository.save(standard);
    }
    @Test
    public void test4() {
        Standard standard = standardRepository.findOne(2L);
        System.out.println(standard);
    }
    @Test
    public void test5() {
        standardRepository.delete(2L);
    }
    @Test
    public void test6() {
        List<Standard> list = standardRepository.findByName("张三");
        for (Standard standard : list) {
            System.out.println(standard);
            
        }
    }
    @Test
    public void test7() {
        List<Standard> list = standardRepository.findByNameLike("%张三%");
        for (Standard standard : list) {
            System.out.println(standard);
            
        }
    }
    @Test
    public void test8() {
        List<Standard> list = standardRepository.findByNameAndMaxWeight("张三", 100);
        for (Standard standard : list) {
            System.out.println(standard);
            
        }
    }
    
   
    @Test
    public void test9() {
        standardRepository.updateMaxWeightByName(500,"张三");
    }

    @Test
    public void test10() {
        standardRepository.deleteByName("张三");
    }
}
  
