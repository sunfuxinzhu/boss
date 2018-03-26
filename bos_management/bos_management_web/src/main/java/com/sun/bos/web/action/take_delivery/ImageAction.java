package com.sun.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午4:22:23 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport {
    //属性驱动获取原文件
    private File imgFile;
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    //属性驱动获取文件名字
    private String imgFileFileName;
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    
    @Action(value="imageAction_upload")
    public String upload() throws IOException{
        Map<String , Object> map = new HashMap<>();
        try {
            //获取文件的后缀
            String sub = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
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
            FileUtils.copyFile(imgFile, destFile);
            
            map.put("error", 0);
            map.put("url", servletContext.getContextPath()+dirpath+"/"+newFileName);
        } catch (Exception e) {
            e.printStackTrace();  
            map.put("error", 1);
            map.put("message", e.getMessage());
        }
        String json = JSONObject.fromObject(map).toString();
        
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
}
  
