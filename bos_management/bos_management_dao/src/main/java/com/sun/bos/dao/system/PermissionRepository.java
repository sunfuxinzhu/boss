package com.sun.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.bos.domain.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 上午12:39:45 <br/>       
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
  
