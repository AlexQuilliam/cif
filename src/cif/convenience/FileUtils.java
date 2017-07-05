package cif.convenience;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import cif.core.PixelDataObject;
import cif.exceptions.InvalidTypeException;
import cif.exceptions.InvalidUnitCodeException;

//use only for single threads. for multiple threads, use FileUtilObject
public final class FileUtils {
	public static class FileReader {
		public static enum ReadAs {
			PLAINTEXT, //returns: String
			PIXELDATA, //returns: List<List<Integer>
			BUFFEREDIMAGE //returns: BufferedImage
		}
		
		public static Object read(String filePath, ReadAs type) {
			try {
				switch (type) {
					case PLAINTEXT:
						return new String(Files.readAllBytes(Paths.get(filePath)));
					case PIXELDATA:
						return new PixelDataObject(ImageIO.read(new File(filePath))).getPixelData();
					case BUFFEREDIMAGE:
						return ImageIO.read(new File(filePath));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}
	
	public static class FileWriter {
		public static enum WriteAs {
			PLAINTEXT, //param: String or List<List<Integer>>
			PNG, //param: BufferedImage or List<List<Integer>>
			JPG, //param: BufferedImage or List<List<Integer>>
			CIF //param: BufferedImage or List<List<Integer>>
		}
		
		@SuppressWarnings("unchecked")
		public static void write(String filePath, Object data, WriteAs type) {
			try {
				switch (type) {
					case PLAINTEXT:
						if(data instanceof String) {
							Files.write(Paths.get(filePath), ((String) data).getBytes());
							break;
						}else if(data instanceof List<?>) {
							String compiledString = String.join("", ((List<List<Integer>>) data).stream().flatMap(l -> l.stream().map(i -> i.toString())).collect(Collectors.toList()));
							Files.write(Paths.get(filePath), compiledString.getBytes());
							break;
						}
					case PNG:
						writeAsImage("PNG", data, filePath);
						break;
					case JPG:
						writeAsImage("JPG", data, filePath);
						break;
					case CIF:
						Print.ln("Not yet supported");
						break;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private static void writeAsImage(String type, Object data, String filePath) {
			try {
				if (data instanceof BufferedImage) {
					ImageIO.write(adjust((BufferedImage) data), "PNG", new File(filePath));
				}else if (data instanceof List<?>) {
					@SuppressWarnings("unchecked")
					BufferedImage image = convertPixelData((List<List<Integer>>) data);
					
					ImageIO.write(adjust(image), "PNG", new File(filePath));
				}else {
					throw new InvalidTypeException();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private static BufferedImage convertPixelData(List<List<Integer>> data) {
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
		private static BufferedImage adjust(BufferedImage image) {
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
	
	public static double getFileSize(String filePath, Unit unit) {
		try {
			double fileSize = Files.walk(new File(filePath).toPath()).map(f -> f.toFile()).filter(f -> f.isFile()).mapToLong(f -> f.length()).sum();
			
			switch(unit) {
				case BYTES:
					return fileSize;
				case KILOBYTES:
					return fileSize / 1000;
				case MEGABYTES:
					return fileSize / 1000000;
				default:
					throw new InvalidUnitCodeException(unit);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}