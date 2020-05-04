package com.java.main.thread;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiThreadsTest {

	public static void main(String[] args) {
		LocalDateTime start = LocalDateTime.now();
		Info info = new Info();

		// Add the setInfo function to the Runnable interface
		Runnable task1 = () -> {
			info.setInfo1(fetchInfo1());
		};

		Runnable task2 = () -> {
			info.setInfo2(fetchInfo2());
		};

		Runnable task4 = () -> {
			LocalDateTime end = LocalDateTime.now();
			Duration duration = Duration.between(start, end);
			assertTrue(duration.getSeconds() < 4.1);
			assertEquals("Info1", info.getInfo1());
			assertEquals("Info2", info.getInfo2());
			assertEquals("Info3", info.getInfo3());
		};

		Runnable task3 = () -> {
			info.setInfo3(fetchInfo3());
			new Thread(task4).start();
		};

		// Create ThreadPoolExecutor with fixed pool size to execute the tasks
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

		// Add task to ThreadPoolExecutor to execute the task parallel
		executor.execute(task1);
		executor.execute(task2);
		executor.execute(task3);

		// Notify the executor to grace shutdown the thread pool
		executor.shutdown();
	}

	private static String fetchInfo1() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Info1";
	}

	private static String fetchInfo2() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Info2";
	}

	private static String fetchInfo3() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Info3";
	}

	static class Info {
		String info1;
		String info2;
		String info3;

		public String getInfo1() {
			return info1;
		}

		public void setInfo1(String info1) {
			this.info1 = info1;
		}

		public String getInfo2() {
			return info2;
		}

		public void setInfo2(String info2) {
			this.info2 = info2;
		}

		public String getInfo3() {
			return info3;
		}

		public void setInfo3(String info3) {
			this.info3 = info3;
		}
	}

}
