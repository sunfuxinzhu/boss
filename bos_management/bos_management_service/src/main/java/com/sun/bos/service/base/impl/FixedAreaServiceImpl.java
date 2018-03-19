package com.sun.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.base.FixedAreaRepository;
import com.sun.bos.dao.base.StandardRepository;
import com.sun.bos.domain.base.FixedArea;
import com.sun.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:21:03 <br/>       
 */
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }
}
  
