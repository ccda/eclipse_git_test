package com.cui.str;

import java.util.HashMap;
import java.util.Map;

public class TestString {
	
	public static void main(String[] args) {
		String line = "身份证1121313213123 工单号12131231312312233";
		Map<String,String> resultMap = saveMapResult(line);
		for(String str:resultMap.keySet()) {
			System.out.println(resultMap.get(str));
		}
	}
	
	public static Map<String,String> saveMapResult(String line) {
		Map<String,String> resultMap = new HashMap<String,String>();
		if(line!=null) {
			int sIndex = line.indexOf("身份证");
			int gIndex = line.indexOf("工单号");
			System.out.println(sIndex+"=="+gIndex);
			String s = line.substring(sIndex+3, gIndex);
			String g = line.substring(gIndex+3, line.length()-1);
			resultMap.put("身份证", s.trim());
			resultMap.put("工单号", g.trim());
		}
		return resultMap;
	}

}
