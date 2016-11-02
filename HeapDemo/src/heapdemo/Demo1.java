package heapdemo;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {

	/*
	 * list:
	 * index 0 : char[]
	 * index 1 : char[]
	 * index n : char[]
	 * 
	 */
	
	public static void main(String[] args) {
		Utils.waitForKey();
		
		List<char[]> list = new ArrayList<char[]>();
		while (true) {
			char[] buffer = new char[Utils.CHARS];
			for(int i=0; i< buffer.length; i++) {
				buffer[i] = Utils.getRandomChar();
			}
			list.add(buffer);
			
			Utils.dumpStats(list.size());
		}
		
	}

}
