package com.fh.controller.system.createcode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.AppUtil;
import com.fh.util.DbFH;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/** 
 * 类名称： 反向生成
 * 创建人：WZX Q149156999
 * 修改时间：2016年4月15日
 * @version
 */
@Controller
@RequestMapping(value="/recreateCode")
public class ReverseCreateCodeController extends BaseController {
	
	String menuUrl = "recreateCode/list.do"; //菜单地址(权限用)
	
	/**列表
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "find")){} 	//校验权限
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/createcode/recreatecode_list");
		mv.addObject("jurisdiction",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**列出所有表
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listAllTable")
	@ResponseBody
	public Object listAllTable(){
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "find")){return null;} //校验权限
		PageData pd = new PageData();		
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		List<String> tblist = new ArrayList<String>();
		try {
			Object[] arrOb = DbFH.getTables(pd);
			tblist = (List<String>)arrOb[1];
			pd.put("msg", "ok");
		} catch (ClassNotFoundException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		} catch (SQLException e) {
			pd.put("msg", "no");
			e.printStackTrace();
		}
		pdList.add(pd);
		map.put("tblist", tblist);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去代码生成器页面(进入弹窗)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goProductCode")
	public ModelAndView goProductCode() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String fieldType = ""; 
		StringBuffer sb = new StringBuffer("");
		List<Map<String,String>> columnList = DbFH.getFieldParameterLsit(DbFH.getFHCon(pd),pd.getString("table")); //读取字段信息
		Map<String, Object> commentMap = DbFH.getTableComment(DbFH.getFHCon(pd),pd.getString("table")); //读取表的备注字段
		for(int i=0;i<columnList.size();i++){
			Map<String,String> fmap = columnList.get(i);
			String fieldNanme = fmap.get("fieldNanme").toString();
			sb.append(fieldNanme);					//字段名称.toUpperCase()
			sb.append(",fh,");
			fieldType = fmap.get("fieldType").toString().toLowerCase();					//字段类型
			if(fieldType.contains("int")){
				sb.append("Integer");
			}else if(fieldType.contains("number")){
				if(Integer.parseInt(fmap.get("fieldSccle")) > 0){
					sb.append("Double");
				}else{
					sb.append("Integer");
				}
			}else if(fieldType.contains("double") || fieldType.contains("numeric")){
				sb.append("Double");
			}else if(fieldType.contains("date")){
				sb.append("Date");
			}else{
				sb.append("String");
			}
			sb.append(",fh,");
//			sb.append("备注"+(i+1));														//备注
			sb.append(commentMap.get(fieldNanme));
			sb.append(",fh,");
			sb.append("是");																//是否前台录入
			sb.append(",fh,");
			sb.append("无");																//默认值
			sb.append(",fh,");
			sb.append(fmap.get("fieldLength").toString());								//长度
			sb.append(",fh,");
			sb.append(fmap.get("fieldSccle").toString());								//小数点右边的位数
			sb.append(",fh,");
//			String lower = StringUtil.replaceUnderlineAndfirstToUpper(fieldNanme, "_", "");
//			sb.append(lower);								//小数点右边的位数
//			sb.append(",fh,");
//			sb.append(StringUtil.firstCharacterToUpper(lower));								//小数点右边的位数
			sb.append("Q149156999");
		}
		pd.put("fieldList", sb.toString());
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.setViewName("system/createcode/productCode");
		return mv;
	}
	
	
	
}
// ZX149156999