package xtvapps.iproc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private int[] raw;
	private int width;
	private int height;
	
	public void load(File file) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
		width = originalImage.getWidth();
		height = originalImage.getHeight();
		
		raw = new int[width*height];
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				int index = y*width + x;
				raw[index] = originalImage.getRGB(x, y);
			}
		}
		
		System.out.println("w:" + width + ",h:" + height + ", len:" + raw.length);
	}
	
	public void save(File outputfile) throws IOException {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				int index = y*width + x;
				img.setRGB(x, y, raw[index]);
			}
		}
	    ImageIO.write(img,"jpg",outputfile);
	}

	public int[] getRaw() {
		return raw;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
}
