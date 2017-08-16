package cif.core.primary;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cif.convenience.HelperUtils;
import cif.core.Dictionary;

public class PrimaryDecompressor {
	private String x, compiledDictionary, compressedPixelData = "";

	public PrimaryDecompressor(String pCompressedData) {
		parseSections(pCompressedData);
	}
	
	//create the decompressed pixel data 
	@SuppressWarnings("unchecked")
	private List<List<Integer>> createPixelData(String decompressedData) {
		String[] rawData = decompressedData.split("(?<=\\G........)");

		return (List<List<Integer>>) (Object) HelperUtils.group(Arrays.asList(rawData).stream().map(s -> Integer.parseInt(s) * -1).collect(Collectors.toList()), Integer.parseInt(x));
	}
	
	//decompress the pixel data
	private String decompress(String compressedData, Dictionary dictionary) {
		for(Map.Entry<String, String> entry : dictionary.entrySet()) {
			compressedData = HelperUtils.replace(compressedData, entry.getValue(), entry.getKey());
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
		return createPixelData(decompress(compressedPixelData, new Dictionary(compiledDictionary, 9)));
	}
}