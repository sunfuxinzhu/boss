package com.sun.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.take_delivery.WayBillRepository;
import com.sun.bos.domain.take_delivary.WayBill;
import com.sun.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午11:49:42 <br/>       
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
    @Autowired
    private WayBillRepository wayBillRepository;

    @Override
    public void save(WayBill model) {
        wayBillRepository.save(model);
    }
}
  
