package cif.core.primary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cif.convenience.GlobalUtils;

public class PrimaryDecompressor {
	private String x, compiledDictionary, compressedPixelData = "";

	public PrimaryDecompressor(String pCompressedData) {
		parseSections(pCompressedData);
	}
	
	//create the decompressed pixel data 
	@SuppressWarnings("unchecked")
	private List<List<Integer>> createPixelData(String decompressedData) {
		String[] rawData = decompressedData.split("(?<=\\G........)");

		return (List<List<Integer>>) (Object) GlobalUtils.group(Arrays.asList(rawData).stream().map(s -> Integer.parseInt(s) * -1).collect(Collectors.toList()), Integer.parseInt(x));
	}
	
	//decompress the pixel data
	private String decompress(String compressedData, PrimaryDictionary dictionary) {
		for(String s : dictionary) {
			compressedData = GlobalUtils.replace(compressedData, s.substring(s.length() - 1), s.substring(0, s.length() - 1));
		}
		
		return compressedData;
	}
	
	//parses the compressed data into 3 parts and stores them in their repective Strings
	private void parseSections(String compiledData) {
		String[] body = compiledData.split("\\Q:\\E");
		x = body[0].split(",")[0];
		compressedPixelData = body[1];
		compiledDictionary = body[2];
	}
	
	//get the decompressed pixel data List<List<Integer>>
	public List<List<Integer>> getPixelData() {
		return createPixelData(decompress(compressedPixelData, new PrimaryDictionary(compiledDictionary)));
	}
}