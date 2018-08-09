package com.cui.io;

import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;

public class IoTest2 {
	
	 public SFTPChannel getSFTPChannel() {
	        return new SFTPChannel();
	    }
	
	public static void main(String[] args) throws Exception {
		IoTest2 test = new IoTest2();
		 Map<String, String> sftpDetails = new HashMap<String, String>();
	        // 设置主机ip，端口，用户名，密码
	        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "123.56.141.240");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "appuser");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "3v8fVcoq");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
	        
	        String src = "E:\\玖富资料\\需求文档\\SVN及Redmine权限申请指导书.pdf"; // 本地文件名
	        String dst = "/app/appuser/apps/upload/cc.pdf"; // 目标文件名
	              
	        SFTPChannel channel = test.getSFTPChannel();
	        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
	        
	        chSftp.put(src, dst, ChannelSftp.OVERWRITE);
	        chSftp.quit();
	        channel.closeChannel();
	}
	
}

class SFTPConstants {
    public static final String SFTP_REQ_HOST = "host";
    public static final String SFTP_REQ_PORT = "port";
    public static final String SFTP_REQ_USERNAME = "username";
    public static final String SFTP_REQ_PASSWORD = "password";
    public static final int SFTP_DEFAULT_PORT = 22;
    public static final String SFTP_REQ_LOC = "location";
}
