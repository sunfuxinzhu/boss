package com.sun.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.sun.bos.dao.system.MenuRepository;
import com.sun.bos.domain.base.Area;
import com.sun.bos.domain.system.Menu;
import com.sun.bos.domain.system.Role;
import com.sun.bos.domain.system.User;
import com.sun.bos.service.system.MenuService;
import com.sun.bos.web.action.CommonAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;


/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:12:38 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends CommonAction<Menu> {

    public MenuAction() {
          
        super(Menu.class);  
    }
    @Autowired
    private MenuService menuService;
    
    @Action("menuAction_findLevelOne")
    public String findLevelOne() throws IOException{
        List<Menu> list = menuService.findLevelOne();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        list2json(list, jsonConfig );
        return NONE;
    }
    
    @Action(value="menuAction_save",results={@Result(name="success",location="/pages/system/menu.html",type="redirect")})
    public String save(){
        
        menuService.save(getModel());
        
        return SUCCESS;
    }
    
    @Action("menuAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage())-1, rows);
        Page<Menu> pageList = menuService.findAll(pageable);
        
        //转化为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        
        page2Json(pageList,jsonConfig);
        
       
        
        return NONE;
    }
    
    
    @Action("menuAction_findbyUser")
    public String findbyUser() throws IOException{
        
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        
        List<Menu> list = menuService.findbyUser(user);
        
      //转化为json
        JsonConfig jsonConfig = new JsonConfig();
        
        
        jsonConfig.registerJsonBeanProcessor(Menu.class,new JsonBeanProcessor() {
            
            @Override
            public JSONObject processBean(Object obj, JsonConfig cfg) {
                Menu menu = (Menu) obj;
                if (menu.getParentMenu()==null) {
                    return new JSONObject().element("pId", 0L)
                                            .element("id", menu.getId())
                                            .element("name", menu.getName())
                                            .element("page", menu.getPage());
                }else{
                    return new JSONObject().element("pId", menu.getParentMenu().getId())
                            .element("id", menu.getId())
                            .element("name", menu.getName())
                            .element("page", menu.getPage());
                    
                }
            }
        });
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        list2json(list, jsonConfig);
        return NONE;
    }
}
  
