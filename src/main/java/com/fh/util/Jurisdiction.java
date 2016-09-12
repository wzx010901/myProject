package com.fh.util;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.fh.entity.system.Menu;

/**
 * 权限处理
 * @author:wangzhengxing
 * 修改日期：2015/11/19
*/
public class Jurisdiction {

	/**
	 * 访问权限及初始化按钮权限(控制按钮的显示 增删改查)
	 * @param menuUrl  菜单路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasJurisdiction(String menuUrl){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		String username = getUsername();	//获取当前登录者loginname
		Session session = getSession();
		List<Menu> menuList = (List<Menu>)session.getAttribute(username + Const.SESSION_allmenuList); //获取菜单列表
		return readMenu(menuList,menuUrl,session,username);
	}
	
	/**校验菜单权限并初始按钮权限用于页面按钮显示与否(递归处理)
	 * @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
	 * @param menuUrl:访问地址
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean readMenu(List<Menu> menuList,String menuUrl,Session session,String username){
		for(int i=0;i<menuList.size();i++){
			if(menuList.get(i).getMenuUrl().split(".do")[0].equals(menuUrl.split(".do")[0])){ //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
					if(!menuList.get(i).isHasMenu()){				//判断有无此菜单权限
					return false;
				}else{											//按钮判断
					Map<String, String> map = (Map<String, String>)session.getAttribute(username + Const.SESSION_JURISDICTION);//按钮权限(增删改查)
					map.remove("add");
					map.remove("del");
					map.remove("edit");
					map.remove("find");
					String menuId =  menuList.get(i).getMenuId();
					Boolean isAdmin = "admin".equals(username);
					map.put("add", (RightsHelper.testRights(map.get("adds"), menuId)) || isAdmin?"1":"0");
					map.put("del", RightsHelper.testRights(map.get("dels"), menuId) || isAdmin?"1":"0");
					map.put("edit", RightsHelper.testRights(map.get("edits"), menuId) || isAdmin?"1":"0");
					map.put("find", RightsHelper.testRights(map.get("finds"), menuId) || isAdmin?"1":"0");
					session.removeAttribute(username + Const.SESSION_JURISDICTION);
					session.setAttribute(username + Const.SESSION_JURISDICTION, map);	//重新分配按钮权限
					return true;
				}
			}else{
				if(!readMenu(menuList.get(i).getSubMenu(),menuUrl,session,username)){//继续排查其子菜单
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 按钮权限(方法中校验)
	 * @param menuUrl  菜单路径
	 * @param type  类型(add、del、edit、find)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean buttonJurisdiction(String menuUrl, String type){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		String username = getUsername();	//获取当前登录者loginname
		Session session = getSession();
		List<Menu> menuList = (List<Menu>)session.getAttribute(username + Const.SESSION_allmenuList); //获取菜单列表
		return readMenuButton(menuList,menuUrl,session,username,type);
	}
	
	/**校验按钮权限(递归处理)
	 * @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
	 * @param menuUrl:访问地址
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean readMenuButton(List<Menu> menuList,String menuUrl,Session session,String username, String type){
		for(int i=0;i<menuList.size();i++){
			if(menuList.get(i).getMenuUrl().split(".do")[0].equals(menuUrl.split(".do")[0])){ //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
				if(!menuList.get(i).isHasMenu()){				//判断有无此菜单权限
					return false;
				}else{											//按钮判断
					Map<String, String> map = (Map<String, String>)session.getAttribute(username + Const.SESSION_JURISDICTION);//按钮权限(增删改查)
					String menuId =  menuList.get(i).getMenuId();
					Boolean isAdmin = "admin".equals(username);
					if("add".equals(type)){
						return ((RightsHelper.testRights(map.get("adds"), menuId)) || isAdmin);
					}else if("del".equals(type)){
						return ((RightsHelper.testRights(map.get("dels"), menuId)) || isAdmin);
					}else if("edit".equals(type)){
						return ((RightsHelper.testRights(map.get("edits"), menuId)) || isAdmin);
					}else if("find".equals(type)){
						return ((RightsHelper.testRights(map.get("finds"), menuId)) || isAdmin);
					}
				}
			}else{
				if(!readMenuButton(menuList.get(i).getSubMenu(),menuUrl,session,username,type)){//继续排查其子菜单
					return false;
				};
			}
		}
		return true;
	}
	
	/**获取当前登录的用户名
	 * @return
	 */
	public static String getUsername(){
		return getSession().getAttribute(Const.SESSION_USERNAME).toString();
	}
	
	/**获取当前按钮权限(增删改查)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getHC(){
		return (Map<String, String>)getSession().getAttribute(getUsername() + Const.SESSION_JURISDICTION);
	}
	
	/**shiro管理的session
	 * @return
	 */
	public static Session getSession(){
		//Subject currentUser = SecurityUtils.getSubject();  
		return SecurityUtils.getSubject().getSession();
	}
}
