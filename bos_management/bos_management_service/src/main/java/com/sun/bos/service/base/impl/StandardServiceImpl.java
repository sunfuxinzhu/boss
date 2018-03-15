package com.sun.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.base.StandardRepository;
import com.sun.bos.domain.base.Standard;
import com.sun.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:54:27 <br/>       
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
          
        return standardRepository.findAll(pageable);
    }

   
    
}
  
