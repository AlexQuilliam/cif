package cif.convenience;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import cif.backend.PixelDataObject;
import cif.backend.PrimaryCompressor;
import cif.exceptions.InvalidUnitCodeException;

public final class FileUtils {
	public static class FileReader {
		public static enum ReadAs {
			PLAINTEXT, //returns: String
			PIXELDATA, //returns: int[][]
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
			PLAINTEXT, //param: String
			PIXELDATA, //param: int[][]
			PNG, //param: BufferedImage or PixelData
			JPG, //param: BufferedImage or PixelData
			CIF //param: BufferedImage or PixelData
		}
		
		public static void write(String filePath, Object data, WriteAs type) {
			try {
				switch (type) {
					case PLAINTEXT:
						Files.write(Paths.get(filePath), ((String) data).getBytes());
						break;
					case PIXELDATA:
						String pixelData = "";
						for(int i = 0; i < ((int[][]) data).length; i++) {
							for(int j = 0; j < ((int[][]) data)[0].length; j++) {
								pixelData += Integer.toString(((int[][]) data)[i][j]);
							}
						}
						
						Files.write(Paths.get(filePath), ((String) pixelData).getBytes());
						break;
					case PNG:
						writeAsImage("PNG", data, filePath, Flip.Vertically, 90);
						break;
					case JPG:
						writeAsImage("JPG", data, filePath, Flip.Vertically, 90);
						break;
					case CIF:
						if(data instanceof BufferedImage) {
							new PrimaryCompressor(new PixelDataObject((BufferedImage) data).getPixelData(), filePath, 100);
						}else if(data instanceof int[][]) {
							new PrimaryCompressor((int[][]) data, filePath, 100);
						}
						break;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private static enum Flip {
			Horizontally,
			Vertically,
			None
		}
		
		private static BufferedImage flip(Flip flip, BufferedImage image) {
			AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			
			if(flip == Flip.Horizontally) {
				affineTransform = AffineTransform.getScaleInstance(-1, 1);
				affineTransform.translate(-(image.getWidth(null)), 0);
				affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = affineTransformOp.filter(image, null);
				
				return image;
			}else {
				affineTransform = AffineTransform.getScaleInstance(1, -1);
				affineTransform.translate(0, -(image.getHeight(null)));
				affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = affineTransformOp.filter(image, null);
				
				return image;
			}
		}
		
		//only rotate 90 degrees to the right; TODO: make it more complete
		private static BufferedImage rotate(int degrees, BufferedImage image) {
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
		
		private static void writeAsImage(String type, Object data, String filePath, Flip flip, int rotate) {
			try {
				if (data instanceof BufferedImage) {
					if(flip != Flip.None) {
						data = flip(flip, (BufferedImage) data);
					}
					
					if(rotate != 0 && rotate != 360) {
						data = rotate(rotate, (BufferedImage) data);
					}
					
					ImageIO.write((BufferedImage) data, "PNG", new File(filePath));
				}else if (data instanceof int[][]) {
					BufferedImage image = convertPixelData((int[][]) data);
					if(flip != Flip.None) {
						image = flip(flip, image);
					}
					
					if(rotate != 0 && rotate != 360) {
						image = rotate(rotate, image);
					}
					
					ImageIO.write(image, "PNG", new File(filePath));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private static BufferedImage convertPixelData(int[][] pixelData) {
			BufferedImage image = new BufferedImage(pixelData.length, pixelData[0].length, BufferedImage.TYPE_INT_ARGB);
			
			int[] outputImagePixelData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		    int width = image.getWidth();
		    int height = image.getHeight();

		    for (int y = 0; y < height; y++) {
		        for (int x = 0; x < width; x++) {
		            outputImagePixelData[y * width + x] = pixelData[x][y];
		        }
		    }

		    return image;
		}
	}
	
	public static double getFileSize(String filePath, Unit unit) {
		try {
			double fileSize = Files.walk(new File(filePath).toPath()).map(f -> f.toFile()).filter(f -> f.isFile()).mapToLong(f -> f.length()).sum();
				
			if(unit == Unit.BYTES) {
				return fileSize;
			}else if(unit == Unit.KILOBYTES) {
				return fileSize / 1000;
			}else if(unit == Unit.MEGABYTES) {
				return fileSize / 1000000;
			}else {
				throw new InvalidUnitCodeException(unit);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}