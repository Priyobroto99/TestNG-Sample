package tst;

import org.testng.annotations.Test;

public class RunnerParallel {
	
	@Test
	public void method1() {
		System.out.println("__________________");
		System.out.println("Running method 1");	
		doingSomething(2);
		System.out.println("Method 1 Done");
		System.out.println("__________________");

	}
	@Test
	public void method2() {
		System.out.println("__________________");
		System.out.println("Running method 2");	
		doingSomething(5);
		System.out.println("Method 2 Done");
		System.out.println("__________________");
		
	}
	@Test
	public void method3() {
		System.out.println("__________________");
		System.out.println("Running method 3");	
		doingSomething(3);
		System.out.println("Method 3 Done");
		System.out.println("__________________");
		
	}
	
	void doingSomething(int i) {
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
