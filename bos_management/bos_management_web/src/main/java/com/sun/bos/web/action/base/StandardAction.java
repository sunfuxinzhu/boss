package com.sun.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.bos.domain.base.Standard;
import com.sun.bos.service.base.StandardService;

import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:21:31 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    
    private Standard model = new Standard();
    @Override
    public Standard getModel() {
        return model;
    }
    @Autowired
    private StandardService standardService;
    
    @Action(value="standardAction_save",results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
    public String save(){
        standardService.save(model);
        return SUCCESS;
    }
    
    private int page;
    public void setPage(int page) {
        this.page = page;
    }
    private int rows;
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Action("standardAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> pageList = standardService.findAll(pageable);
        
        //获取两个属性值
        long total = pageList.getTotalElements();
        List<Standard> list = pageList.getContent();
        
        
        //封装
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        //转化为json
        String json = JSONObject.fromObject(map).toString();
        
        //传回去
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        
        response.getWriter().write(json);
        
        return NONE;
    }
    
}
  
