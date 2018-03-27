package com.sun.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午2:49:39 <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
  
