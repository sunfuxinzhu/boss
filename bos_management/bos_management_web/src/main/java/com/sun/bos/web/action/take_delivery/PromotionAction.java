package com.sun.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.sun.bos.domain.base.Standard;
import com.sun.bos.domain.take_delivary.Promotion;
import com.sun.bos.service.take_delivery.PromotionService;
import com.sun.bos.web.action.CommonAction;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午9:08:22 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends CommonAction<Promotion> {

    public PromotionAction() {
          
        super(Promotion.class);  
    }
    
    @Autowired
    private PromotionService promotionService;
    
 // 使用属性驱动获取封面图片和图片的文件名
    private File titleImgFile;
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }
    
    @Action(value="promotionAction_save",results={@Result(name="success",location="/pages/take_delivery/promotion.html",type="redirect")})
    public String save(){
        
        Promotion promotion = getModel();
        promotion.setStatus("1");
        
        
      try {
        //获取文件的后缀
            String sub = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
            //用uuid组成新的文件名
            String newFileName = UUID.randomUUID().toString().replaceAll("-", "")+sub;
            
            //存储的文件夹
            String dirpath = "/upload";
            
            //获得新文件的绝对路径
            ServletContext servletContext = ServletActionContext.getServletContext();
            String realPath = servletContext.getRealPath(dirpath);
            
            
            //创建接收的文件(路径和名字)
            File destFile = new File(realPath+"/"+newFileName);
            //复制文件
            FileUtils.copyFile(titleImgFile, destFile);
            promotion.setTitleImg("/upload/"+newFileName);
        } catch (IOException e) {
            e.printStackTrace();  
            promotion.setTitleImg(null);
        }
        
        
        
        promotionService.save(promotion);
        
        return SUCCESS;
    }
    
    @Action("promotionAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Promotion> pageList = promotionService.findAll(pageable);
        
        page2Json(pageList, null);
        
        return NONE;
    }

}
  
