package com.sun.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sun.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:53:13 <br/>       
 */
public interface StandardService {

    void save(Standard model);

    Page<Standard> findAll(Pageable pageable);


}
  
