package com.fh.util.mail;

import java.util.Properties;

import com.fh.util.Const;
import com.fh.util.PropertiesUtil;
import com.fh.util.StringUtil;

/**
 * 发送邮件需要使用的基本信息
 * 
 * @author wangzhengxing 修改时间：2015年7月27日
 * @version 2.0
 */
public class MailSenderInfo {
	// 发送邮件的服务器的IP和端口
	private String mailServerHost = "";
	private int mailServerPort = 25;
	// 登陆邮件发送服务器的用户名和密码
	private String username = "";
	private String password = "";
	private String nickName;
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String[] toAddress;
	// 是否需要身份验证
	private boolean validate = true;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;

	public MailSenderInfo() {
		PropertiesUtil pu = new PropertiesUtil(Const.EMAIL);
		String smtp = pu.getProperty("smtp");
		String portString = pu.getProperty("port");
		int port = Integer.valueOf(portString);
		String email = pu.getProperty("email");
		String paw = pu.getProperty("paw");
		String validateString = pu.getProperty("validate");
		boolean validate = false;
		if (!StringUtil.isEmpty(validateString.trim())) {
			validate = Boolean.valueOf(validateString);
		}
		if (StringUtil.isEmpty(this.nickName)) {
			String nickName = pu.getProperty("nickName");
			this.nickName = nickName;
		}
		this.mailServerHost = smtp;
		this.mailServerPort = port;
		this.username = email;
		this.password = paw;
		this.validate = validate;
	}

	public MailSenderInfo(String mailServerHost, int mailServerPort, String username, String password,
			boolean validate) {
		this.mailServerHost = mailServerHost;
		this.mailServerPort = mailServerPort;
		this.username = username;
		this.password = password;
		this.validate = validate;
	}

	public MailSenderInfo(String filePath) {
		PropertiesUtil pu = new PropertiesUtil(filePath);
		String smtp = pu.getProperty("smtp");
		String portString = pu.getProperty("port");
		int port = Integer.valueOf(portString);
		String email = pu.getProperty("email");
		String paw = pu.getProperty("paw");
		String validateString = pu.getProperty("validate");
		boolean validate = false;
		if (!StringUtil.isEmpty(validateString.trim())) {
			validate = Boolean.valueOf(validateString);
		}
		if (StringUtil.isEmpty(this.nickName)) {
			String nickName = pu.getProperty("nickName");
			this.nickName = nickName;
		}
		this.mailServerHost = smtp;
		this.mailServerPort = port;
		this.username = email;
		this.password = paw;
		this.validate = validate;

	}

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public int getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(int mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
