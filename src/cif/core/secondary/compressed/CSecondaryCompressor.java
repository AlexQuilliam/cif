package cif.core.secondary.compressed;

import cif.convenience.GlobalUtils;

public class CSecondaryCompressor {
	private String cSCompressedData = "";
	
	public CSecondaryCompressor(String pCompressedData) {
		CSecondaryDictionary dictionary = new CSecondaryDictionary(new CSecondaryPatternSet(pCompressedData), pCompressedData.charAt(pCompressedData.length() - 1));
		this.cSCompressedData = compress(pCompressedData, dictionary);
	}
	
	private String compress(String data, CSecondaryDictionary dictionary) {
		for(String s : dictionary) {
			data = GlobalUtils.replace(data, s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
		
		data += (":" + String.join("", dictionary));
		
		return data;
	}

	public String getCSCompressedData() {
		return cSCompressedData;
	}
}
