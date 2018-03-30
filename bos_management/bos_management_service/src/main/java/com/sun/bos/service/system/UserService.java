package com.sun.bos.service.system;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sun.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:22:44 <br/>       
 */
public interface UserService {

    void save(User model, Long[] roleIds);

    Page<User> findAll(Pageable pageable);
    
    
    
}
  
