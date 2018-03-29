package com.sun.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.system.MenuRepository;
import com.sun.bos.dao.system.PermissionRepository;
import com.sun.bos.dao.system.RoleRepository;
import com.sun.bos.domain.system.Menu;
import com.sun.bos.domain.system.Permission;
import com.sun.bos.domain.system.Role;
import com.sun.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午2:54:32 <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
    
    
    @Override
    public void save(Role role, String menuIds, Long[] permissionIds) {
        roleRepository.save(role);
        //foreach也可以
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String menuid : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(menuid));
                role.getMenus().add(menu);
            }
            
        }
        if (permissionIds!=null&&permissionIds.length>0) {
            for (Long permissionId : permissionIds) {
                Permission permission = new Permission();
                permission.setId(permissionId);
                role.getPermissions().add(permission);
            }
        }
    }


    @Override
    public void save2(Role role, String menuIds, Long[] permissionIds) {
        roleRepository.save(role);
        //foreach也可以
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (int i = 0; i < split.length; i++) {
                Menu menu = menuRepository.findOne(Long.parseLong(split[i]));
                role.getMenus().add(menu);
            }
        }
        if (permissionIds!=null&&permissionIds.length>0) {
            for (int i = 0; i < permissionIds.length; i++) {
                Permission permission = permissionRepository.findOne(permissionIds[i]);
                role.getPermissions().add(permission);
            }
        }
    }

}
  
