package com.sun.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sun.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 上午12:41:23 <br/>       
 */
public interface PermissionService {

    void save(Permission model);

    Page<Permission> findAll(Pageable pageable);

}
  
