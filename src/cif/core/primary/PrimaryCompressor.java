package cif.core.primary;

import java.util.List;
import java.util.stream.Collectors;

import cif.convenience.HelperUtils;

/**
 * The first compression stage. This compressor identifies reoccuring pixel values via {@link PrimaryPatternSet} 
 * and then creates a dictionary via {@link PrimaryDictionary}. Afterwards, it replaces each reoccuring pixel value with 
 * its definition in the dictionary.
 * <br>
 * <strong>Performance Notes:</strong>
 * <ul>
 * <li>Replacing is a major bottleneck here (in the {@code compress} method), often taking upwards of 4/5 of the execution
 * time of the program.</li>
 * </ul>
*/
public class PrimaryCompressor {
	private String compressedData = "";
	
	/**
	 * Create a new {@code PrimaryCompressor} object. All code is executed upon class initialization in this constructor, not in the {@link getPCompressedData}
	 * method.
	 * @param data a {@code List<List<Integer>>} containing the pixel data to be compressed.
	 */
	public PrimaryCompressor(List<List<Integer>> data) {
		List<List<String>> stringData = applyPadding(data);
		PrimaryDictionary dictionary = new PrimaryDictionary(new PrimaryPatternSet(stringData));
		compressedData = compress(stringData, dictionary);
	}
	
	/**
	 * Get the primary compressed pixel data.
	 * @return the primary compressed pixel data
	 */
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
	@SuppressWarnings("unchecked")
	private String compress(List<List<String>> data, PrimaryDictionary dictionary) {
		String compiledData = "";
		String pixelData = "";
		String compiledDictionary = "";
		
		String dimensions = Integer.toString(data.get(0).size()) + "," + Integer.toString(data.size());
		
		compiledData += (dimensions + ":");
		
		pixelData = String.join("", data.stream().map(l -> String.join("", l)).collect(Collectors.toList()));
		
		for(String s : dictionary) {
			pixelData = HelperUtils.replace(pixelData, s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
		
		compiledData += pixelData;

		for(String s : dictionary) {
			compiledDictionary += s;
		}
		
		compiledData += (":" + compiledDictionary);
		
		return compiledData;
	}
}