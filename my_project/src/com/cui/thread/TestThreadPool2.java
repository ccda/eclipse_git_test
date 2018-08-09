package com.cui.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool2 {
	
	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		TestThreadPool2 t = new TestThreadPool2();
		TestThreadPool2.MyRunnable m = t.new  MyRunnable(); 
		
		for(int i = 0;i<10;i++) {
			executorService.submit(m);
		}
		
		executorService.shutdown();
	}
	
	
	
	
	public static void print() {
		System.out.println(Thread.currentThread().getName());
	}
	
	private class MyRunnable extends Thread{
		public void run() {
			TestThreadPool2.print();
		}
	}
	

}
