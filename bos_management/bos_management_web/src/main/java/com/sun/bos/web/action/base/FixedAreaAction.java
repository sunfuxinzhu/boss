package com.sun.bos.web.action.base;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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
import com.sun.crm.domain.Customer;

import net.sf.json.JsonConfig;

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
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"couriers","subareas"});
        
        page2Json(pageList, jsonConfig);
        
        return NONE;
    }
    
    @Action("fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException{
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }
    
    @Action("fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException{
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersAssociated2FixedArea")
                .query("fixedAreaId", getModel().getId())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }
    // 使用属性驱动获取要关联到指定定区的客户ID
    private Long[] customerIds;

    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignCustomers2FixedArea() throws IOException{
        WebClient.create(
                "http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
                .query("fixedAreaId", getModel().getId())
                .query("customerIds",customerIds)
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .put(null);
        return SUCCESS;
    }
    
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    
    //关联快递员
    @Action(value = "fixedAreaAction_associationCourierToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String associationCourierToFixedArea() throws IOException{
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        
        
        return SUCCESS;
    }
    
 // 使用属性驱动获取分区的Id
    private Long[] subAreaIds;

    public void setSubAreaIds(Long[] subAreaIds) {
        this.subAreaIds = subAreaIds;
    }

    // 关联分区
    @Action(value = "fixedAreaAction_assignSubAreas2FixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String assignSubAreas2FixedArea() throws IOException {
        fixedAreaService.assignSubAreas2FixedArea(getModel().getId(),
                subAreaIds);

        return SUCCESS;
    }
    
    
    

}
  
