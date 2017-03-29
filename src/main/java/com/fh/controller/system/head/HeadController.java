package com.fh.controller.system.head;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.fhsms.FhsmsManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.ConfigUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SmsUtil;
import com.fh.util.Tools;
import com.fh.util.Watermark;
import com.fh.util.mail.MailSenderInfo;
import com.fh.util.mail.SimpleMailSender;

/**
 * 类名称：HeadController 创建人：ZX 149156999Q 修改时间：2015年11月23日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/head")
public class HeadController extends BaseController {

	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "appuserService")
	private AppuserManager appuserService;
	@Resource(name = "fhsmsService")
	private FhsmsManager fhsmsService;

	/**
	 * 获取头部信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Object getList() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			Session session = Jurisdiction.getSession();
			PageData pds = new PageData();
			pds = (PageData) session.getAttribute(Const.SESSION_userpds);
			if (null == pds) {
				pd.put("username", Jurisdiction.getUsername());// 当前登录者用户名
				pds = userService.findByUsername(pd);
				session.setAttribute(Const.SESSION_userpds, pds);
			}
			pdList.add(pds);
			map.put("list", pdList);
			map.put("fhsmsCount", fhsmsService.findFhsmsCount(Jurisdiction.getUsername()).get("fhsmsCount").toString());// 站内信未读总数
			// String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//
			// 读取WEBSOCKET配置
			PageData webSocketPageData = ConfigUtil.readWebSocket();
			if (null != webSocketPageData) {
				map.put("wimadress", webSocketPageData.getString("wimip") + ":" + webSocketPageData.get("wimport")); // 即时聊天服务器IP和端口
				map.put("oladress", webSocketPageData.getString("olip") + ":" + webSocketPageData.get("olport")); // 在线管理和站内信服务器IP和端口
				map.put("smsSound", webSocketPageData.get("smsSound")); // 站内信提示音效配置
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 获取站内信未读总数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFhsmsCount")
	@ResponseBody
	public Object getFhsmsCount() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("fhsmsCount", fhsmsService.findFhsmsCount(Jurisdiction.getUsername()).get("fhsmsCount").toString());// 站内信未读总数
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去发送邮箱页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEmail")
	public ModelAndView editEmail() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/head/edit_email");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去发送短信页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSendSms")
	public ModelAndView goSendSms() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/head/send_sms");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 发送短信
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendSms")
	@ResponseBody
	public Object sendSms() {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "ok"; // 发送状态
		int count = 0; // 统计发送成功条数
		int zcount = 0; // 理论条数
		List<PageData> pdList = new ArrayList<PageData>();
		String phones = pd.getString("phone"); // 对方邮箱
		String content = pd.getString("content"); // 内容
		String isAll = pd.getString("isAll"); // 是否发送给全体成员 yes or no
		String type = pd.getString("type"); // 类型 1：短信接口1 2：短信接口2
		String fmsg = pd.getString("fmsg"); // 判断是系统用户还是会员 "appuser"为会员用户
		if ("yes".endsWith(isAll)) {
			try {
				List<PageData> userList = new ArrayList<PageData>();
				userList = "appuser".equals(fmsg) ? appuserService.listAllUser(pd) : userService.listAllUser(pd);
				zcount = userList.size();
				try {
					for (int i = 0; i < userList.size(); i++) {
						if (Tools.checkMobileNumber(userList.get(i).getString("phone"))) { // 手机号格式不对就跳过
							if ("1".equals(type)) {
								SmsUtil.sendSms1(userList.get(i).getString("phone"), content); // 调用发短信函数1
							} else {
								SmsUtil.sendSms2(userList.get(i).getString("phone"), content); // 调用发短信函数2
							}
							count++;
						} else {
							continue;
						}
					}
					msg = "ok";
				} catch (Exception e) {
					msg = "error";
				}
			} catch (Exception e) {
				msg = "error";
			}
		} else {
			phones = phones.replaceAll("；", ";");
			phones = phones.replaceAll(" ", "");
			String[] arrtitle = phones.split(";");
			zcount = arrtitle.length;
			try {
				for (int i = 0; i < arrtitle.length; i++) {
					if (Tools.checkMobileNumber(arrtitle[i])) { // 手机号式不对就跳过
						if ("1".equals(type)) {
							SmsUtil.sendSms1(arrtitle[i], content); // 调用发短信函数1
						} else {
							SmsUtil.sendSms2(arrtitle[i], content); // 调用发短信函数2
						}
						count++;
					} else {
						continue;
					}
				}
				msg = "ok";
			} catch (Exception e) {
				msg = "error";
			}
		}
		pd.put("msg", msg);
		pd.put("count", count); // 成功数
		pd.put("ecount", zcount - count); // 失败数
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去发送电子邮件页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSendEmail")
	public ModelAndView goSendEmail() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/head/send_email");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 发送电子邮件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public Object sendEmail() {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "ok"; // 发送状态
		int count = 0; // 统计发送成功条数
		int zcount = 0; // 理论条数
		// String strEmail = Tools.readTxtFile(Const.EMAIL); // 读取邮件配置

		List<PageData> pdList = new ArrayList<PageData>();
		String toEmail = pd.getString("email"); // 对方邮箱
		String title = pd.getString("title"); // 标题
		String content = pd.getString("content"); // 内容
		String type = pd.getString("type"); // 类型
		String isAll = pd.getString("isAll"); // 是否发送给全体成员 yes or no
		String isSeparate = pd.getString("isSeparate"); // 是否分开发送 yes or no
		String fmsg = pd.getString("fmsg"); // 判断是系统用户还是会员 "appuser"为会员用户
		MailSenderInfo mailInfo = new MailSenderInfo(Const.EMAIL);

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); // 读取session中的用户信息(单独用户信息)

		mailInfo.setNickName(user.getName());
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		if ("yes".endsWith(isAll)) {
			try {
				List<PageData> userList = new ArrayList<PageData>();
				userList = "appuser".equals(fmsg) ? appuserService.listAllUser(pd) : userService.listAllUser(pd);
				zcount = userList.size();
				try {
					List<String> list = new ArrayList<String>();
					int userListSize = userList.size();
					for (int i = 0; i < userListSize; i++) {
						if (Tools.checkEmail(userList.get(i).getString("email"))) { // 邮箱格式不对就跳过
							list.add(userList.get(i).getString("email"));
							count++;
						} else {
							continue;
						}
					}
					int size = list.size();
					if (size > 0) {
						if ("yes".endsWith(isSeparate)) {
							for (int i = 0; i < size; i++) {
								String[] s = new String[] { list.get(i) };
								mailInfo.setToAddress(s);
								SimpleMailSender.sendEmail(mailInfo, type);// 调用发送邮件函数
							}
						} else {
							String[] toEmailArray = (String[]) list.toArray(new String[size]);
							mailInfo.setToAddress(toEmailArray);
							SimpleMailSender.sendEmail(mailInfo, type);// 调用发送邮件函数
						}
						msg = "ok";
					} else {
						msg = "error";
					}
				} catch (Exception e) {
					msg = "error";
				}
			} catch (Exception e) {
				msg = "error";
			}
		} else {
			toEmail = toEmail.replaceAll("；", ";");
			toEmail = toEmail.replaceAll(" ", "");
			String[] arrtitle = toEmail.split(";");
			zcount = arrtitle.length;
			try {
				List<String> list = new ArrayList<String>();
				int arrtitleLength = arrtitle.length;
				for (int i = 0; i < arrtitleLength; i++) {
					if (Tools.checkEmail(arrtitle[i])) { // 邮箱格式不对就跳过
						list.add(arrtitle[i]);
						count++;
					} else {
						continue;
					}
				}
				int size = list.size();
				if (size > 0) {
					if ("yes".endsWith(isSeparate)) {
						for (int i = 0; i < size; i++) {
							String[] s = new String[] { list.get(i) };
							mailInfo.setToAddress(s);
							SimpleMailSender.sendEmail(mailInfo, type);// 调用发送邮件函数
						}
					} else {
						String[] toEmailArray = (String[]) list.toArray(new String[size]);
						mailInfo.setToAddress(toEmailArray);
						SimpleMailSender.sendEmail(mailInfo, type);// 调用发送邮件函数
					}
					msg = "ok";
				} else {
					msg = "error";
				}
			} catch (Exception e) {
				msg = "error";
			}
		}
		// } else {
		// msg = "error";
		// }
		// } else {
		// msg = "error";
		// }
		pd.put("msg", msg);
		pd.put("count", count); // 成功数
		pd.put("ecount", zcount - count); // 失败数
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去系统设置页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSystem")
	public ModelAndView goEditEmail() throws Exception {
		if (!"admin".equals(Jurisdiction.getUsername())) {
			return null;
		} // 非admin用户不能修改
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("ysyName", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		PageData sysNamePageData = ConfigUtil.readSysName();// 读取系统名称
		pd.put("ysyName", sysNamePageData.getString("sysName"));

		// pd.put("countPage", Tools.readTxtFile(Const.PAGE)); // 读取每页条数
		PageData pagePageData = ConfigUtil.readPage();// 读取每页条数
		pd.put("countPage", pagePageData.get("countPage"));

		// String strEmail = Tools.readTxtFile(Const.EMAIL); // 读取邮件配置
		PageData mailPageData = ConfigUtil.readMail();// 读取每页条数

		// String strSMS1 = Tools.readTxtFile(Const.SMS1); // 读取短信1配置
		// String strSMS2 = Tools.readTxtFile(Const.SMS2); // 读取短信2配置
		PageData smsPageData = ConfigUtil.readSms();// 读取短信配置

		// String strFWATERM = Tools.readTxtFile(Const.FWATERM); // 读取文字水印配置
		PageData fWaterMPageData = ConfigUtil.readFWaterMark();// 读取文字水印配置

		// String strIWATERM = Tools.readTxtFile(Const.IWATERM); // 读取图片水印配置
		PageData iWaterMPageData = ConfigUtil.readIWaterMark();// 读取图片水印配置

		// pd.put("Token", Tools.readTxtFile(Const.WEIXIN)); // 读取微信配置
		PageData weiXinPageData = ConfigUtil.readWeiXin();

		// String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//
		// 读取WEBSOCKET配置
		PageData webSocketPageData = ConfigUtil.readWebSocket();// 读取WEBSOCKET配置
		
		if (null != mailPageData) {
			pd.putAll(mailPageData);
		}
		
		if (null != smsPageData) {
			pd.putAll(smsPageData);

		}

		if (null != fWaterMPageData) {
			pd.putAll(fWaterMPageData);
			pd.put("isCheck1", fWaterMPageData.get("isCheck"));
		}
		
		if (null != iWaterMPageData) {
			pd.putAll(iWaterMPageData);
			pd.put("isCheck2", iWaterMPageData.get("isCheck"));
		}
		
		if (null != webSocketPageData) {
			pd.putAll(webSocketPageData);
		}
		
		if (null != weiXinPageData) {
			pd.putAll(weiXinPageData);
		}
		
		mv.setViewName("system/head/sys_edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存系统设置1
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSys")
	public ModelAndView saveSys() throws Exception {
		if (!"admin".equals(Jurisdiction.getUsername())) {
			return null;
		} // 非admin用户不能修改
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// Tools.writeFile(Const.SYSNAME, pd.getString("ysyName")); // 写入系统名称
		ConfigUtil.writeSysName(pd);// 写入系统名称

		// Tools.writeFile(Const.PAGE, pd.getString("countPage")); // 写入每页条数
		ConfigUtil.writePage(pd);// 写入每页条数

		// Tools.writeFile(Const.EMAIL, pd.getString("smtp") + ",fh," +
		// pd.getString("port") + ",fh,"
		// + pd.getString("email") + ",fh," + pd.getString("PAW")); // 写入邮件服务器配置
		ConfigUtil.writeMail(pd);// 写入邮件服务器配置

		// Tools.writeFile(Const.SMS1, pd.getString("SMSU1") + ",fh," +
		// pd.getString("SMSPAW1")); // 写入短信1配置
		// Tools.writeFile(Const.SMS2, pd.getString("SMSU2") + ",fh," +
		// pd.getString("SMSPAW2")); // 写入短信2配置
		ConfigUtil.writeSms(pd);

		mv.addObject("msg", "OK");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 保存系统设置2
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSys2")
	public ModelAndView saveSys2() throws Exception {
		if (!"admin".equals(Jurisdiction.getUsername())) {
			return null;
		} // 非admin用户不能修改
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		ConfigUtil.writeFWaterMark(pd);// 文字水印配置
		ConfigUtil.writeIWaterMark(pd);// 图片水印配置
		// Tools.writeFile(Const.FWATERM, pd.getString("isCheck1") + ",fh," +
		// pd.getString("fcontent") + ",fh,"
		// + pd.getString("fontSize") + ",fh," + pd.getString("fontX") + ",fh,"
		// + pd.getString("fontY")); // 文字水印配置
		// Tools.writeFile(Const.IWATERM, pd.getString("isCheck2") + ",fh," +
		// pd.getString("imgUrl") + ",fh,"
		// + pd.getString("imgX") + ",fh," + pd.getString("imgY")); // 图片水印配置
		Watermark.fushValue();
		mv.addObject("msg", "OK");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 保存系统设置3
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSys3")
	public ModelAndView saveSys3() throws Exception {
		if (!"admin".equals(Jurisdiction.getUsername())) {
			return null;
		} // 非admin用户不能修改
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		ConfigUtil.writeWeiXin(pd);
//		Tools.writeFile(Const.WEIXIN, pd.getString("Token")); // 写入微信配置

		ConfigUtil.writeWebSocket(pd);
		// Tools.writeFile(Const.WEBSOCKET, pd.getString("WIMIP") + ",fh," +
		// pd.getString("WIMPORT") + ",fh,"
		// + pd.getString("OLIP") + ",fh," + pd.getString("OLPORT") + ",fh," +
		// pd.getString("FHsmsSound")); // websocket配置
		mv.addObject("msg", "OK");
		mv.setViewName("save_result");
		return mv;
	}

}
