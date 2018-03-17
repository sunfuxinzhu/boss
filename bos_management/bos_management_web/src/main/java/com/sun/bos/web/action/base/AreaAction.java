package com.sun.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.bos.domain.base.Area;
import com.sun.bos.domain.base.Standard;
import com.sun.bos.service.base.AreaService;
import com.sun.bos.web.action.CommonAction;
import com.sun.utils.PinYin4jUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午7:27:47 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {
    
    public AreaAction() {
        super(Area.class);  
    }

    @Autowired
    private AreaService areaService;
    
    
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    
    @Action(value="areaAction_importXLS",results={@Result(name="success",location="pages/base/area.html",type="redirect")})
    public String importXLS(){
        try {
           // System.out.println(file.getAbsolutePath());
            List<Area> list = new ArrayList<>();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            for (Row row : sheet) {
                int rowNum = row.getRowNum();
                if (rowNum==0) {
                    continue;
                }
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                
                province=province.substring(0, province.length()-1);
                city=city.substring(0, city.length()-1);
                district=district.substring(0, district.length()-1);
                String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
                
                String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                
                Area area = new Area();
                area.setCity(city);
                area.setCitycode(citycode);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setProvince(province);
                area.setShortcode(shortcode);
                
                list.add(area);
            }
            
            System.out.println(list);
            areaService.save(list);
            
            hssfWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();  
        }
        
        return SUCCESS;
    }
   
    
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> pageList = areaService.findAll(pageable);
        
        //转化为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        
        page2Json(pageList,jsonConfig);
        
       
        
        return NONE;
    }
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    @Action("areaAction_findAll")
    public String findAll() throws IOException{
        List<Area> list;
       if (StringUtils.isEmpty(q)) {
           Page<Area> page = areaService.findAll(null);
           list = page.getContent();
       }else{
           list =areaService.findByQ(q);
       }
        
       
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[] {"subareas"});
       
       list2json(list, jsonConfig);
       return NONE;
    }
}
  
