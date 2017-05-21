package cif.core;

import cif.convenience.FileUtils;
import cif.convenience.FileUtils.FileWriter.WriteAs;

//Primary compressor; identifies reoccuring pixel values, and compresses them

//takes pixel data (int[][]), compresses it, and writes it to a destination file (String)
public class PrimaryCompressor {
	private String[][] data = null;
	private Patterns patterns = null;
	private Dictionary dictionary = null;
	
	public PrimaryCompressor(int[][] data, String destination, int compressionLevel) {
		this.data = convertFrom2DIntAndPad(data);
		patterns = new Patterns(this.data);
		dictionary = new Dictionary(patterns);
		compress(destination);
	}
	
	//convert int[][] to String[][], and call pad() on each converted value
	private String[][] convertFrom2DIntAndPad(int[][] data) {
		String[][] converted = new String[data.length][data[0].length];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				converted[i][j] = Integer.toString(data[i][j]);
				converted[i][j] = pad(converted[i][j]);
			}
		}
		
		return converted;
	}
	
	//ensure each pixel value is exactly 9 digits long
	private String pad(String unpadded) {
		if(unpadded.length() == 9) {
			return unpadded;
		}
		
		int zeros = 9 - unpadded.length();
		
		if(unpadded.contains("-")) {
			unpadded = unpadded.replace("-", "");
			
			for(int i = 0; i < zeros; i++) {
				unpadded = "0" + unpadded;
			}
			
			unpadded = "-" + unpadded;
		}else if(!(unpadded.contains("-"))) {
			for(int i = 0; i < zeros; i++) {
				unpadded = "0" + unpadded;
			}
		}
		
		return unpadded;
	}
	
	//compress the pixel data
	private void compress(String destination) {
		String data = "";
		String pixelData = "";
		String compiledDictionary = "";
		
		String dimensions = Integer.toString(this.data[0].length) + " " + Integer.toString(this.data.length);
		String zeros = "";
		
		//create and append dimensions
		
		for(int i = 0; i < (10 - dimensions.length()); i++) {
			zeros += "0";
		}
		
		data += (zeros + dimensions);
		
		//create and append compressed pixel data
		
		for(int i = 0; i < this.data.length; i++) {
			for(int j = 0; j < this.data[0].length; j++) {
				pixelData += this.data[i][j];
			}
		}
		
		for(String s : dictionary) {
			pixelData = pixelData.replace(s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
		
		data += pixelData;
		
		//compile and append the dictionary
		
		for(String s : dictionary) {
			compiledDictionary += s;
		}
		
		data += (":" + compiledDictionary);
		
		//write results to output file
		FileUtils.FileWriter.write(destination, data, WriteAs.PLAINTEXT);
	}
}