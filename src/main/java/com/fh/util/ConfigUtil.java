package com.fh.util;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {

	/**
	 * 读取系统名称
	 * 
	 * @return map sysName 系统名称
	 */
	public static PageData readSysName() {
		PropertiesUtil sysNamePropertiesUtil = new PropertiesUtil(Const.SYSNAME);
		PageData map = sysNamePropertiesUtil.getAllProperty();
		return map;
	}

	/**
	 * 写入系统名称
	 * 
	 * @param pd
	 *            ysyName 系统名称<br>
	 */
	public static void writeSysName(PageData pd) {
		String ysyName = pd.getString("ysyName");
		PropertiesUtil sysNamePropertiesUtil = new PropertiesUtil(Const.SYSNAME);
		String sysName = sysNamePropertiesUtil.getProperty("sysName");
		Map<String, Object> sysNameMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(ysyName)) {
			if (!ysyName.equals(sysName)) {
				sysNameMap.put("sysName", ysyName);
			}
		}
		if (sysNameMap.size() > 0) {
			sysNamePropertiesUtil.writeProperties(sysNameMap);// 写入邮件服务器配置
		}
	}

	/**
	 * 读取每页条数
	 * 
	 * @return map countPage 每页显示几条
	 */
	public static PageData readPage() {
		PropertiesUtil pagePropertiesUtil = new PropertiesUtil(Const.PAGE);
		PageData map = pagePropertiesUtil.getAllProperty();
		return map;
	}

	/**
	 * 写入每页条数
	 * 
	 * @param pd
	 *            countPage 页数<br>
	 */
	public static void writePage(PageData pd) {
		Integer countPageParam = Integer.parseInt(pd.getString("countPage"));
		PropertiesUtil pagePropertiesUtil = new PropertiesUtil(Const.PAGE);
		int countPageFile = Integer.parseInt(pagePropertiesUtil.getProperty("countPage"));
		Map<String, Object> pageMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(countPageParam)) {
			if (!countPageParam.equals(countPageFile)) {
				pageMap.put("countPage", countPageParam);
			}
		}
		if (pageMap.size() > 0) {
			pagePropertiesUtil.writeProperties("countPage", "" + countPageParam);// 写入邮件服务器配置
		}
	}

	/**
	 * 读取邮箱信息
	 * 
	 * @return map smtp smtp服务器地址<br>
	 *         port 端口<br>
	 *         email 邮箱地址<br>
	 *         paw 密码<br>
	 *         validate 是否加密<br>
	 *         nickName 别名<br>
	 */
	public static PageData readMail() {
		PropertiesUtil mailPropertiesUtil = new PropertiesUtil(Const.EMAIL);
		PageData pdFile = mailPropertiesUtil.getAllProperty();
		return pdFile;
	}

	/**
	 * 写入邮箱信息
	 * 
	 * @param pd
	 *            smtp smtp服务器地址<br>
	 *            port 端口<br>
	 *            email 邮箱地址<br>
	 *            paw 密码<br>
	 *            validate 是否加密<br>
	 *            nickName 别名<br>
	 */
	public static void writeMail(PageData pd) {
		String smtpParam = pd.getString("smtp");
		Integer portParam = pd.get("port") == null ? null : Integer.parseInt(pd.getString("port"));
		String emailParam = pd.getString("email");
		String pawParam = pd.getString("paw");

		PropertiesUtil mailPropertiesUtil = new PropertiesUtil(Const.EMAIL);
		PageData pdFile = mailPropertiesUtil.getAllProperty();
		String smtpFile = pdFile.getString("smtp");
		int portFile = (Integer) pdFile.get("port");
		String emailFile = pd.getString("email");
		String pawFile = pd.getString("paw");

		Map<String, Object> mailMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(smtpParam)) {
			if (!smtpParam.equals(smtpFile)) {
				mailMap.put("smtp", smtpParam);
			}
		}
		if (!StringUtil.isEmpty(portParam)) {
			if (portParam != portFile) {
				mailMap.put("port", portParam);
			}
		}

		if (!StringUtil.isEmpty(emailParam)) {
			if (emailParam.equals(emailFile)) {
				mailMap.put("email", emailParam);
			}
		}
		if (!StringUtil.isEmpty(pawParam)) {
			if (pawParam.equals(pawFile)) {
				mailMap.put("paw", pawParam);
			}
		}
		if (mailMap.size() > 0) {
			mailPropertiesUtil.writeProperties(mailMap);
		}
	}

	/**
	 * 读取短信配置
	 * 
	 * @return map smsUrl短信地址<br>
	 *         appkey <br>
	 *         secret 密钥<br>
	 *         username<br>
	 *         password<br>
	 */
	public static PageData readSms() {
		PropertiesUtil smsPropertiesUtil = new PropertiesUtil(Const.SMS);
		PageData pdFile = smsPropertiesUtil.getAllProperty();
		return pdFile;
	}

	/**
	 * 写入短信配置
	 * 
	 * @param pd
	 *            smsUrl短信地址<br>
	 *            appkey <br>
	 *            secret 密钥<br>
	 *            username<br>
	 *            password<br>
	 */
	public static void writeSms(PageData pd) {

		String smsUrlParam = pd.getString("smsUrl");
		String appkeyParam = pd.getString("appkey");
		String secretParam = pd.getString("secret");
		String usernameParam = pd.getString("smsUsername");
		String passwordParam = pd.getString("smsPassword");

		PropertiesUtil smsPropertiesUtil = new PropertiesUtil(Const.SMS);
		String smsUrlFile = smsPropertiesUtil.getProperty("smsUrl");
		String appkeyFile = smsPropertiesUtil.getProperty("appkey");
		String secretFile = smsPropertiesUtil.getProperty("secret");
		String usernameFile = smsPropertiesUtil.getProperty("smsUsername");
		String passwordFile = smsPropertiesUtil.getProperty("smsPassword");

		Map<String, Object> smsMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(smsUrlParam)) {
			if (smsUrlParam.equals(smsUrlFile)) {
				smsMap.put("smsUrl", smsUrlParam);
			}
		}
		if (!StringUtil.isEmpty(appkeyParam)) {
			if (appkeyParam.equals(appkeyFile)) {
				smsMap.put("appkey", appkeyParam);
			}
		}
		if (!StringUtil.isEmpty(secretParam)) {
			if (secretParam.equals(secretFile)) {
				smsMap.put("secret", secretParam);
			}
		}
		if (!StringUtil.isEmpty(usernameParam)) {
			if (usernameParam.equals(usernameFile)) {
				smsMap.put("username", usernameParam);
			}
		}
		if (!StringUtil.isEmpty(passwordParam)) {
			if (passwordParam.equals(passwordFile)) {
				smsMap.put("password", passwordParam);
			}
		}
		if (smsMap.size() > 0) {
			smsPropertiesUtil.writeProperties(smsMap);
		}
	}

	/**
	 * 读取文字水印
	 * 
	 * @return map isCheck 是否选中<br>
	 *         fcontent 文字内容<br>
	 *         fontSize 文字大小<br>
	 *         fontX 位置坐标x <br>
	 *         fontY 位置坐标y<br>
	 */
	public static PageData readFWaterMark() {
		PropertiesUtil fWaterMPropertiesUtil = new PropertiesUtil(Const.FWATERM);
		PageData map = fWaterMPropertiesUtil.getAllProperty();
		return map;
	}

	/**
	 * 写入文字水印
	 * 
	 * @param pd
	 *            isCheck1 是否选中<br>
	 *            fcontent 文字内容<br>
	 *            fontSize 文字大小<br>
	 *            fontX 位置坐标x <br>
	 *            fontY 位置坐标y<br>
	 */
	public static void writeFWaterMark(PageData pd) {
		String isCheckParam = pd.getString("isCheck");
		String fcontentParam = pd.getString("fcontent");
		String fontSizeParam = pd.getString("fontSize");
		String fontXParam = pd.getString("fontX");
		String fontYParam = pd.getString("fontY");

		PropertiesUtil fWaterMPropertiesUtil = new PropertiesUtil(Const.FWATERM);
		String isCheckFile = fWaterMPropertiesUtil.getProperty("isCheck");
		String fcontentFile = fWaterMPropertiesUtil.getProperty("fcontent");
		String fontSizeFile = fWaterMPropertiesUtil.getProperty("fontSize");
		String fontXFile = fWaterMPropertiesUtil.getProperty("fontX");
		String fontYFile = fWaterMPropertiesUtil.getProperty("fontY");

		Map<String, Object> fWaterMMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(isCheckParam)) {
			if (!isCheckParam.equals(isCheckFile)) {
				fWaterMMap.put("isCheck", isCheckParam);
			}
		}
		if (!StringUtil.isEmpty(fcontentParam)) {
			if (!fcontentParam.equals(fcontentFile)) {
				fWaterMMap.put("fcontent", fcontentParam);
			}
		}
		if (!StringUtil.isEmpty(fontSizeParam)) {
			if (!fontSizeParam.equals(fontSizeFile)) {
				fWaterMMap.put("fontSize", fontSizeParam);
			}
		}
		if (!StringUtil.isEmpty(fontXParam)) {
			if (!fontXParam.equals(fontXFile)) {
				fWaterMMap.put("fontX", fontXParam);
			}
		}
		if (!StringUtil.isEmpty(fontYParam)) {
			if (!fontYParam.equals(fontYFile)) {
				fWaterMMap.put("fontY", fontYParam);
			}
		}
		if (fWaterMMap.size() > 0) {
			fWaterMPropertiesUtil.writeProperties(fWaterMMap);
		}
	}

	/**
	 * 读取图片水印
	 * 
	 * @return map isCheck是否选中<br>
	 *         imgUrl 图片地址<br>
	 *         imgX 图片X坐标<br>
	 *         imgY图片Y坐标<br>
	 */
	public static PageData readIWaterMark() {
		PropertiesUtil fWaterMPropertiesUtil = new PropertiesUtil(Const.IWATERM);
		PageData map = fWaterMPropertiesUtil.getAllProperty();
		return map;
	}

	/**
	 * 写入图片水印
	 * 
	 * @param pd
	 *            isCheck2 是否选中<br>
	 *            imgUrl 图片地址<br>
	 *            imgX 图片X坐标<br>
	 *            imgY图片Y坐标<br>
	 * 
	 */
	public static void writeIWaterMark(PageData pd) {
		String isCheckParam = pd.getString("isCheck");
		String imgUrlParam = pd.getString("imgUrl");
		String imgXParam = pd.getString("imgX");
		String imgYParam = pd.getString("imgY");

		PropertiesUtil iWaterMPropertiesUtil = new PropertiesUtil(Const.IWATERM);
		String isCheckFile = iWaterMPropertiesUtil.getProperty("isCheck");
		String imgUrlFile = iWaterMPropertiesUtil.getProperty("imgUrl");
		String imgXFile = iWaterMPropertiesUtil.getProperty("imgX");
		String imgYFile = iWaterMPropertiesUtil.getProperty("imgY");

		Map<String, Object> iWaterMMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(isCheckParam)) {
			if (!isCheckParam.equals(isCheckFile)) {
				iWaterMMap.put("isCheck", isCheckParam);
			}
		}
		if (!StringUtil.isEmpty(imgUrlParam)) {
			if (!imgUrlParam.equals(imgUrlFile)) {
				iWaterMMap.put("imgUrl", imgUrlParam);
			}
		}
		if (!StringUtil.isEmpty(imgXParam)) {
			if (!imgXParam.equals(imgXFile)) {
				iWaterMMap.put("imgX", imgXParam);
			}
		}
		if (!StringUtil.isEmpty(imgYParam)) {
			if (!imgYParam.equals(imgYFile)) {
				iWaterMMap.put("imgY", imgYParam);
			}
		}
		if (iWaterMMap.size() > 0) {
			iWaterMPropertiesUtil.writeProperties(iWaterMMap);
		}
	}

	/**
	 * 读取webSock配置
	 * 
	 * @return map wimip是否选中<br>
	 *         wimport 图片地址<br>
	 *         olip 图片X坐标<br>
	 *         olport 图片Y坐标<br>
	 *         smsSound<br>
	 */
	public static PageData readWebSocket() {
		PropertiesUtil fWaterMPropertiesUtil = new PropertiesUtil(Const.WEBSOCKET);
		PageData map = fWaterMPropertiesUtil.getAllProperty();
		return map;
	}

	/**
	 * 写入webSocket
	 * 
	 * @param pd
	 *            wimip是否选中<br>
	 *            wimport 图片地址<br>
	 *            olip 图片X坐标<br>
	 *            olport 图片Y坐标<br>
	 *            smsSound<br>
	 * 
	 */
	public static void writeWebSocket(PageData pd) {
		String wimipParam = pd.getString("wimip");
		String wimportParam = pd.getString("wimport");
		String olipParam = pd.getString("olip");
		String olportParam = pd.getString("olport");
		String smsSoundParam = pd.getString("smsSound");

		PropertiesUtil webSocketPropertiesUtil = new PropertiesUtil(Const.WEBSOCKET);
		String wimipFile = webSocketPropertiesUtil.getProperty("wimip");
		String wimportFile = webSocketPropertiesUtil.getProperty("wimport");
		String olipFile = webSocketPropertiesUtil.getProperty("olip");
		String olportFile = webSocketPropertiesUtil.getProperty("olport");
		String smsSoundFile = webSocketPropertiesUtil.getProperty("smsSound");

		Map<String, Object> webSocketMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(wimipParam)) {
			if (!wimipParam.equals(wimipFile)) {
				webSocketMap.put("wimip", wimipParam);
			}
		}
		if (!StringUtil.isEmpty(wimportParam)) {
			if (!wimportParam.equals(wimportFile)) {
				webSocketMap.put("wimport", wimportParam);
			}
		}
		if (!StringUtil.isEmpty(olipParam)) {
			if (!olipParam.equals(olipFile)) {
				webSocketMap.put("olip", olipParam);
			}
		}
		if (!StringUtil.isEmpty(olportParam)) {
			if (!olportParam.equals(olportFile)) {
				webSocketMap.put("olport", olportParam);
			}
		}
		if (!StringUtil.isEmpty(smsSoundParam)) {
			if (!smsSoundParam.equals(smsSoundFile)) {
				webSocketMap.put("smsSound", smsSoundParam);
			}
		}
		if (webSocketMap.size() > 0) {
			webSocketPropertiesUtil.writeProperties(webSocketMap);
		}
	}

	/**
	 * 读取微信配置
	 * 
	 * @return map weixin 系统名称
	 */
	public static PageData readWeiXin() {
		PropertiesUtil sysNamePropertiesUtil = new PropertiesUtil(Const.WEIXIN);
		PageData map = sysNamePropertiesUtil.getAllProperty();
		return map;
	}

	/**
	 * 写入系统名称
	 * 
	 * @param pd
	 *            ysyName 系统名称<br>
	 */
	public static void writeWeiXin(PageData pd) {
		String tonkenParam = pd.getString("token");
		PropertiesUtil weiXinPropertiesUtil = new PropertiesUtil(Const.WEIXIN);
		String tonkenFile = weiXinPropertiesUtil.getProperty("token");
		Map<String, Object> weiXinMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(tonkenParam)) {
			if (!tonkenParam.equals(tonkenFile)) {
				weiXinMap.put("tonken", tonkenParam);
			}
		}
		if (weiXinMap.size() > 0) {
			weiXinPropertiesUtil.writeProperties(weiXinMap);// 写入邮件服务器配置
		}
	}
}
