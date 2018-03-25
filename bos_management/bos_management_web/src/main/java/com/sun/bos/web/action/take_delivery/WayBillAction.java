package com.sun.bos.web.action.take_delivery;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sun.bos.domain.base.TakeTime;
import com.sun.bos.domain.take_delivary.WayBill;
import com.sun.bos.service.take_delivery.WayBillService;
import com.sun.bos.web.action.CommonAction;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午11:52:44 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WayBillAction extends CommonAction<WayBill> {

    public WayBillAction() {
        super(WayBill.class);  
    }
    
    @Autowired
    private WayBillService wayBillService;
    
    @Action(value="wayBillAction_save")
    public String save() throws IOException{
        String msg = "0";
        try {
            wayBillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();  
            msg="1";
            
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(msg);
        return NONE;
    }

}
  
