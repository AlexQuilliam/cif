package cif.core.secondary.uncompressed;

import cif.convenience.GlobalUtils;

public class USecondaryDecompressor {
	private String uSDecompressedData = "";

	public USecondaryDecompressor(String compressedData) {
		String[] sections = getSections(compressedData);
		uSDecompressedData = decompress(sections[0], new USecondaryDictionary(sections[1]));
	}
	
	private String decompress(String data, USecondaryDictionary dictionary) {
		for(String s : dictionary) {
			data = GlobalUtils.replace(data, s.substring(s.length() - 1), s.substring(0, s.length() - 1));
		}
		
		return data;
	}
	
	private String[] getSections(String data) {
		String[] sections = data.split(":");
		sections[0] = sections[0] + ":" + sections[1] + ":" + sections[2];
		sections[1] = sections[3];
		
		return sections;
	}

	public String getUSDecompressedData() {
		return uSDecompressedData;
	}
}
 