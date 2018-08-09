package com.cui.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

public class NioOperationTest5 {
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> fileList = getFiles("E:\\appFile");
		
		for(int i=0;i<fileList.size();i++) {
			readFile(new File(fileList.get(i)));
		}
		
	}
	
	/**
	 * 读取文件夹下的所有文件
	 * @param path
	 * @return
	 */
	public static ArrayList<String> getFiles(String path){
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	              System.out.println("文     件：" + tempList[i]);
	            files.add(tempList[i].toString());
	        }
	        if (tempList[i].isDirectory()) {
	              System.out.println("文件夹：" + tempList[i]);
	        }
	    }
	    return files;
	}
	
	
	public static void readFile(File file) throws IOException {
		Map<String,String> resultMap = new HashMap<String,String>();
		int bufSize = 1200;
		FileInputStream fis = new FileInputStream(file);
		FileChannel fcin = fis.getChannel();
		ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
		String enterStr = "\n";
 
		try {
			StringBuffer strBuf = new StringBuffer("");
			int lineNum = 0;
			while (fcin.read(rBuffer) != -1) {
				int rSize = rBuffer.position();
				rBuffer.clear();
				String tempString = new String(rBuffer.array(), 0, rSize);
				/*if(fis.available() ==0){//最后一行，加入"\n分割符"
					tempString+="\n";
				}*/
				int fromIndex = 0;
				int endIndex = 0;
 
				while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
					String line = tempString.substring(fromIndex, endIndex);
					line = new String(strBuf.toString() + line);
					System.out.println("lineNum ="+ ++lineNum);
					System.out.println(line+"===");
					saveMapResult(line,resultMap);
					if(resultMap.size()==1000||lineNum==1) {
						print(resultMap);
						resultMap.clear();
					}
					strBuf.delete(0, strBuf.length());
					fromIndex = endIndex + 1;
 
				}
 
				if (rSize > tempString.length()) {
					System.out.println("**");
					strBuf.append(tempString.substring(fromIndex, tempString.length()));
					
				} else {
					System.out.println(tempString+"&&");
					strBuf.append(tempString.substring(fromIndex, rSize));
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			fcin.close();
			fis.close();
		}
		System.out.print("OK!!!");
		
	}
	
	public static void print(Map<String,String> map) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for(int i = 0;i<map.size();i++) {
			
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					for(String str:map.keySet()) {
						System.out.println(map.get(str)+"threadName"+Thread.currentThread().getName());
					}
				}
			});
			
		}
	}
	
	
	
	public static void saveMapResult(String line,Map<String,String> resultMap) {
		if(StringUtils.isNotEmpty(line)&&line.contains("sfz")) {
			int sIndex = line.indexOf("sfz");
			int gIndex = line.indexOf("gdh");
			System.out.println(sIndex+"=="+gIndex);
			String s = line.substring(sIndex+3, gIndex);
			String g = line.substring(gIndex+3, line.length()-1);
			resultMap.put("sfz", s.trim());
			resultMap.put("gdh", g.trim());
		}
	}
	
		
}
