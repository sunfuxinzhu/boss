package com.sun.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.sun.bos.domain.base.Courier;
import com.sun.bos.domain.base.Standard;
import com.sun.bos.domain.base.TakeTime;
import com.sun.bos.service.base.TakeTimeService;
import com.sun.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午5:20:26 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

    public TakeTimeAction() {
        super(TakeTime.class);  
    }
    @Autowired
    private TakeTimeService takeTimeService;
    
    @Action(value="takeTimeAction_listajax")
    public String listajax() throws IOException{
        //查找所有时间
        List<TakeTime> list = takeTimeService.findAll();
        
        list2json(list, null);
        return NONE;
    }
}
  
