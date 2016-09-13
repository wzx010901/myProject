package com.fh.controller.system.dictionaries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.system.dictionaries.DictionariesManager;

/** 
 * 说明：数据字典
 * 创建人：WZX Q149156999
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/dictionaries")
public class DictionariesController extends BaseController {
	
	String menuUrl = "dictionaries/list.do"; //菜单地址(权限用)
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Dictionaries");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("dictionariesId", this.get32UUID());	//主键
		dictionariesService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * @param dictionariesId
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String dictionariesId) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除Dictionaries");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("dictionariesId", dictionariesId);
		String errInfo = "success";
		if(dictionariesService.listSubDictByParentId(dictionariesId).size() > 0){//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			pd = dictionariesService.findById(pd);//根据ID读取
			if(null != pd.get("tbsName") && !"".equals(pd.getString("tbsName"))){
				String[] table = pd.getString("tbsName").split(",");
				for(int i=0;i<table.length;i++){
					pd.put("thisTable", table[i]);
					try {
						if(Integer.parseInt(dictionariesService.findFromTbs(pd).get("zs").toString())>0){//判断是否被占用，是：不允许删除(去排查表检查字典表中的编码字段)
							errInfo = "false";
							break;
						}
					} catch (Exception e) {
							errInfo = "false2";
							break;
					}
				}
			}
		}
		if("success".equals(errInfo)){
			dictionariesService.delete(pd);	//执行删除
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
		logBefore(logger, Jurisdiction.getUsername()+"修改Dictionaries");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		dictionariesService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Dictionaries");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String dictionariesId = null == pd.get("dictionariesId")?"":pd.get("dictionariesId").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			dictionariesId = pd.get("id").toString();
		}
		pd.put("dictionariesId", dictionariesId);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = dictionariesService.list(page);	//列出Dictionaries列表
		mv.addObject("pd", dictionariesService.findById(pd));		//传入上级所有信息
		mv.addObject("dictionariesId", dictionariesId);			//上级ID
		mv.setViewName("system/dictionaries/dictionaries_list");
		mv.addObject("varList", varList);
		mv.addObject("jurisdiction",Jurisdiction.getHC());					//按钮权限
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDict")
	public ModelAndView listAllDict(Model model,String dictionariesId)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
//			JSONArray arr = JSONArray.fromObject(dictionariesService.listAllDict("0"));
			String allDictListString = JSONArray.toJSONString(dictionariesService.listAllDict("0"));
			JSONArray arr = JSONArray.parseArray(allDictListString);
			String json = arr.toString();
			json = json.replaceAll("dictionariesId", "id").replaceAll("parentId", "pId").replaceAll("name", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeUrl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("dictionariesId",dictionariesId);
			mv.addObject("pd", pd);	
			mv.setViewName("system/dictionaries/dictionaries_ztree");
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
		String dictionariesId = null == pd.get("dictionariesId")?"":pd.get("dictionariesId").toString();
		pd.put("dictionariesId", dictionariesId);					//上级ID
		mv.addObject("pds",dictionariesService.findById(pd));		//传入上级所有信息
		mv.addObject("dictionariesId", dictionariesId);			//传入ID，作为子级ID用
		mv.setViewName("system/dictionaries/dictionaries_edit");
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
		String dictionariesId = pd.getString("dictionariesId");
		pd = dictionariesService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("dictionariesId",pd.get("parentId").toString());			//用作上级信息
		mv.addObject("pds",dictionariesService.findById(pd));				//传入上级所有信息
		mv.addObject("dictionariesId", pd.get("parentId").toString());	//传入上级ID，作为子ID用
		pd.put("dictionariesId",dictionariesId);							//复原本ID
		mv.setViewName("system/dictionaries/dictionaries_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasEncoding")
	@ResponseBody
	public Object hasEncoding(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(dictionariesService.findByEncoding(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
