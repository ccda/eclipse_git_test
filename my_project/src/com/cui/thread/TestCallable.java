package com.cui.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestCallable {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Callable<Integer> c = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("call==>"+Thread.currentThread().getName());
				print();
				return 11;
			}
		};
		FutureTask<Integer> ft = new FutureTask<Integer>(c);
		
		for(int i=0;i<10;i++) {
			new Thread(ft).start();
			System.out.println(ft.get());
		}
	}
	
	public static void print() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("zhang");
	}
	

}
