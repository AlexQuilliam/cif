package cif.core.bases;

import cif.convenience.GlobalUtils;

public abstract class SecondaryCompressor {
	private String sCompressedData = "";
	
	public SecondaryCompressor(String pCompressedData) {
		sCompressedData = compress(pCompressedData, createDictionary(pCompressedData, pCompressedData.charAt(pCompressedData.length() - 1)));
	}
	
	public abstract Dictionary createDictionary(String data, char lastEntry);
	
	public String compress(String data, Dictionary dictionary) {
		for(String s : dictionary) {
			data = GlobalUtils.replace(data, s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
		
		data += (":" + String.join("", dictionary));
		
		return data;
	}

	public String getSCompressedData() {
		return sCompressedData;
	}
}
