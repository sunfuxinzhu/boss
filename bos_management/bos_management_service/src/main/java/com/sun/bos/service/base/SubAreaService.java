package com.sun.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sun.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 上午12:34:29 <br/>       
 */
public interface SubAreaService {

    void save(SubArea model);

    Page<SubArea> findAll(Pageable pageable);

}
  
