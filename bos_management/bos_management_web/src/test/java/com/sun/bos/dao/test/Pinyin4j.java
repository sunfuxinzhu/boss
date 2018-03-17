package com.sun.bos.dao.test;

import com.sun.utils.PinYin4jUtils;

/**  
 * ClassName:Pinyin4j <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午8:59:34 <br/>       
 */
public class Pinyin4j {
    public static void main(String[] args) {
        String string = PinYin4jUtils.hanziToPinyin("广东省");
        System.out.println(string);
    }
}
  
