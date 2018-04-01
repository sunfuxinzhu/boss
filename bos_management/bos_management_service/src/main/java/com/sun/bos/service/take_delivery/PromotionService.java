package com.sun.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.take_delivery.PromotionRepository;
import com.sun.bos.domain.take_delivary.PageBean;
import com.sun.bos.domain.take_delivary.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午9:07:35 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PromotionService {

    void save(Promotion model);
    @GET
    @Path("/findAll4Fore")
    PageBean<Promotion> findAll4Fore(@QueryParam("pageIndex") int page,
            @QueryParam("pageSize") int pageSize);
    
    
    
    Page<Promotion> findAll(Pageable pageable);
   
}
  
