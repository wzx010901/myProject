package com.fh.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.fh.util.Logger;

/**
 * 邮件发送器
 * 
 * @author wangzhengxing
 */
public class SimpleMailSender {

	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		// Session sendMailSession = Session.getDefaultInstance(pro,
		// authenticator);
		Session sendMailSession = Session.getInstance(pro, authenticator);
		logger.info("构造一个发送邮件的session");
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		// Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		// mailMessage.setFrom(from);
		String nick = javax.mail.internet.MimeUtility.encodeText(mailInfo.getNickName());
		mailMessage.setFrom(new InternetAddress(nick + " <" + mailInfo.getUsername() + ">"));
		// 创建邮件的接收者地址，并设置到邮件消息中

		Address[] a = new Address[mailInfo.getToAddress().length];
		for (int i = 0; i < mailInfo.getToAddress().length; i++) {
			a[i] = new InternetAddress(mailInfo.getToAddress()[i]);
		}
		// Address to = new InternetAddress(mailInfo.getToAddress());

		mailMessage.setRecipients(Message.RecipientType.TO, a);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// 设置邮件消息的主要内容
		String mailContent = mailInfo.getContent();
		mailMessage.setText(mailContent);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		// Session sendMailSession = Session.getInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者标题
		String nick = javax.mail.internet.MimeUtility.encodeText(mailInfo.getNickName());
		mailMessage.setFrom(new InternetAddress(nick + " <" + mailInfo.getUsername() + ">"));
		// 创建邮件发送者地址
		Address[] a = new Address[mailInfo.getToAddress().length];
		for (int i = 0; i < mailInfo.getToAddress().length; i++) {
			a[i] = new InternetAddress(mailInfo.getToAddress()[i]);
		}
		mailMessage.setRecipients(Message.RecipientType.TO, a);

		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);

		// 邮件的附件
		BodyPart attachmentBodyPart;
		javax.activation.DataSource source;
		if (mailInfo.getAttachFileNames() != null) {
			for (String attachment : mailInfo.getAttachFileNames()) {
				attachmentBodyPart = new MimeBodyPart();
				source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));
				System.out.println(source.getName());
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(source.getName()));
				mainPart.addBodyPart(attachmentBodyPart);
			}
		}

		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}

	/*
	 * @title:标题
	 * 
	 * @content:内容
	 * 
	 * @type:类型,1:文本格式;2:html格式
	 * 
	 * @tomail:接收的邮箱
	 */
	public boolean sendMail(String title, String content, String type, String[] tomail) throws Exception {

		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort(25);
		mailInfo.setValidate(true);
		mailInfo.setUsername("itfather@1b23.com");
		mailInfo.setPassword("tttt");// 您的邮箱密码
		mailInfo.setFromAddress("itfather@1b23.com");
		mailInfo.setToAddress(tomail);
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		if ("1".equals(type)) {
			return sms.sendTextMail(mailInfo);// 发送文体格式
		} else if ("2".equals(type)) {
			return sms.sendHtmlMail(mailInfo);// 发送html格式
		}
		return false;
	}

	/**
	 * @param smtp
	 *            邮件服务器
	 * @param port
	 *            端口
	 * @param email
	 *            本邮箱账号
	 * @param paw
	 *            本邮箱密码
	 * @param toEmail
	 *            对方箱账号
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param type
	 *            1：文本格式;2：HTML格式
	 */
	public static void sendEmail(String smtp, String port, String email, String paw, String[] toEmail, String title,
			String content, String type) throws Exception {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo(smtp, Integer.parseInt(port), email, paw, true);
		mailInfo.setFromAddress(email);
		mailInfo.setToAddress(toEmail);
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		if ("1".equals(type)) {
			sms.sendTextMail(mailInfo);
		} else {
			sms.sendHtmlMail(mailInfo);
		}
	}

	/**
	 * @param mailInfo
	 *            本邮箱配置
	 * @param type
	 *            1：文本格式;2：HTML格式
	 */
	public static void sendEmail(MailSenderInfo mailInfo, String type) throws Exception {

		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		if ("1".equals(type)) {
			sms.sendTextMail(mailInfo);
		} else {
			sms.sendHtmlMail(mailInfo);
		}

	}

	public static void main(String[] args) throws Exception {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(new String[] { "149156999@qq.com", "wzx010901@163.com" });
		mailInfo.setSubject("设置邮箱标题(添加附件测试)");
		mailInfo.setContent("设置邮箱内容");
		String[] s = { "E:/Program Files (x86)/Tencent/Tencent Files/149156999/Image/a.gif",
				"E:/Program Files (x86)/Tencent/Tencent Files/149156999/Image/b.gif" };
		mailInfo.setAttachFileNames(s);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		// sms.sendTextMail(mailInfo);// 发送文体格式
		sms.sendHtmlMail(mailInfo);// 发送html格式
	}

}
