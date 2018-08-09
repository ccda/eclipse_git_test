package com.cui.str;

public class StringTest2 {
	public static void main(String[] args) {
		StringTest2 st = new StringTest2();
		/*String ip= "112";
		st.setStr(ip);
		System.out.println(ip);*/
		
		MyStr ms = new MyStr();
		ms.setAge(11);
		st.setStr2(ms);
		System.out.println(ms.getAge());
	}
	
	
	/*public void setStr(String ip) {
		ip = "11";
	}*/
	
	public void setStr2(MyStr ms) {
		ms.setAge(12);
	}
	
	
	private static final class MyStr{
		int age ;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

}
