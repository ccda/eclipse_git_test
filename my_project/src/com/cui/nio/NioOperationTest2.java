package com.cui.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class NioOperationTest2 {
	
	public static void main(String[] args) throws IOException {
		
		List<String> fileList = getFiles("E:\\appFile");
		int bufSize = 1024;
		FileInputStream fis = null;
		for(int i=0;i<fileList.size();i++) {
			fis = new FileInputStream(fileList.get(i));
			FileChannel fc = fis.getChannel();
			ByteBuffer bf = ByteBuffer.allocate(bufSize);
			while(fc.read(bf)!=-1) {
				bf.flip();
				System.out.println(new String(bf.array()));
				bf.clear();
			}
		}
		fis.close();
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
		
		
}
