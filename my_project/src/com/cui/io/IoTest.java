package com.cui.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class IoTest {
	
	public static void main(String[] args) {
		ArrayList<String> fileList = getFiles("E:\\\\appFile");
		
		for(int i = 0;i<fileList.size();i++) {
			readFile(new File(fileList.get(i)));
		}
	}
	
	
	public static void readFile(File file) {
		InputStreamReader isr = null;
		BufferedReader bufferedReader = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file),"GBK");
			bufferedReader = new BufferedReader(isr);
			String lineContent = null;
			while(StringUtils.isNotEmpty(lineContent=bufferedReader.readLine())) {
				System.out.println(lineContent);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedReader.close();
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

}
