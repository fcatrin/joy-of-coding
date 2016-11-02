package heapdemo;

public class Demo3 {

	static char[][] data = new char[Utils.LINES][Utils.CHARS];

	public static void main(String[] args) {

		for(int i=0; i<Utils.LINES; i++) {
			data[i] = new char[Utils.CHARS];
		}
		
		Utils.waitForKey();
		
		int index = 0;
		while (true) {
			char buffer[] = data[index % Utils.LINES];
			
			for(int i=0; i<Utils.CHARS; i++) {
				buffer[i] = Utils.getRandomChar();
			}
			
			Utils.dumpStats(index);
			index++;
		}
		
	}

}
