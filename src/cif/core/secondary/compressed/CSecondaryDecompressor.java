package cif.core.secondary.compressed;

import cif.convenience.GlobalUtils;

public class CSecondaryDecompressor {
	private String cSDecompressedData = "";

	public CSecondaryDecompressor(String compressedData) {
		String[] sections = getSections(compressedData);
		cSDecompressedData = decompress(sections[0], new CSecondaryDictionary(sections[1]));
	}
	
	private String decompress(String data, CSecondaryDictionary dictionary) {
		for(String s : dictionary) {
			data = GlobalUtils.replace(data, s.substring(s.length() - 1), s.substring(0, s.length() - 1));
		}
		
		return data;
	}

	private String[] getSections(String data) {
		String[] sections = data.split(":");
		sections[0] = sections[0] + ":" + sections[1] + ":" + sections[2] + ":" + sections[3];
		sections[1] = sections[4];
		
		return sections;
	}
	
	public String getCSDecompressedData() {
		return cSDecompressedData;
	}
}
