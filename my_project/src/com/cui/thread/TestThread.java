package com.cui.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestThread {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Callable<Integer> c = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("call==>"+Thread.currentThread().getName());
				print();
				return 11;
			}
		};
		for(int i=0;i<10;i++) {
			FutureTask<Integer> ft = new FutureTask<Integer>(c);
			new Thread(ft).start();
			System.out.println(ft.get());
		}
	}
	
	public static void print() {
		System.out.println(Thread.currentThread().getName());
		System.out.println("zhang");
	}
}
