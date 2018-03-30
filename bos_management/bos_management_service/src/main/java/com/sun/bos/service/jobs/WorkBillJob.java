package com.sun.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.bos.dao.take_delivery.WorkBillRepository;
import com.sun.bos.domain.take_delivary.WorkBill;
import com.sun.utils.MailUtils;

/**  
 * ClassName:WorkBillJob <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 上午1:02:45 <br/>       
 */
@Component
public class WorkBillJob {
    @Autowired
    private WorkBillRepository workBillRepository;

    public void sendMail() {
        List<WorkBill> list = workBillRepository.findAll();

        String emailBody = "编号\t快递员\t取件状态\t时间<br/>";

        for (WorkBill workBill : list) {
            emailBody += workBill.getId() + "\t"
                    + workBill.getCourier().getName() + "\t"
                    + workBill.getPickstate() + "\t"
                    + workBill.getBuildtime().toLocaleString() + "<br/>";
        }
        MailUtils.sendMail("工单信息统计", emailBody,"1037241234@qq.com");
        
        System.out.println("邮件已经发送");
    }
}
  
