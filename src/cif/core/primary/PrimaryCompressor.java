package cif.core.primary;

import java.util.List;
import java.util.stream.Collectors;

import cif.convenience.GlobalUtils;

//Primary compressor; identifies reoccuring pixel values, and compresses them
//takes pixel data (List<List<Integer>>) and compresses it
public class PrimaryCompressor {
	private String compressedData = "";
	
	public PrimaryCompressor(List<List<Integer>> data) {
		List<List<String>> stringData = applyPadding(data);
		PrimaryDictionary dictionary = new PrimaryDictionary(new PrimaryPatternSet(stringData));
		compressedData = compress(stringData, dictionary);
	}
	
	public String getPCompressedData() {
		return compressedData;
	}
	
	private List<List<String>> applyPadding(List<List<Integer>> data) {
		return data.stream().map(l -> l.stream().map(i -> pad(Integer.toString(i), 8)).collect(Collectors.toList())).collect(Collectors.toList());
	}
	
	//ensure each pixel value is exactly the desired length
	private String pad(String unpadded, int desiredLength) {
		if(unpadded.length() == desiredLength) {
			return unpadded;
		}
		
		int zeros = desiredLength - unpadded.length();
		
		for(int i = 0; i < zeros; i++) {
			unpadded = "0" + unpadded;
		}
		
		return unpadded;
	}
	
	//compress the pixel data
	private String compress(List<List<String>> data, PrimaryDictionary dictionary) {
		String compiledData = "";
		String pixelData = "";
		String compiledDictionary = "";
		
		String dimensions = Integer.toString(data.get(0).size()) + "," + Integer.toString(data.size());
		
		compiledData += (dimensions + ":");
		
		//create and append compressed pixel data
		pixelData += String.join("", data.stream().map(l -> String.join("", l)).collect(Collectors.toList()));
		
		for(String s : dictionary) {
			pixelData = GlobalUtils.replace(pixelData, s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
		
		compiledData += pixelData;
		
		//compile and append the dictionary
		
		for(String s : dictionary) {
			compiledDictionary += s;
		}
		
		compiledData += (":" + compiledDictionary);
		
		return compiledData;
	}
}