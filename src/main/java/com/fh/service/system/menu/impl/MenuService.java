package com.fh.service.system.menu.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.PageData;

/** 
 * 类名称：MenuService 菜单处理
 * 创建人：zx qq1 4 9 1 5 6 9 9 9 [碌碌]
 * 修改时间：2015年10月27日
 * @version v2
 */
@Service("menuService")
public class MenuService implements MenuManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 通过ID获取其子一级菜单
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}
	
	/**
	 * 通过菜单ID获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception {
		dao.save("MenuMapper.insertMenu", menu);
	}
	
	/**
	 * 取最大ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @throws Exception
	 */
	public void deleteMenuById(String menuId) throws Exception {
		dao.save("MenuMapper.deleteMenuById", menuId);
	}
	
	/**
	 * 编辑
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception {
		 dao.update("MenuMapper.updateMenu", menu);
	}
	
	/**
	 * 保存菜单图标 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.editicon", pd);
	}
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String menuId) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(menuId);
		for(Menu menu : menuList){
			menu.setMenuUrl("menu/toEdit.do?menuId="+menu.getMenuId());
			menu.setSubMenu(this.listAllMenu(menu.getMenuId()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuJurisdiction(String menuId) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(menuId);
		for(Menu menu : menuList){
			menu.setSubMenu(this.listAllMenuJurisdiction(menu.getMenuId()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}
	
}
