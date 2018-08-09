package com.cui.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NioOperationTest {
	
	public static void main(String[] args) throws IOException {
		int bufSize = 1000000;
		File inFile = new File("E://aa.txt");
		File outFile = new File("E://bb.txt");
		Date startDate = new Date();
		FileChannel fcin = new RandomAccessFile(inFile, "r").getChannel();
		ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
		
		
		FileChannel fcout = new RandomAccessFile(outFile, "rws").getChannel();
		ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);
		
		readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);
		Date endDate = new Date();
		
		System.out.print(startDate+"|"+endDate);//����ִ��ʱ��
		if(fcin.isOpen()){
			fcin.close();
		}
		if(fcout.isOpen()){
			fcout.close();
		}

		
		
	}
	
	
	public static void readFileByLine(int bufSize, FileChannel fcin,
			ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer) {
		String enter = "\n";
		List<String> dataList = new ArrayList<String>();//�洢��ȡ��ÿ������
		byte[] lineByte = new byte[0];
		
		String encode = "GBK";
//		String encode = "UTF-8";
		try {
			//temp�������ǰ��̶��ֽڶ�ȡ����һ�ζ�ȡ�У���һ�к����һ�о����ǲ��������У���˶���˱������洢�ϴε����һ�к���εĵ�һ�е����ݣ�
			//����֮���ӳ���ɵ�һ�У��������ֺ��ֱ���ֳ�2���ֽڣ�������ǰת�����ַ��������������
			byte[] temp = new byte[0];
			while (fcin.read(rBuffer) != -1) {//fcin.read(rBuffer)�����ļ��ܵ���ȡ���ݵ�������(rBuffer)
				int rSize = rBuffer.position();//��ȡ�������λ�ã��൱�ڶ�ȡ�ĳ���
				byte[] bs = new byte[rSize];//������Ŷ�ȡ�����ݵ�����
				rBuffer.rewind();//��position���0,����������ض�Buffer�е���������,�˴����������,�޷�ʹ�������get����
				rBuffer.get(bs);//�൱��rBuffer.get(bs,0,bs.length())����position��ʼλ�ÿ�ʼ��Զ�,��bs.length��byte,��д��bs[0]��bs[bs.length-1]������
				rBuffer.clear();
				
				int startNum = 0;
				int LF = 10;//���з�
				int CR = 13;//�س���
				boolean hasLF = false;//�Ƿ��л��з�
				for(int i = 0; i < rSize; i++){
					if(bs[i] == LF){
						hasLF = true;
						int tempNum = temp.length;
						int lineNum = i - startNum;
						lineByte = new byte[tempNum + lineNum];//�����С�Ѿ�ȥ�����з�
						
						System.arraycopy(temp, 0, lineByte, 0, tempNum);//�����lineByte[0]~lineByte[tempNum-1]
						temp = new byte[0];
						System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);//���lineByte[tempNum]~lineByte[tempNum+lineNum-1]
						
						String line = new String(lineByte, 0, lineByte.length, encode);//һ���������ַ���(�����˻��кͻس�)
						dataList.add(line);
//						System.out.println(line);
						writeFileByLine(fcout, wBuffer, line + enter);
						
						//���˻س����ͻ��з�
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
				}else{//���ݵ��ζ�ȡ�����ݲ���һ�е����
					byte[] toTemp = new byte[temp.length + bs.length];
					System.arraycopy(temp, 0, toTemp, 0, temp.length);
					System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
					temp = toTemp;
				}
			}
			if(temp != null && temp.length > 0){//�����ļ����һ��û�л��е����
				String line = new String(temp, 0, temp.length, encode);
				dataList.add(line);
//				System.out.println(line);
				writeFileByLine(fcout, wBuffer, line + enter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
		
		/**
		 * д���ļ���
		 * @param fcout
		 * @param wBuffer
		 * @param line
		 */
		@SuppressWarnings("static-access")
		public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer,
				String line) {
			try {
				fcout.write(wBuffer.wrap(line.getBytes("UTF-8")), fcout.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
