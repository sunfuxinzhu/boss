package com.sun.bos.fore.web.action;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.crm.domain.Customer;
import com.sun.utils.SmsUtils;

/**  
 * ClassName:Action <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午12:09:06 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    
    private Customer model;
    
    @Override
    public Customer getModel() {
        if (model==null) {
            model= new Customer();
        }
        return model;
    }
    //发送验证码
    @Action("customerAction_sendSMS")
    public String sendSMS() throws Exception{
        //随机生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        
        System.out.println(code);
        //存起来
        ServletActionContext.getRequest().getSession().setAttribute("code", code);
        //发送验证码
        SmsUtils.sendSms(getModel().getTelephone(), code);
        return NONE;
    }
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    //注册用户
    @Action(value = "customerAction_regist",results={@Result(name="success",location="/signup-success.html",type="redirect")
                                                     ,@Result(name="error",location="/signup-fail.html",type="redirect")})
    public String regist() throws IOException{
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("code");
        if (StringUtils.isNotEmpty(checkcode)&&StringUtils.isNotEmpty(code)&&code.equals(checkcode)) {
            //注册
            WebClient.create("http://localhost:8180/crm/webService/customerService/save")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .post(model);
            
            return SUCCESS;
        }
        return ERROR;
    }
}
  
