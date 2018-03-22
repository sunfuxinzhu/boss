package com.sun.crm.service;

import java.util.List;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午2:42:31 <br/>       
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerService {
    
    @GET
    @Path("/findAll")
    List<Customer> findAll();

    // 查询未关联定区的客户
    @GET
    @Path("/findCustomersUnAssociated")
    List<Customer> findCustomersUnAssociated();

    // 查询已关联到指定定区的客户
    @GET
    @Path("/findCustomersAssociated2FixedArea")
    List<Customer> findCustomersAssociated2FixedArea(
            @QueryParam("fixedAreaId") String fixedAreaId);

    // 定区ID,要关联的数据
    // 根据定区ID,把关联到这个定区的所有客户全部解绑
    // 要关联的数据和定区Id进行绑定
    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(
            @QueryParam("customerIds") Long[] customerIds,
            @QueryParam("fixedAreaId") String fixedAreaId);

    
    //保存
    @POST
    @Path("/save")
    void save(Customer customer);
    
    //激活
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone") String telephone);
    
 // 检查用户是否激活
    @GET
    @Path("/isActived")
    Customer isActived(@QueryParam("telephone") String telephone);

    // 登录
    @GET
    @Path("/login")
    Customer login(@QueryParam("telephone") String telephone,@QueryParam("password") String password);
    
}

  
