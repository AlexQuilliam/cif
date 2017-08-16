package cif.convenience;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import cif.core.PixelDataObject;
import cif.exceptions.InvalidTypeException;
import cif.exceptions.InvalidUnitCodeException;

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
					ImageIO.write(ImageUtils.adjust((BufferedImage) data), "PNG", new File(filePath));
				}else if (data instanceof List<?>) {
					@SuppressWarnings("unchecked")
					BufferedImage image = ImageUtils.convertPixelData((List<List<Integer>>) data);
					
					ImageIO.write(ImageUtils.adjust(image), "PNG", new File(filePath));
				}else {
					throw new InvalidTypeException();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
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