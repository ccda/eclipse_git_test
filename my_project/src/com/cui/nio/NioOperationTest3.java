package com.cui.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class NioOperationTest3 {
	
	public static void main(String[] args) throws IOException {
		
		List<String> fileList = getFiles("E:\\appFile");
		FileInputStream fis = null;
		int bufSize = 1024;
		for(int i=0;i<fileList.size();i++) {
			fis = new FileInputStream(new File(fileList.get(i)));
			FileChannel fc = fis.getChannel();
			ByteBuffer bf = ByteBuffer.allocate(bufSize);
			while(fc.read(bf)!=-1) {
				readFileByLine(bufSize, fc, bf);
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
		
		
		public static void readFileByLine(int bufSize, FileChannel fcin,
				ByteBuffer rBuffer) {
			String enter = "\n";
			List<String> dataList = new ArrayList<String>();//存储读取的每行数据
			byte[] lineByte = new byte[0];
			String encode = "GBK";
//			String encode = "UTF-8";
			try {
				//temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
				//并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
				byte[] temp = new byte[0];
				//while (fcin.read(rBuffer) != -1) {//fcin.read(rBuffer)：从文件管道读取内容到缓冲区(rBuffer)
					int rSize = rBuffer.position();//读取结束后的位置，相当于读取的长度
					byte[] bs = new byte[rSize];//用来存放读取的内容的数组
					rBuffer.rewind();//将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
					rBuffer.get(bs);//相当于rBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
					rBuffer.clear();
					
					int startNum = 0;
					int LF = 10;//换行符
					int CR = 13;//回车符
					boolean hasLF = false;//是否有换行符
					for(int i = 0; i < rSize; i++){
						if(bs[i] == LF){
							hasLF = true;
							int tempNum = temp.length;
							int lineNum = i - startNum;
							lineByte = new byte[tempNum + lineNum];//数组大小已经去掉换行符
							
							System.arraycopy(temp, 0, lineByte, 0, tempNum);//填充了lineByte[0]~lineByte[tempNum-1]
							temp = new byte[0];
							System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]
							
							String line = new String(lineByte, 0, lineByte.length, encode);//一行完整的字符串(过滤了换行和回车)
							dataList.add(line);
//							System.out.println(line);
							System.out.println(line);
							//writeFileByLine(fcout, wBuffer, line + enter);
							
							//过滤回车符和换行符
							if(i + 1 < rSize && bs[i + 1] == CR){
								startNum = i + 2;
							}else{
								startNum = i + 1;
							}
							
						}
					}
					if(hasLF){
						temp = new byte[bs.length - startNum];
						System.arraycopy(bs, startNum, temp, 0, temp.length);
					}else{//兼容单次读取的内容不足一行的情况
						byte[] toTemp = new byte[temp.length + bs.length];
						System.arraycopy(temp, 0, toTemp, 0, temp.length);
						System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
						temp = toTemp;
					}
				//}
				if(temp != null && temp.length > 0){//兼容文件最后一行没有换行的情况
					String line = new String(temp, 0, temp.length, encode);
					dataList.add(line);
					System.out.println(line);
					//writeFileByLine(fcout, wBuffer, line + enter);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 

		}
		
		
}
