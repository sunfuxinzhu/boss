package com.sun.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
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
import com.sun.utils.FileDownloadUtils;
import com.sun.utils.PinYin4jUtils;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
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
    
    // 加载文件
    // 读取sheet
    // 读取行
    // 读取列

    // 创建文件
    // 创建sheet
    // 创建行
    // 创建列
    // 写入数据
    @Action(value = "areaAction_exportExcel")
    public String exportExcel() throws IOException {
        Page<Area> page = areaService.findAll(null);
        List<Area> list = page.getContent();

        // 在内存中创建了一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = workbook.createSheet();
        // 创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");

        // 遍历数据,创建数据行
        for (Area area : list) {
            // 获取最后一行的行号
            int lastRowNum = sheet.getLastRowNum();

            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
        }
        // 文件名
        String filename = "区域数据统计.xls";

        // 一个流两个头
        HttpServletResponse response = ServletActionContext.getResponse();
        ServletContext servletContext = ServletActionContext.getServletContext();
        ServletOutputStream outputStream = response.getOutputStream();
        HttpServletRequest request = ServletActionContext.getRequest();

        // 获取mimeType
        // 先获取mimeType再重新编码,避免编码后后缀名丢失,导致获取失败
        String mimeType = servletContext.getMimeType(filename);
        // 获取浏览器的类型
        String userAgent = request.getHeader("User-Agent");
        // 对文件名重新编码
        filename =FileDownloadUtils.encodeDownloadFilename(filename, userAgent);

        // 设置信息头
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition",
                "attachment; filename=" + filename);

        // 写出文件
        workbook.write(outputStream);
        workbook.close();
        return NONE;
    }
    
    
    @Action(value = "areaAction_exportCharts")
    public String exportCharts() throws IOException {
        List<Object[]> list = areaService.exportCharts();
        
        
        list2json(list, null);
        
        
        return NONE;
    }
    
    @Autowired
    private DataSource dataSource;
    @Action(value = "areaAction_exportPDF")
    public String exportPDF() throws Exception {
        // 读取 jrxml 文件
        String jrxml = ServletActionContext.getServletContext().getRealPath("/jasper/area.jrxml");
        // 准备需要数据
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company", "传智播客");
        // 准备需要数据
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());

        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream ouputStream = response.getOutputStream();
        // 设置相应参数，以附件形式保存PDF
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + FileDownloadUtils.encodeDownloadFilename("工作单.pdf",
                ServletActionContext.getRequest().getHeader("user-agent")));
        // 使用JRPdfExproter导出器导出pdf
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        exporter.exportReport();// 导出
        ouputStream.close();// 关闭流
        return NONE;
    }
}
  
