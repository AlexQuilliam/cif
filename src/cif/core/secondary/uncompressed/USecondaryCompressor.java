package cif.core.secondary.uncompressed;

import cif.convenience.GlobalUtils;

public class USecondaryCompressor {
	private String uSCompressedData = "";
	
	public USecondaryCompressor(String pCompressedData) {
		USecondaryDictionary dictionary = new USecondaryDictionary(new USecondaryPatternSet(pCompressedData), pCompressedData.charAt(pCompressedData.length() - 1));
		uSCompressedData = compress(pCompressedData, dictionary);
	}
	
	private String compress(String data, USecondaryDictionary dictionary) {
		for(String s : dictionary) {
			data = GlobalUtils.replace(data, s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
		
		data += (":" + String.join("", dictionary));
		
		return data;
	}

	public String getUSCompressedData() {
		return uSCompressedData;
	}
}
