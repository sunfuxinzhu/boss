package com.sun.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.bos.domain.base.Area;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午9:44:50 <br/>       
 * @param <T>
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    
    private T model;
    private Class<T> clazz;
    public CommonAction(Class<T> clazz) {
        this.clazz=clazz;
    }

    @Override
    public T getModel() {
        try {
            model=clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }
        return model;
    }
    protected int page;
    public void setPage(int page) {
        this.page = page;
    }
    protected int rows;
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void page2Json(Page<T> pageList,JsonConfig jsonConfig) throws IOException{
        //获取两个属性值
        long total = pageList.getTotalElements();
        List<T> list = pageList.getContent();
        
        
        //封装
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
       
        String json;
        if (jsonConfig!=null) {
            json = JSONObject.fromObject(map,jsonConfig).toString();
        }else{
            json = JSONObject.fromObject(map).toString();
        }
        //传回去
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        
        response.getWriter().write(json);
        
    }
    public void list2json(List<T> list,JsonConfig jsonConfig) throws IOException{
        String json;
        if (jsonConfig!=null) {
            json =JSONArray.fromObject(list,jsonConfig).toString();
        }else{
            json =JSONArray.fromObject(list).toString();
        }
        //传回去
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        
        response.getWriter().write(json);
    }
}
  
