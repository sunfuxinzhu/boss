package com.sun.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.base.CourierRepository;
import com.sun.bos.domain.base.Courier;
import com.sun.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午4:11:32 <br/>       
 */

@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findAll(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
    // 批量删除
    @RequiresPermissions("batchDel")
    // 在调用方法时,框架就会检查当前用户是否有对应的权限,如果有就放行,没有就抛异常
    @Override
    public void batchDel(String ids) {
        //ids要判空
        if (StringUtils.isNotEmpty(ids)) {
            String[] strings = ids.split(",");
            for (String string : strings) {
                courierRepository.updateDelTagById(Long.parseLong(string));
            }
        }
        
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public List<Courier> listajax() {
        return courierRepository.findByDeltagIsNull();
    }

}
  
