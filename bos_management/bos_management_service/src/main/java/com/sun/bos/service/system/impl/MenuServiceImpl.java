package com.sun.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.system.MenuRepository;
import com.sun.bos.domain.system.Menu;
import com.sun.bos.domain.system.User;
import com.sun.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:19:38 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {
        //判断是否设为父菜单
        if (model.getParentMenu()!=null&&model.getParentMenu().getId()==null) {
            model.setParentMenu(null);
        }
        
        
        menuRepository.save(model);  
        
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findbyUser(User user) {
        if ("admin".equals(user.getUsername())) {
            return menuRepository.findAll();
        }
        return menuRepository.findbyUser(user.getId());
    }
}
  
