package heapdemo;

public class Demo2 {
	static char[][] data = new char[Utils.LINES][Utils.CHARS];

	public static void main(String[] args) {
		Utils.waitForKey();
		
		int index = 0;
		while (true) {
			char[] buffer = new char[Utils.CHARS];
			for(int i=0; i< buffer.length; i++) {
				buffer[i] = Utils.getRandomChar();
			}
			data[index % Utils.LINES] = buffer;
			Utils.dumpStats(index);
			
			index++;
		}
	}

}
