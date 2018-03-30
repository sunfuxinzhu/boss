package com.sun.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.system.RoleRepository;
import com.sun.bos.dao.system.UserRepository;
import com.sun.bos.domain.system.Role;
import com.sun.bos.domain.system.User;
import com.sun.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:23:04 <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(User user, Long[] roleIds) {
        userRepository.save(user);
        if (roleIds!=null&&roleIds.length>0) {
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                user.getRoles().add(role);
            }
        }
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
  
