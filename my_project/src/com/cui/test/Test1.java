package com.cui.test;

public class Test1 {
	
	public static void main(String[] args) {
		
		System.out.println(switchType("7"));
		
		
		
	}
	
	private static String switchType(String type){
			switch (type) {
				case "5":
					return "CN";
				case "6":
					return "CR";
				default:
					return "";
			}
	} 

}
