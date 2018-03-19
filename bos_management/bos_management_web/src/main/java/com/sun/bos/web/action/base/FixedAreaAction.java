package com.sun.bos.web.action.base;

import java.io.IOException;

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

import com.sun.bos.domain.base.FixedArea;
import com.sun.bos.domain.base.Standard;
import com.sun.bos.service.base.FixedAreaService;
import com.sun.bos.web.action.CommonAction;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:22:55 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    public FixedAreaAction() {
        super(FixedArea.class);  
    }
    @Autowired
    private FixedAreaService fixedAreaService;
    
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save(){
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<FixedArea> pageList = fixedAreaService.findAll(pageable);
        
        page2Json(pageList, null);
        
        return NONE;
    }
}
  
