package xtvapps.iproc;

import java.io.File;

public class Processor {
	
	/*
	 * cargar imagen en java
	 * pasar pixeles a C mediante la llamada a "process"
	 * guardar pixeles como nueva imagen
	 */
	
	public static void main(String args[]) throws Exception {
		Image image = new Image();
		image.load(new File("media/input.jpg"));
		NativeInterface.setMatrix(new float[] {
				2, 2, 2,
				2, 10, 2,
				2, 2, 2
		});
		for(int n=0; n<1; n++) {
			NativeInterface.process(image.getWidth(), image.getHeight(), image.getRaw());
		}
		image.save(new File("media/output.jpg"));
	}
}
