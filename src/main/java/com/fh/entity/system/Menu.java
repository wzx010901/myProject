package com.fh.entity.system;

import java.util.List;
/**
 * 
* 类名称：菜单
* 类描述： 
* @author wangzhengxing
* 作者单位： 
* 联系方式：
* 创建时间：2015年7月27日
* @version 2.0
 */
public class Menu {
	
	private String menuId;		//菜单ID
	private String menuName;	//菜单名称
	private String menuUrl;	//链接
	private String parentId;	//上级菜单id
	private String menuOrder;	//排序
	private String menuIcon;	//图标
	private String menuType;	//类型
	private String menuState;	//菜单状态
	private String target;
	private Menu parentMenu;
	private List<Menu> subMenu;
	private boolean hasMenu = false;
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * @return the menuUrl
	 */
	public String getMenuUrl() {
		return menuUrl;
	}
	/**
	 * @param menuUrl the menuUrl to set
	 */ 
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the menuOrder
	 */
	public String getMenuOrder() {
		return menuOrder;
	}
	/**
	 * @param menuOrder the menuOrder to set
	 */
	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}
	/**
	 * @return the menuIcon
	 */
	public String getMenuIcon() {
		return menuIcon;
	}
	/**
	 * @param menuIcon the menuIcon to set
	 */
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}
	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	/**
	 * @return the menuState
	 */
	public String getMenuState() {
		return menuState;
	}
	/**
	 * @param menuState the menuState to set
	 */
	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the parentMenu
	 */
	public Menu getParentMenu() {
		return parentMenu;
	}
	/**
	 * @param parentMenu the parentMenu to set
	 */
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	/**
	 * @return the subMenu
	 */
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	/**
	 * @param subMenu the subMenu to set
	 */
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	/**
	 * @return the hasMenu
	 */
	public boolean isHasMenu() {
		return hasMenu;
	}
	/**
	 * @param hasMenu the hasMenu to set
	 */
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	
	
}
