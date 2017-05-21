package cif.core;

import cif.convenience.FileUtils;
import cif.convenience.FileUtils.FileReader.ReadAs;

public class PrimaryDecompressor {
	private String x, y, compiledDictionary, compressedPixelData, decompressedPixelData = "";
	private Dictionary dictionary = null;

	public PrimaryDecompressor(String source) {
		parseSections((String) FileUtils.FileReader.read(source, ReadAs.PLAINTEXT));
		dictionary = new Dictionary(compiledDictionary);
		decompress();
	}
	
	//create the decompressed pixel data (int[][])
	private int[][] createPixelData() {
		int[][] pixelData = new int[Integer.parseInt(y)][Integer.parseInt(x)];
		String[] data = decompressedPixelData.split("(?<=\\G.........)");
		
		int l = 0;
		for(int i = 0; i < pixelData.length; i++) {
			for(int j = 0; j < pixelData[0].length; j++) {
				pixelData[i][j] = Integer.parseInt(data[l]);
				l++;
			}
		}
		
		return pixelData;
	}
	
	//decompress the pixel data
	private void decompress() {
		for(String s : dictionary) {
			compressedPixelData = compressedPixelData.replace(s.substring(s.length() - 1), s.substring(0, s.length() - 1));
		}
		
		decompressedPixelData = compressedPixelData;
	}
	
	//parses the compressed data into 4 parts and stores them in their repective Strings
	private void parseSections(String compiledData) {
		StringBuilder data = new StringBuilder(compiledData);
		
		String[] dimensions = data.substring(0, 10).split(" ");
		x = dimensions[0];
		y = dimensions[1];
		data.delete(0, 10);
		
		String[] body = data.toString().split("\\Q:\\E");
		compressedPixelData = body[0];
		compiledDictionary = body[1];
	}
	
	//get the decompressed pixel data (int[][])
	int[][] getPixelData() {
		return createPixelData();
	}
}