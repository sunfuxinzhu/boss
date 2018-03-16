package com.sun.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.bos.domain.base.Courier;
import com.sun.bos.domain.base.Standard;
import com.sun.bos.service.base.CourierService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午4:03:02 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    private Courier model = new Courier();
    
    @Override
    public Courier getModel() {
        return model;
    }
    
    @Autowired
    private CourierService courierService;
    
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        courierService.save(model);
        return SUCCESS;
    }
    //属性驱动获取参数
    private int page;
    public void setPage(int page) {
        this.page = page;
    }
    private int rows;
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    
    @Action(value="courierAction_pageQuery")
    public String findAll() throws IOException{
        Specification<Courier> specification = new Specification<Courier>() {
            
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String company = model.getCompany();
                String courierNum = model.getCourierNum();
                Standard standard = model.getStandard();
                String type = model.getType();
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(company)) {
                    //不为空进行条件添加
                    Predicate p1 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p1);
                }
                if (StringUtils.isNotEmpty(courierNum)) {
                    //不为空进行条件添加
                    Predicate p2 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p2);
                }
                if (StringUtils.isNotEmpty(type)) {
                    //不为空进行条件添加
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                //对象要先判断
                if (standard!=null) {
                    String name = standard.getName();
                    if (StringUtils.isNotEmpty(name)) {
                        //因为是对象,连表查询
                        Join<Object, Object> join = root.join("standard");
                        //不为空进行条件添加
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                    }
                }
                
                if (list.size()==0) {
                    return null;
                }
                Predicate[] predicates = new Predicate[list.size()];
                list.toArray(predicates);
                Predicate predicate = cb.and(predicates);
                
                
                
                return predicate;
            }};
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page = courierService.findAll(specification,pageable);
        
        //获得数据
        long total = page.getTotalElements();
        List<Courier> list = page.getContent();
        
        //封装map
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        //选择不用的字段
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas","takeTime"});
        
        
        //转为json并传回页面
        String json = JSONObject.fromObject(map,jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        
        return NONE;
    }
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    @Action(value="courierAction_batchDel",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchDel(){
        
        courierService.batchDel(ids);
        return SUCCESS;
    }
}
  
