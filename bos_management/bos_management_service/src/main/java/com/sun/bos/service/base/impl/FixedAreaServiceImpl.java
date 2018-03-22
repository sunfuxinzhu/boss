package com.sun.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.base.CourierRepository;
import com.sun.bos.dao.base.FixedAreaRepository;
import com.sun.bos.dao.base.StandardRepository;
import com.sun.bos.dao.base.TakeTimeRepository;
import com.sun.bos.domain.base.Courier;
import com.sun.bos.domain.base.FixedArea;
import com.sun.bos.domain.base.TakeTime;
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
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        courier.setTakeTime(takeTime);
        fixedArea.getCouriers().add(courier);
        
    }
}
  
