package com.sun.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sun.bos.domain.take_delivary.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午9:17:42 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>  {

}
  
