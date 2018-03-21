package com.sun.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sun.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午2:41:19 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);
    // 把关联到指定定区的客户进行解绑操作
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    void unbindCustomerByFixedArea(String fixedAreaId);

    // 把客户绑定到指定的定区
    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void bindCustomer2FixedArea(Long customerId, String fixedAreaId);
}
  