package com.sun.bos.fore.web.action;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.bos.domain.base.Area;
import com.sun.bos.domain.take_delivary.Order;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午5:25:53 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
    
    private Order model;
    
    @Override
    public Order getModel() {
        if (model==null) {
            model=new Order();
        }
        return model;
    }
    
    //属性驱动获取详细地址
    private String sendAreaInfo ;
    private String recAreaInfo;
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    //添加订单
    @Action(value = "orderAction_add",results={@Result(name="success",location="/index.html",type="redirect")})
    public String add() throws IOException{
        if (StringUtils.isNotEmpty(sendAreaInfo)) {
            //去掉"/"
            String[] strings = sendAreaInfo.split("/");
            String province = strings[0];
            String city = strings[1];
            String district = strings[2];
            
            //去掉省，市，区
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            
            Area area = new Area();
            
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            
            
            model.setSendArea(area);
            
            
        }
        if (StringUtils.isNotEmpty(recAreaInfo)) {
            //去掉"/"
            String[] strings = recAreaInfo.split("/");
            String province = strings[0];
            String city = strings[1];
            String district = strings[2];
            
            //去掉省，市，区
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            
            Area area = new Area();
            
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            
            
            model.setRecArea(area);
            
            
        }
        
        WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON)
        .post(model);
        
        
        return SUCCESS;
    }
}
  
