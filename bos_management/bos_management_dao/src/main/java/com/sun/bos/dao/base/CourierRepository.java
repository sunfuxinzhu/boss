package com.sun.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sun.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午4:13:40 <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier, Long>
            ,JpaSpecificationExecutor<Courier>{
    @Modifying
    @Query("update Courier set deltag=1 where id=?")
    void updateDelTagById(Long id);
}
  
