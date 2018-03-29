package com.sun.bos.web.action.system;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.sun.bos.dao.system.MenuRepository;
import com.sun.bos.dao.system.PermissionRepository;
import com.sun.bos.dao.system.RoleRepository;
import com.sun.bos.domain.system.Menu;
import com.sun.bos.domain.system.Role;
import com.sun.bos.service.system.RoleService;
import com.sun.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午2:51:10 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

    public RoleAction() {
          
        super(Role.class);  
    }
    
    @Autowired
    private RoleService roleService;
    
   

    
    @Action("roleAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> pageList = roleService.findAll(pageable);
        
        //转化为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"menus","users","permissions"});
        
        page2Json(pageList,jsonConfig);
        
       
        
        return NONE;
    }
    
    private String menuIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    
    private Long[] permissionIds;
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    
    @Action(value="roleAction_save",results={@Result(name="success",location="/pages/system/role.html",type="redirect")})
    public String save(){
        
        
        
        roleService.save(getModel(),menuIds,permissionIds);
        
        return SUCCESS;
    }
}
  
