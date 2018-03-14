package com.sun.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月13日 下午12:35:27 <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {
    List<Standard> findByName(String name);
    
    List<Standard> findByNameLike(String name);
    
    List<Standard> findByNameAndMaxWeight(String name,Integer maxWeight);
    
    @Modifying
    @Transactional
    @Query("update Standard set maxWeight=? where name=? ")
    void updateMaxWeightByName(Integer maxWeight,String name);
    
    @Modifying
    @Transactional
    @Query("delete from Standard where name=? ")
    void deleteByName(String name);
}
  
