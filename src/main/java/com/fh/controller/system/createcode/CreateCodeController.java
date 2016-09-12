package com.fh.controller.system.createcode;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.createcode.CreateCodeManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileZip;
import com.fh.util.Freemarker;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.StringUtil;

/**
 * 类名称： 代码生成器 创建人：WZX Q149156999 修改时间：2015年11月23日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/createCode")
public class CreateCodeController extends BaseController {

	String menuUrl = "createcode/list.do"; // 菜单地址(权限用)
	@Resource(name = "createcodeService")
	private CreateCodeManager createcodeService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "find")) {
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 检索条件
		if (null != keywords && !"".equals(keywords)) {
			keywords = keywords.trim();
			pd.put("keywords", keywords);
		}
		page.setPd(pd);
		List<PageData> varList = createcodeService.list(page); // 列出CreateCode列表
		mv.setViewName("system/createcode/createcode_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("jurisdiction", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去代码生成器页面(进入弹窗)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goProductCode")
	public ModelAndView goProductCode() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String createcodeId = pd.getString("createcodeId");
		if (!"add".equals(createcodeId)) {
			pd = createcodeService.findById(pd);
			mv.addObject("pd", pd);
			mv.addObject("msg", "edit");

		} else {
			mv.addObject("msg", "add");
		}
		List<PageData> varList = createcodeService.listFa(); // 列出所有主表结构的
		mv.addObject("varList", varList);
		mv.setViewName("system/createcode/productCode");
		return mv;
	}

	/**
	 * 生成代码
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/proCode")
	public void proCode(HttpServletResponse response) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "执行代码生成器");
		PageData pd = new PageData();
		pd = this.getPageData();
		save(pd); // 保存到数据库
		/*
		 * =====================================================================
		 * ========================
		 */
		String faobject = pd.getString("faobject"); // 主表名 ========参数0-1 主附结构用
		String fhtype = pd.getString("fhtype"); // 模块类型 ========参数0-2
												// 类型，单表、树形结构、主表明细表
		String title = pd.getString("title"); // 说明 ========参数0
		String packageName = pd.getString("packageName"); // 包名 ========参数1
		String objectName = pd.getString("objectName"); // 类名 ========参数2
		String tabletop = pd.getString("tabletop"); // 表前缀 ========参数3
		tabletop = null == tabletop ? "" : tabletop.toUpperCase(); // 表前缀转大写
		String zindext = pd.getString("zindex"); // 属性总数
		int zindex = 0;
		if (null != zindext && !"".equals(zindext)) {
			zindex = Integer.parseInt(zindext);
		}
		List<String[]> fieldList = new ArrayList<String[]>(); // 属性集合
																// ========参数4
		for (int i = 0; i < zindex; i++) {
			String[] s = pd.getString("field" + i).split(",fh,");
			String[] a = new String[2];
			a[0] = StringUtil.replaceUnderlineAndfirstToUpper(s[0], "_", "");
			a[1] = StringUtil.firstCharacterToUpper(a[0]);
			String[] b = StringUtil.concat(s, a);
			fieldList.add(b); // 属性放到集合里面
		}
		Map<String, Object> root = new HashMap<String, Object>(); // 创建数据模型
		root.put("fieldList", fieldList);
		root.put("faobject", faobject.toUpperCase()); // 主附结构用，主表名
		root.put("title", title); // 说明
		root.put("packageName", packageName); // 包名
		root.put("objectNameOriginal", objectName.toLowerCase()); // 类名
		String objectNameUpper = StringUtil.replaceUnderlineAndfirstToUpper(objectName, "_", "");
		root.put("objectName", objectNameUpper); // 类名
		String objectNameFirstUpper = StringUtil.firstCharacterToUpper(objectNameUpper);
		root.put("objectNameFirstUpper", objectNameFirstUpper); // 类名(首字母大写和遇到_改成大写)
		String objectNameFirstLower = StringUtil.firstCharacterToLower(objectNameUpper);
		root.put("objectNameFirstLower", objectNameFirstLower); // 类名(首字母小写)
		String objectNameLower = objectNameFirstLower.toLowerCase();
		root.put("objectNameLower", objectNameLower); // 类名(全小写)
		root.put("tabletop", tabletop); // 表前缀
		root.put("nowDate", new Date()); // 当前日期

		DelAllFile.delFolder(PathUtil.getClasspath() + "admin/ftl"); // 生成代码前,先清空之前生成的代码
		/*
		 * =====================================================================
		 * ========================
		 */
		String filePath = "admin/ftl/code/"; // 存放路径
		String ftlPath = "createCode"; // ftl路径
		if ("tree".equals(fhtype)) {
			ftlPath = "createTreeCode";
			/* 生成实体类 */
			Freemarker.printFile("entityTemplate.ftl", root, "entity/" + packageName + "/" + objectNameUpper + ".java",
					filePath, ftlPath);
			/* 生成jsp_tree页面 */
			Freemarker.printFile("jsp_tree_Template.ftl", root, "jsp/" + packageName + "/"
					+ objectNameLower.toLowerCase() + "/" + objectNameLower.toLowerCase() + "_tree.jsp", filePath,
					ftlPath);
		} else if ("fathertable".equals(fhtype)) {
			ftlPath = "createFaCode"; // 主表
		} else if ("sontable".equals(fhtype)) {
			ftlPath = "createSoCode"; // 明细表
		}
		/* 生成controller */
		Freemarker.printFile("controllerTemplate.ftl", root, "controller/" + packageName + "/"
				+ objectNameLower.toLowerCase() + "/" + objectNameUpper + "Controller.java", filePath, ftlPath);
		/* 生成service */
		Freemarker.printFile("serviceTemplate.ftl", root, "service/" + packageName + "/" + objectNameLower.toLowerCase()
				+ "/impl/" + objectNameUpper + "Service.java", filePath, ftlPath);
		/* 生成manager */
		Freemarker.printFile("managerTemplate.ftl", root,
				"service/" + packageName + "/" + objectNameLower.toLowerCase() + "/" + objectNameUpper + "Manager.java",
				filePath, ftlPath);
		/* 生成mybatis xml */
		Freemarker.printFile("mapperMysqlTemplate.ftl", root,
				"mybatis_mysql/" + packageName + "/" + objectNameUpper + "Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperOracleTemplate.ftl", root,
				"mybatis_oracle/" + packageName + "/" + objectNameUpper + "Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperSqlserverTemplate.ftl", root,
				"mybatis_sqlserver/" + packageName + "/" + objectNameUpper + "Mapper.xml", filePath, ftlPath);
		/* 生成SQL脚本 */
		Freemarker.printFile("mysql_SQL_Template.ftl", root,
				"mysql数据库脚本/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);
		Freemarker.printFile("oracle_SQL_Template.ftl", root,
				"oracle数据库脚本/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);
		Freemarker.printFile("sqlserver_SQL_Template.ftl", root,
				"sqlserver数据库脚本/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);
		/* 生成jsp页面 */
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/" + packageName + "/" + objectNameLower.toLowerCase()
				+ "/" + objectNameLower.toLowerCase() + "_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/" + packageName + "/" + objectNameLower.toLowerCase()
				+ "/" + objectNameLower.toLowerCase() + "_edit.jsp", filePath, ftlPath);
		/* 生成说明文档 */
		Freemarker.printFile("docTemplate.ftl", root, "部署说明.doc", filePath, ftlPath);
		// this.print("oracle_SQL_Template.ftl", root); 控制台打印
		/* 生成的全部代码压缩成zip文件 */
		if (FileZip.zip(PathUtil.getClasspath() + "admin/ftl/code", PathUtil.getClasspath() + "admin/ftl/code.zip")) {
			/* 下载代码 */
			FileDownload.fileDownload(response, PathUtil.getClasspath() + "admin/ftl/code.zip", "code.zip");
		}
	}

	/**
	 * 保存到数据库
	 * 
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		pd.put("packageName", pd.getString("packageName")); // 包名
		pd.put("objectName", pd.getString("objectName")); // 类名
		pd.put("tableName", pd.getString("tabletop") + ",fh," + pd.getString("objectName").toUpperCase()); // 表名
		pd.put("fieldList", pd.getString("fieldList")); // 属性集合
		pd.put("createTime", DateUtil.getTime()); // 创建时间
		pd.put("title", pd.getString("title")); // 说明
		pd.put("createcodeId", this.get32UUID()); // 主键
		createcodeService.save(pd);
	}

	/**
	 * 通过ID获取数据
	 */
	@RequestMapping(value = "/findById")
	@ResponseBody
	public Object findById() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "find")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = createcodeService.findById(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 删除
	 * 
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除CreateCode");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		createcodeService.delete(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除CreateCode");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String dataIds = pd.getString("dataIds");
			if (null != dataIds && !"".equals(dataIds)) {
				String arrayDataIds[] = dataIds.split(",");
				createcodeService.deleteAll(arrayDataIds);
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

}
