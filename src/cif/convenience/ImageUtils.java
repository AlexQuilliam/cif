package cif.convenience;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

public class ImageUtils {
	public static BufferedImage convertPixelData(List<List<Integer>> data) {
		int width = data.size();
	    int height = data.get(0).size();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int[] pixelData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	        	pixelData[y * width + x] = data.get(x).get(y);
	        }
	    }
		
		return image;
	}
	
	//transform vertically (flip), rotate 90 degrees to the right
	public static BufferedImage adjust(BufferedImage image) {
		AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
		affineTransform.translate(0, -(image.getHeight(null)));
		image = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(image, null);
		
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage buffer = new BufferedImage(height, width, image.getType());

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				buffer.setRGB(height - y - 1, x, image.getRGB(x, y));
			}
		}
		
		return buffer;
	}
}
