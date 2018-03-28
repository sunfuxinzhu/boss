package com.sun.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:18:37 <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByParentMenuIsNull();

}
  
