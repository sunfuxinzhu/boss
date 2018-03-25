package com.sun.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.sun.utils.SmsUtils;

/**  
 * ClassName:SmsConsumer <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:03:46 <br/>       
 */
@Component
public class SMSConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String telephone = mapMessage.getString("tel");
            String code = mapMessage.getString("code");
            System.out.println(telephone+"-----"+code);
            try {
                SmsUtils.sendSms(telephone, code);
            } catch (ClientException e) {
                e.printStackTrace();  
            }
        } catch (JMSException e) {
            e.printStackTrace();  
            
        }
    }

}
  
