package com.sun.bos.service.take_delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.take_delivery.PromotionRepository;
import com.sun.bos.domain.take_delivary.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午9:07:35 <br/>       
 */

public interface PromotionService {

    void save(Promotion model);

    Page<Promotion> findAll(Pageable pageable);
   
}
  
