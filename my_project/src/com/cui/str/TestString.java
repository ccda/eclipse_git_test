package com.cui.str;

import java.util.HashMap;
import java.util.Map;

public class TestString {
	
	public static void main(String[] args) {
		String line = "���֤1121313213123 ������12131231312312233";
		Map<String,String> resultMap = saveMapResult(line);
		for(String str:resultMap.keySet()) {
			System.out.println(resultMap.get(str));
		}
	}
	
	public static Map<String,String> saveMapResult(String line) {
		Map<String,String> resultMap = new HashMap<String,String>();
		if(line!=null) {
			int sIndex = line.indexOf("���֤");
			int gIndex = line.indexOf("������");
			System.out.println(sIndex+"=="+gIndex);
			String s = line.substring(sIndex+3, gIndex);
			String g = line.substring(gIndex+3, line.length()-1);
			resultMap.put("���֤", s.trim());
			resultMap.put("������", g.trim());
		}
		return resultMap;
	}

}
