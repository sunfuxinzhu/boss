package com.sun.bos.dao.test;

import java.io.FileInputStream;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.sun.crm.domain.Customer;

/**  
 * ClassName:POITest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午8:30:45 <br/>       
 */
public class POITest {
    public static void main(String[] args) throws Exception{
       /* HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream("E:\\javaee\\2018.3.9\\BOS前5天资料\\BOS课前资料\\Day04\\资料\\03_区域测试数据\\区域导入测试数据.xls"));
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum==0) {
                continue;
            }
            for (Cell cell : row) {
                System.out.print(cell.getStringCellValue()+"\t");
            }
            System.out.println();
        }
    
        hssfWorkbook.close();*/
        Collection<? extends Customer> collection = WebClient.create("http://localhost:8180/crm/webService/customerService/findAll")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
                for (Customer customer : collection) {
                    System.out.println(customer);
                }
    }
}
  
