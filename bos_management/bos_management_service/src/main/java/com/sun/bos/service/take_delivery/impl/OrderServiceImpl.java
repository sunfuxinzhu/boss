package com.sun.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.bos.dao.base.AreaRepository;
import com.sun.bos.dao.base.FixedAreaRepository;
import com.sun.bos.dao.take_delivery.OrderRepository;
import com.sun.bos.dao.take_delivery.WorkBillRepoditory;
import com.sun.bos.domain.base.Area;
import com.sun.bos.domain.base.Courier;
import com.sun.bos.domain.base.FixedArea;
import com.sun.bos.domain.base.SubArea;
import com.sun.bos.domain.take_delivary.Order;
import com.sun.bos.domain.take_delivary.WorkBill;
import com.sun.bos.service.take_delivery.OrderService;
import com.sun.crm.domain.Customer;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午9:48:18 <br/>       
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Autowired
    private WorkBillRepoditory workBillRepoditory;
    
    @Override
    public void saveOrder(Order order) {
        //area要持久态的才可以
        Area sendArea = order.getSendArea();
        if (sendArea!=null) {
            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
            order.setSendArea(sendAreaDB);
        }
        
        Area recArea = order.getRecArea();
        if (recArea!=null) {
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
            order.setRecArea(recAreaDB);
        }
        order.setOrderTime(new Date());
        order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
        //保存订单
        orderRepository.save(order);
        
        //分单，先根据详细地址(crm)-->定区-->快递员
        if (StringUtils.isNotEmpty(order.getSendAddress())) {
            String fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findfixedAreaIdByAddress")
            .query("address", order.getSendAddress())
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .get(String.class);
            
            if (StringUtils.isNotEmpty(fixedAreaId)) {
                FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
                if (fixedArea!=null) {
                    Set<Courier> couriers = fixedArea.getCouriers();
                    if (!couriers.isEmpty()) {
                        Iterator<Courier> iterator = couriers.iterator();
                        Courier courier = iterator.next();
                        order.setCourier(courier);
                     // 生成工单
                        WorkBill workBill = new WorkBill();
                        workBill.setAttachbilltimes(0);
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setPickstate("新单");
                        workBill.setRemark(order.getRemark());
                        workBill.setSmsNumber("111");
                        workBill.setType("新");
                        
                        workBillRepoditory.save(workBill);
                     // 发送短信,推送一个通知
                        // 中断代码的执行
                        order.setOrderType("自动分单");
                        return;
                    }
                }
            }else {
                //根据区域--->分区/关键字--->定区---->快递员
                Area sendArea2 = order.getSendArea();
                if (sendArea2!=null) {
                    Set<SubArea> subareas = sendArea2.getSubareas();
                    if (subareas!=null) {
                        for (SubArea subArea : subareas) {
                            String keyWords = subArea.getKeyWords();
                            String assistKeyWords = subArea.getAssistKeyWords();
                            if (order.getSendAddress().contains(keyWords)||order.getSendAddress().contains(assistKeyWords)) {
                                FixedArea fixedArea2 = subArea.getFixedArea();
                                if (fixedArea2!=null) {
                                    Set<Courier> couriers = fixedArea2.getCouriers();
                                    if (!couriers.isEmpty()) {
                                        Iterator<Courier> iterator = couriers.iterator();
                                        Courier courier = iterator.next();
                                        order.setCourier(courier);
                                     // 生成工单
                                        WorkBill workBill = new WorkBill();
                                        workBill.setAttachbilltimes(0);
                                        workBill.setBuildtime(new Date());
                                        workBill.setCourier(courier);
                                        workBill.setOrder(order);
                                        workBill.setPickstate("新单");
                                        workBill.setRemark(order.getRemark());
                                        workBill.setSmsNumber("111");
                                        workBill.setType("新");
                                        
                                        workBillRepoditory.save(workBill);
                                     // 发送短信,推送一个通知
                                        // 中断代码的执行
                                        order.setOrderType("自动分单");
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        
        
        
        
        order.setOrderType("人工分单");
        
    }

}
  
