package com.sun.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.bos.dao.system.PermissionRepository;
import com.sun.bos.dao.system.RoleRepository;
import com.sun.bos.dao.system.UserRepository;
import com.sun.bos.domain.system.Permission;
import com.sun.bos.domain.system.Role;
import com.sun.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午2:42:24 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    
    
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        
        if ("admin".equals(user.getUsername())) {
            //内置超级管理员权限代码写死
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        
        }else {
            //其他用户
            List<Role> roles = roleRepository.findbyUid(user.getId());
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            
            
            List<Permission> permissions = permissionRepository.findbyUid(user.getId());
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            } 
        }
        return info;
    }

    
    @Autowired
    private UserRepository userRepository;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken =
                (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        // 根据用户名查找用户
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // 找到 ->比对密码

            /**
             * @param principal 当事人,主体.通常是从数据库中查询到的用户
             * @param credentials 凭证,密码.是从数据库中查询出来的密码
             * @param realmName
             */
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,
                    user.getPassword(), getName());
            // 比对成功 -> 执行后续的逻辑
            // 比对失败 -> 抛异常
            return info;
        }

        // 找不到 -> 抛异常

        return null;
    }

}
  
