package com.sun.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.bos.domain.take_delivary.Order;

/**  
 * ClassName:OrderService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午9:21:21 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OrderService {
    
    @POST
    @Path("/saveOrder")
    void saveOrder(Order order);
}
  
