package com.sun.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.take_delivery.PromotionRepository;
import com.sun.bos.domain.take_delivary.PageBean;
import com.sun.bos.domain.take_delivary.Promotion;
import com.sun.bos.service.take_delivery.PromotionService;

/**  
 * ClassName:PromotionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午9:07:52 <br/>       
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
          
        return promotionRepository.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> findAll4Fore(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);
        PageBean<Promotion> pageBean = new PageBean<>();
        Page<Promotion> p = findAll(pageable);
        pageBean.setList(p.getContent());
        pageBean.setTotal(p.getTotalElements());
        
        return pageBean;
    }
}
  
