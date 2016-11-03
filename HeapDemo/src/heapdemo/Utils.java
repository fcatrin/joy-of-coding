package heapdemo;

import java.util.Scanner;

public class Utils {
	private static final long DUMP_PERIOD = 4000;
	public static final int LINES = 1000000;
	public static final int CHARS = 16;
	
	private Utils(){};
	
	public static void waitForKey() {
		Scanner keyboard = new Scanner(System.in);
		keyboard.next();
		keyboard.close();
	}
	
	private static long lastDumpTime = 0;
	private static long startDumpTime = 0;
	public static void dumpStats( int index) {
		long t = System.currentTimeMillis();
		if (t-lastDumpTime < DUMP_PERIOD) return;
		lastDumpTime = t;
		if (startDumpTime == 0) startDumpTime = t;
		
		long sec = (t - startDumpTime) / 1000;
		String tp = sec == 0 ? "": ((float) index / sec) + "[loops/sec]";
		
		System.out.println("----- " + index + " loops ----- " + tp);
	}
	
	public static char getRandomChar() {
		return (char)(Math.random()*26 + 'a');
	}
}
