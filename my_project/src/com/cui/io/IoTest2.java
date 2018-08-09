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
	        // ��������ip���˿ڣ��û���������
	        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "123.56.141.240");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "appuser");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "3v8fVcoq");
	        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
	        
	        String src = "E:\\��������\\�����ĵ�\\SVN��RedmineȨ������ָ����.pdf"; // �����ļ���
	        String dst = "/app/appuser/apps/upload/cc.pdf"; // Ŀ���ļ���
	              
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
