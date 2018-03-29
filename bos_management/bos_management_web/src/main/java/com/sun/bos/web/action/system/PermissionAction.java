package com.sun.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.sun.bos.domain.system.Menu;
import com.sun.bos.domain.system.Permission;
import com.sun.bos.service.system.MenuService;
import com.sun.bos.service.system.PermissionService;
import com.sun.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:permissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 上午12:44:42 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission> {

    public PermissionAction() {
          
        super(Permission.class);  
    }
    
    @Autowired
    private PermissionService permissionService;
    
    @Action("permissionAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Permission> pageList = permissionService.findAll(pageable);
        
        //转化为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        
        page2Json(pageList,jsonConfig);
        
       
        
        return NONE;
    }
    
    @Action(value="permissionAction_save",results={@Result(name="success",location="/pages/system/permission.html",type="redirect")})
    public String save(){
        
        permissionService.save(getModel());
        
        return SUCCESS;
    }
    
    
    @Action("permissionAction_findAll")
    public String findAll() throws IOException{
        Page<Permission> page = permissionService.findAll(null);
        List<Permission> list = page.getContent();
        
      //转化为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        
        list2json(list, jsonConfig);
        
        return NONE;
    }

}
  
