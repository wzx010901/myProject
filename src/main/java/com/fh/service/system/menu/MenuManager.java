package com.fh.service.system.menu;

import java.util.List;

import com.fh.entity.system.Menu;
import com.fh.util.PageData;


/**说明：MenuService 菜单处理接口
 * @author fh 149156999
 */
public interface MenuManager {

	/**
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listSubMenuByParentId(String parentId)throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception;
	
	/**
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception;
	
	/**
	 * @param menuId
	 * @throws Exception
	 */
	public void deleteMenuById(String menuId) throws Exception;
	
	/**
	 * @param menu
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception;
	
	/**
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String menuId) throws Exception;
	
	/**
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuJurisdiction(String menuId) throws Exception;
}
