package com.cui.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool {
	
	public static void main(String[] args) {
		int len = 2;
		List<String> resultList = new ArrayList<String>();
		resultList.add("c1");
		resultList.add("c2");
		resultList.add("c3");
		resultList.add("c4");
		resultList.add("c5");
		resultList.add("c6");
		//ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3,10, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));
		/*threadPool.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("999");
			}
		});*/
		ExecutorService eService = Executors.newFixedThreadPool(3);
		
		/*for(String str:resultList) {
			//System.out.println(str);
			eService.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(str);
				}
			});
		}*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(new Date());
		
		System.out.println();
		
		
		
		
		
		
	}
	

}
