package com.fh.util.ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.fh.util.Logger;

/**
 * FTP上传文件
 */
public class FtpUtil {
	private static FTPClient ftpClient;
	static Logger logger = Logger.getLogger(FtpUtil.class);

	/**
	 * 取ftpClient线程
	 * 
	 * @return
	 */
	public static FTPClient getFTPClient() {
		return ftpClient;
	}

	public static void setFTPClient(FTPClient ftpClient) {
		FtpUtil.ftpClient = ftpClient;
	}

	/**
	 * FTPClient连接
	 * 
	 * @param hostname
	 *            服务器地址
	 * @param port
	 *            服务器端口
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	private static boolean connectServer(String hostname, int port) throws SocketException, IOException {
		ftpClient.connect(hostname, port);
		boolean result = ftpClient.isConnected();
		if (!result) {
			logger.info("ftp连接失败,hostname:" + hostname);
		}
		return result;
	}

	/**
	 * FTPClient登录
	 * 
	 * @param username
	 *            服务器用户名
	 * @param password
	 *            服务器密码
	 * @return
	 * @throws IOException
	 */
	private static boolean login(String username, String password) throws IOException {
		boolean result = false;
		try {
			result = ftpClient.login(username, password);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}

		if (!result) {
			logger.info("ftp登录失败,账号或密码错误,username:" + username);
		}
		return result;
	}

	/**
	 * 判断FTPClient是否连接
	 * 
	 * @param hostname
	 *            服务器地址
	 * @param port
	 *            服务器端口
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * 
	 *             public static boolean isConnect() { boolean result = false;
	 *             if(ftpClient!=null) { result = ftpClient.isConnected(); }
	 *             return result; }
	 */

	/**
	 * 登录
	 * 
	 * @throws Exception
	 */
	public static boolean login(String hostname, int port, String username, String password, String encoding)
			throws Exception {
		try {
			
			if (ftpClient == null) {
				ftpClient = new FTPClient();
			}
			ftpClient.setControlEncoding(encoding);
			// ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
			// ftpClient.setConnectTimeout(5000);
			boolean isLogin = true;
			isLogin = isLogin && connectServer(hostname,port);
			isLogin = isLogin && login(username,password);
			return isLogin;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 手动断开连接
	 * 
	 * @throws IOException
	 */
	public static void disposeFtpClient() throws IOException {
		if (ftpClient != null) {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		}
		ftpClient = null;
	}

	/**
	 * 逐级检查目录是否存在,如果不存在则创建
	 * 
	 * @param fullPath
	 *            ,完整的路径
	 * @return
	 */
	public static boolean makeResumeDir(String fullPath) {
		try {
			StringTokenizer s = new StringTokenizer(fullPath, "/");
			String pathName = "";
			while (s.hasMoreElements()) {
				pathName = pathName + "/" + (String) s.nextElement();
				try {
					ftpClient.makeDirectory(pathName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 创建目录,如果存在则不做操作
	 * 
	 * @param fullPath
	 *            ,完整的路径
	 * @return
	 */
	public static boolean makeDir(String fullPath) {
		try {
			ftpClient.makeDirectory(fullPath);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 通过输入流上传本地文件
	 * 
	 * @param fileName
	 *            上传到服务器后文件名称
	 * @param inputStream
	 *            本地文件流
	 * @param remotePath
	 *            服务器路径
	 * @return 返回上传成功标志true
	 * @throws SocketException
	 * @throws IOException
	 */
	public static boolean uploadFile(String fileName, InputStream inputStream, String remotePath)
			throws SocketException, IOException {
		boolean flag = false;
		// if (connectServer()&& login()) {
		// if(ftpClient==null) {
		// init();
		// }
		ftpClient.setControlEncoding("GBK");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 使用二进制流上传文件
		ftpClient.enterLocalPassiveMode();
		makeDir(remotePath);
		ftpClient.changeWorkingDirectory(remotePath); // 跳转到服务器对应目录
		flag = ftpClient.storeFile(fileName, inputStream);
		/*
		 * if (flag) { ftpClient.rename(fileName + ".bak", fileName); // 修改文件名称
		 * }
		 */
		// }
		return flag;
	}

	/**
	 * 上传多附件，传入FTPClient
	 * 
	 * @param fileName
	 * @param inputStream
	 * @param remotePath
	 * @param ftpClient
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 *             xxxx
	 */
	public static boolean uploadFile(String fileName, InputStream inputStream, String remotePath, FTPClient ftpClient,FtpInfo ftpInfo)
			throws SocketException, IOException {
		boolean flag = false;
		try {
			if (ftpClient == null) {
				ftpClient = new FTPClient();
			}
			ftpClient.connect(ftpInfo.getHostName(), ftpInfo.getPort());
			boolean result = ftpClient.isConnected();
			if (!result) {
				logger.info("ftp连接失败,hostname:" + ftpInfo.getHostName());
			} else {
				result = ftpClient.login(ftpInfo.getUsername(), ftpInfo.getPassword());
			}
			if (result == false) {
				return false;
			}
			ftpClient.setControlEncoding(ftpInfo.getEncoding());
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 使用二进制流上传文件
			ftpClient.enterLocalPassiveMode();
			ftpClient.setConnectTimeout(5000);
			StringTokenizer s = new StringTokenizer(remotePath, "" + "/");
			String pathName = "";
			while (s.hasMoreElements()) {
				pathName = pathName + "/" + (String) s.nextElement();
				try {
					ftpClient.makeDirectory(pathName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			logger.info("remotePath = " + remotePath);
			ftpClient.changeWorkingDirectory(remotePath); // 跳转到服务器对应目录
			flag = ftpClient.storeFile(fileName, inputStream);
			logger.info("flag = " + flag);
		} catch (Exception e) {
			logger.info("附件上传失败：" + e.getMessage());
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 删除附件
	 * 
	 * @param fileName
	 * @param inputStream
	 * @param remotePath
	 * @param ftpClient
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public static boolean removeFile(String remotePath,FtpInfo ftpInfo) throws SocketException, IOException {
		boolean flag = false;
		try {
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect(ftpInfo.getHostName(), ftpInfo.getPort());
			boolean result = ftpClient.isConnected();
			if (!result) {
				logger.info("ftp连接失败,hostname:" + ftpInfo.getHostName());
			} else {
				result = ftpClient.login(ftpInfo.getUsername(), ftpInfo.getPassword());
			}
			ftpClient.setControlEncoding(ftpInfo.getEncoding());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setConnectTimeout(5000);
			logger.info("remotePath:" + remotePath);
			flag = ftpClient.deleteFile(remotePath);
			logger.info("flag:" + flag);
		} catch (Exception e) {
			logger.info("附件删除失败：");
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 本地文件上传到ftp
	 * 
	 * @param local
	 *            本地文件
	 * @param remote
	 *            ftp目录
	 * @param fileName
	 *            保存的文件名
	 * @return
	 * @throws IOException
	 * @throws SocketException
	 * @throws IOException
	 */
	public static boolean uploadFile(String local, String remote, String fileName) {
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			makeDir(remote);
			ftpClient.changeWorkingDirectory(remote);
			InputStream inputStream = new FileInputStream(local);
			ftpClient.storeFile(fileName, inputStream);
			return true;
		} catch (SocketException sex) {
			sex.printStackTrace();
			return false;
		} catch (IOException iex) {
			iex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 本地文件上传到ftp,文件名由取本地文件名
	 * 
	 * @param local
	 *            ,本地目录
	 * @param remote
	 *            ,远程目录,"/"开始
	 * @return
	 */
	public static boolean uploadFile(String local, String remote) {
		local = local.replace("/", "\\");
		String fileName = local.substring(local.lastIndexOf("\\") + 1);
		return uploadFile(local, remote, fileName);
	}

	/**
	 * 从ftp下载文件到本地
	 * 
	 * @param remote
	 * @param local
	 */
	public static void downloadFile(String remote, String local) throws IOException {
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		FileOutputStream fos = new FileOutputStream(local);
		ftpClient.retrieveFile(remote, fos);
		fos.close();
	}

	public static InputStream downloadFile(String remote) throws IOException {
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		return ftpClient.retrieveFileStream(remote);
	}

	/**
	 * 启动ftp服务
	 * 
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @param encoding
	 */
	public static boolean init(String[] hostname, String[] port, String[] username, String[] password,
			String[] encoding) {
		try {
			boolean flag = false;
			int i = 0;
			while (!flag && i < hostname.length) {
				flag = login(hostname[i], Integer.parseInt(port[i].trim()), username[i], password[i], encoding[i]);
				i++;
			}
			return flag;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 取ftp文件大小
	 * 
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public static long getFileSize(String path, String fileName) throws IOException {
		ftpClient.changeWorkingDirectory(path);
		FTPFile[] ftpFile = ftpClient.listFiles(fileName);
		if (ftpFile != null && ftpFile.length > 0) {
			return ftpFile[0].getSize();
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		// FtpUtils.init();
		FtpUtil.uploadFile("c:/bar.emf", "E:/itsm/ftpdata/201105");
		//
		//
		// InputStream is =
		// FtpUtils.downloadFile("E:/itsm/ftpdata/201105/bar.emf");
		// InputStream is =
		// FtpUtils.downloadFile("E:/itsm/ftpdata/201105/11175919_crm.xls");
		// FileOutputStream os = new FileOutputStream("c:/testcrm.emf");
		// byte[] ss = new byte[1024];
		// while(is.read(ss) != -1) {
		// os.write(ss);
		// }
		// logger.debug("size:"+is.available());
		// is.close();
		// long l = FtpUtils.getFileSize("/home/itsm/upload/201001/",
		// "ecTable 使用.pdf");
		// logger.info(l);
	}
}