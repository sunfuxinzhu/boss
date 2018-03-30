package com.sun.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sun.bos.domain.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 上午12:39:45 <br/>       
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
//    select * from t_Permission p inner join t_role_permission rp on p.c_id=rp.c_permission_id
//            inner join t_role r on r.c_id=rp.c_role_id inner join t_user_role ur on ur.c_role_id=r.c_id
//            inner join t_user u on u.c_id=ur.c_user_id where u.c_id=3161
    
    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id=?")
    List<Permission> findbyUid(Long uid);

}
  
