package cif.core.bases;

import cif.convenience.GlobalUtils;

public class SecondaryDecompressor {
	private String sDecompressedData = "";

	public SecondaryDecompressor(String compressedData, int entryLength) {
		String[] sections = getSections(compressedData);
		sDecompressedData = decompress(sections[0], new Dictionary(sections[1], entryLength));
	}
	
	private String decompress(String data, Dictionary dictionary) {
		for(String s : dictionary) {
			data = GlobalUtils.replace(data, s.substring(s.length() - 1), s.substring(0, s.length() - 1));
		}
		
		return data;
	}
	
	private String[] getSections(String data) {
		String[] sections = data.split(":");
		
		String sectionZero = "";
		for(int i = 0; i < sections.length - 1; i++) {
			if(i < sections.length - 2) {
				sectionZero += (sections[i] + ":");
			}else {
				sectionZero += (sections[i]);
			}
		}
		
		sections[0] = sectionZero;
		sections[1] = sections[sections.length - 1];
		
		return sections;
	}

	public String getSDecompressedData() {
		return sDecompressedData;
	}
}
