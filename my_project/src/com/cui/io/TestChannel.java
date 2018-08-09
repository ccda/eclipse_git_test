package com.cui.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class TestChannel {
	
	public static void main(String[] args) throws SftpException {
		ChannelSftp sftp = null;
		Session sshSession = null;
		Channel channel = null;
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession("appuser","123.56.141.240",22);
			sshSession.setPassword("3v8fVcoq");
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			try {
				sftp.cd("/app/appuser/apps/upload/");
			}catch(SftpException e) {
				if (e.id == 2) { // No such file
					sftp.mkdir("/app/appuser/apps/upload/");
					sftp.cd("/app/appuser/apps/upload/");
				}
			}
			
			uploadUrlFile(sftp, obtainSoMap());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sftp.disconnect();
			channel.disconnect();
			sshSession.disconnect();
		}
		
	}
	
	private static Map<String,Object> obtainSoMap(){
		Map<String,Object> infoMap = new HashMap<String, Object>();
		infoMap.put("remoteAddress", "/app/appuser/apps/upload/");
		infoMap.put("userName", "appuser");
		infoMap.put("password", "3v8fVcoq");
		infoMap.put("port", 22);
		infoMap.put("ip", "123.56.141.240");
		infoMap.put("fileName","cc.pdf");
		infoMap.put("fileUrl", "http://47.94.40.127:7006/ossFile/download.do?fileId=5a62ba81-7e37-4601-a890-08c4c12b8fc7");
		infoMap.put("batchNum", 12121212);
		infoMap.put("appId", 12121);
		return infoMap;
	}
	
	
	
	public static void uploadUrlFile(ChannelSftp sftp,Map<String, Object> infoMap){
    	try{
    		String remoteAddress = "";
    		if(infoMap.get("fileName") != null && (infoMap.get("fileName")+"").contains(".")){
    			remoteAddress = infoMap.get("remoteAddress")+""+infoMap.get("batchNum")+"/"+infoMap.get("appId")+"/"+infoMap.get("fileName");
    		}else{
    			remoteAddress = infoMap.get("remoteAddress")+""+infoMap.get("batchNum")+"/"+infoMap.get("appId")+"/"+infoMap.get("fileName")+getUrlFileType(infoMap.get("fileUrl")+"");
    		}
    		OutputStream out = sftp.put("/app/appuser/apps/upload/cc.pdf", ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
    		byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
    		int read;
    		
    		URL url = new URL(infoMap.get("fileUrl")+"");    
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			httpConn.connect();    
			InputStream is = httpConn.getInputStream();
			//sftp.cd("/app/appuser/apps/upload");
			//sftp.put(is, infoMap.get("fileName")+"");
			if (out != null) {
    			
    			do {
    				read = is.read(buff, 0, buff.length);
    				if (read > 0) {
    					out.write(buff, 0, read);
    				}
    				out.flush();
    			} while (read >= 0);
    		}
    	}catch(Exception e){
    	}
    }
	
	/**
	 * 获取链接地址文件的类型
	 * @param fileUrl
	 * @return
	 * @throws Exception
	 */
    public static String getUrlFileType(String fileUrl) throws Exception    
    {    
    	String str = "";
    	if(fileUrl != null && !"".equals(fileUrl)){
    		URL url = new URL(fileUrl);    
    		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();    
    		httpConn.connect();    
    		InputStream cin = httpConn.getInputStream();    
    		ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
    		byte[] buffer = new byte[1024];    
    		outStream.write(buffer, 0, cin.read(buffer));    
    		cin.close();    
    		byte[] fileData = outStream.toByteArray();
    		//截取流的前十个，主要是为了解决在不知道文件类型的情况，通过前十位来判断文件的类型
    		fileData =Arrays.copyOfRange(fileData, 0, 10);
    		outStream.close();   
    		byte[] pdfByte = {37, 80, 68, 70, 45, 49, 46, 52, 10, 37};
    		byte[] jpegByte = {-1, -40, -1, -31, 47, -2, 69, 120, 105, 102};
    		byte[] jpgByte = {-1, -40, -1, -32, 0, 16, 74, 70, 73, 70};
    		byte[] pngByte = {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0};
    		if(Arrays.equals(pdfByte, fileData)){
    			str = ".pdf";
    		}else if(Arrays.equals(jpgByte, fileData) || Arrays.equals(jpegByte, fileData)){
    			str = ".jpeg";
    		}else if(Arrays.equals(pngByte, fileData)){
    			str = ".png";
    		}
    		System.out.println("获取的合同文件类型是："+str+"，截取流的前十个是："+fileData);
    	}
        return str;    
    }
	
	

}
