package com.fh.controller.system.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
/** 
 * 类名称：MenuController 菜单处理
 * 创建人：FH 
 * 创建时间：2015年10月27日
 * @version
 */
@Controller
@RequestMapping(value="/menu")
public class MenuController extends BaseController {

	String menuUrl = "menu.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuManager menuService;
	
	/**
	 * 显示菜单列表
	 * @param model
	 * @return
	 */
	@RequestMapping
	public ModelAndView list()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			String menuId = (null == pd.get("menuId") || "".equals(pd.get("menuId").toString()))?"0":pd.get("menuId").toString();
			List<Menu> menuList = menuService.listSubMenuByParentId(menuId);
			mv.addObject("pd", menuService.getMenuById(pd));	//传入父菜单所有信息
			mv.addObject("menuId", menuId);
			mv.addObject("msg", null == pd.get("msg")?"list":pd.get("msg").toString()); //MSG=change 则为编辑或删除后跳转过来的
			mv.addObject("menuList", menuList);
			mv.addObject("jurisdiction",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/menu/menu_list");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = new PageData();
			pd = this.getPageData();
			String menuId = (null == pd.get("menuId") || "".equals(pd.get("menuId").toString()))?"0":pd.get("menuId").toString();//接收传过来的上级菜单ID,如果上级为顶级就取值“0”
			pd.put("menuId",menuId);
			mv.addObject("pds", menuService.getMenuById(pd));	//传入父菜单所有信息
			mv.addObject("menuId", menuId);					//传入菜单ID，作为子菜单的父菜单ID用
			mv.addObject("msg", "add");							//执行状态 add 为添加
			mv.setViewName("system/menu/menu_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}	
	
	/**
	 * 保存菜单信息
	 * @param menu
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public ModelAndView add(Menu menu)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"保存菜单");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			menu.setMenuId(String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString())+1));
			menu.setMenuIcon("menu-icon fa fa-leaf black");//默认菜单图标
			menuService.saveMenu(menu); //保存菜单
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("redirect:?msg='change'&menuId="+menu.getParentId()); //保存成功跳转到列表页面
		return mv;
	}
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @param out
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String menuId)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除菜单");
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "";
		try{
			if(menuService.listSubMenuByParentId(menuId).size() > 0){//判断是否有子菜单，是：不允许删除
				errInfo = "false";
			}else{
				menuService.deleteMenuById(menuId);
				errInfo = "success";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 请求编辑菜单页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(String id)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("menuId",id);				//接收过来的要修改的ID
			pd = menuService.getMenuById(pd);	//读取此ID的菜单数据
			mv.addObject("pd", pd);				//放入视图容器
			pd.put("menuId",pd.get("parentId").toString());			//用作读取父菜单信息
			mv.addObject("pds", menuService.getMenuById(pd));			//传入父菜单所有信息
			mv.addObject("menuId", pd.get("parentId").toString());	//传入父菜单ID，作为子菜单的父菜单ID用
			mv.addObject("msg", "edit");
			pd.put("menuId",id);			//复原本菜单ID
			mv.addObject("jurisdiction",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/menu/menu_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 保存编辑
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(Menu menu)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单");
		ModelAndView mv = this.getModelAndView();
		try{
			menuService.edit(menu);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("redirect:?msg='change'&menuId="+menu.getParentId()); //保存成功跳转到列表页面
		return mv;
	}
	
	/**
	 * 请求编辑菜单图标页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toEditicon")
	public ModelAndView toEditicon(String menuId)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("menuId",menuId);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/menu_icon");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 保存菜单图标 
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/editicon")
	public ModelAndView editicon()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单图标");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd = menuService.editicon(pd);
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 显示菜单列表ztree(菜单管理)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllMenu")
	public ModelAndView listAllMenu(Model model,String menuId)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			JSONArray arr = JSONArray.fromObject(menuService.listAllMenu("0"));
			String json = arr.toString();
			json = json.replaceAll("menuId", "id").replaceAll("parentId", "pId").replaceAll("menuName", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked").replaceAll("menuUrl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("menuId",menuId);
			mv.setViewName("system/menu/menu_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 显示菜单列表ztree(拓展左侧四级菜单)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/otherlistMenu")
	public ModelAndView otherlistMenu(Model model,String menuId)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = new PageData();
			pd.put("menuId", menuId);
			String menuUrl = menuService.getMenuById(pd).getString("menuUrl");
			if("#".equals(menuUrl.trim()) || "".equals(menuUrl.trim()) || null == menuUrl){
				menuUrl = "login_default.do";
			}
			String roleRights = Jurisdiction.getSession().getAttribute(Jurisdiction.getUsername() + Const.SESSION_ROLE_RIGHTS).toString();	//获取本角色菜单权限
			List<Menu> athmenuList = menuService.listAllMenuJurisdiction(menuId);					//获取某菜单下所有子菜单
			athmenuList = this.readMenu(athmenuList, roleRights);							//根据权限分配菜单
			JSONArray arr = JSONArray.fromObject(athmenuList);
			String json = arr.toString();
			json = json.replaceAll("menuId", "id").replaceAll("parentId", "pId").replaceAll("menuName", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked").replaceAll("menuUrl", "url").replaceAll("#", "");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("menuUrl",menuUrl);		//本ID菜单链接
			mv.setViewName("system/menu/menu_ztree_other");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMenuId()));
			if(menuList.get(i).isHasMenu() && "1".equals(menuList.get(i).getMenuState())){	//判断是否有此菜单权限并且是否隐藏
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);					//是：继续排查其子菜单
			}else{
				menuList.remove(i);
				i--;
			}
		}
		return menuList;
	}
	
}
