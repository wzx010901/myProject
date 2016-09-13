package com.fh.controller.${packageName}.${objectNameLower};

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.${packageName}.${objectNameLower}.${objectName}Manager;

/** 
 * 说明：${title}
 * 创建人：ZX Q149156999
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 */
@Controller
@RequestMapping(value="/${objectNameFirstLower}")
public class ${objectNameFirstUpper}Controller extends BaseController {
	
	String menuUrl = "${objectNameLower}/list.do"; //菜单地址(权限用)
	@Resource(name="${objectNameFirstLower}Service")
	private ${objectNameFirstUpper}Manager ${objectNameFirstLower}Service;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("${objectNameFirstLower}Id", this.get32UUID());	//主键
<#list fieldList as var>
	<#if var[3] == "否">
		<#if var[1] == 'Date'>
		pd.put("${var[7]}", Tools.date2Str(new Date()));	//${var[2]}
		<#elseif var[1] == 'Integer'>
		pd.put("${var[7]}", "${var[4]?replace("无",0)}");	//${var[2]}
		<#elseif var[1] == 'Double'>
		pd.put("${var[7]}", "${var[4]?replace("无",0)}");	//${var[2]}
		<#else>
		pd.put("${var[7]}", "${var[4]?replace("无","")}");	//${var[2]}
		</#if>
	</#if>
</#list>
		${objectNameFirstLower}Service.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String ${objectNameFirstLower}Id) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} 					//校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("${objectNameFirstLower}Id", ${objectNameFirstLower}Id);
		String errInfo = "success";
		if(${objectNameFirstLower}Service.listByParentId(${objectNameFirstLower}Id).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			${objectNameFirstLower}Service.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改${objectName}");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		${objectNameFirstLower}Service.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表${objectName}");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "find")){return null;} 	//校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");								//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String ${objectNameFirstLower}Id = null == pd.get("${objectNameFirstLower}Id")?"":pd.get("${objectNameFirstLower}Id").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			${objectNameFirstLower}Id = pd.get("id").toString();
		}
		pd.put("${objectNameFirstLower}Id", ${objectNameFirstLower}Id);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = ${objectNameFirstLower}Service.list(page);			//列出${objectName}列表
		mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_list");
		mv.addObject("pd", ${objectNameFirstLower}Service.findById(pd));				//传入上级所有信息
		mv.addObject("${objectNameFirstLower}Id", ${objectNameFirstLower}Id);			//上级ID
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());								//按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String ${objectNameFirstLower}Id)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//JSONArray arr = JSONArray.fromObject(${objectNameFirstLower}Service.listTree("0"));
			String treeListString = JSONArray.toJSONString(${objectNameFirstLower}Service.listTree("0"));
			JSONArray arr = JSONArray.parseArray(treeListString);
			String json = arr.toString();
			json = json.replaceAll("${objectNameFirstLower}Id", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("sub${objectName}", "nodes").replaceAll("has${objectName}", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("${objectNameFirstLower}Id",${objectNameFirstLower}Id);
			mv.addObject("pd", pd);	
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ${objectNameFirstLower}Id = null == pd.get("${objectNameFirstLower}Id")?"":pd.get("${objectNameFirstLower}Id").toString();
		pd.put("${objectNameFirstLower}Id", ${objectNameFirstLower}Id);					//上级ID
		mv.addObject("pds",${objectNameFirstLower}Service.findById(pd));				//传入上级所有信息
		mv.addObject("${objectNameFirstLower}Id", ${objectNameFirstLower}Id);			//传入ID，作为子级ID用
		mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_edit");
		mv.addObject("msg", "save");
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ${objectNameFirstLower}Id = pd.getString("${objectNameFirstLower}Id");
		pd = ${objectNameFirstLower}Service.findById(pd);							//根据ID读取		
		mv.addObject("pd", pd);													//放入视图容器
		pd.put("${objectNameFirstLower}Id",pd.get("parentId").toString());			//用作上级信息
		mv.addObject("pds",${objectNameFirstLower}Service.findById(pd));				//传入上级所有信息
		mv.addObject("${objectNameFirstLower}Id", pd.get("parentId").toString());	//传入上级ID，作为子ID用
		pd.put("${objectNameFirstLower}Id",${objectNameFirstLower}Id);					//复原本ID
		pd = ${objectNameFirstLower}Service.findById(pd);							//根据ID读取
		mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出${objectName}到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "find")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
<#list fieldList as var>
		titles.add("${var[2]}");	//${var_index+1}
</#list>
		dataMap.put("titles", titles);
		List<PageData> varOList = ${objectNameFirstLower}Service.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
<#list fieldList as var>
		<#if var[1] == 'Integer'>
			vpd.put("var${var_index+1}", varOList.get(i).get("${var[7]}").toString());	//${var_index+1}
		<#elseif var[1] == 'Double'>
			vpd.put("var${var_index+1}", varOList.get(i).get("${var[7]}").toString());	//${var_index+1}
		<#else>
			vpd.put("var${var_index+1}", varOList.get(i).getString("${var[7]}"));	    //${var_index+1}
		</#if>
</#list>
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
