package cif.core.secondary;

import java.util.Map;

import cif.convenience.HelperUtils;
import cif.core.Dictionary;

public class SecondaryDecompressor {
	private String sDecompressedData = "";
	
	public SecondaryDecompressor() {}

	public SecondaryDecompressor(String compressedData) {
		sDecompressedData = new CSecondaryDecompressor(compressedData).getSDecompressedData();
		sDecompressedData = new USecondaryDecompressor(sDecompressedData).getSDecompressedData();
	}
	
	private String decompress(String data, Dictionary dictionary) {
		for(Map.Entry<String, String> entry : dictionary.entrySet()) {
			data = HelperUtils.replace(data, entry.getValue(), entry.getKey());
		}
		
		return data;
	}

	public String getSDecompressedData() {
		return sDecompressedData;
	}
	
	public class CSecondaryDecompressor extends SecondaryDecompressor {
		public CSecondaryDecompressor(String sCompressedData) {
			HelperUtils.parseSections(sCompressedData);
			sDecompressedData = decompress(sCompressedData.substring(0, sCompressedData.length() - HelperUtils.sections.get(4).length() - 1), new Dictionary(HelperUtils.sections.get(4), 3));
		}
	}
	
	public class USecondaryDecompressor extends SecondaryDecompressor {
		public USecondaryDecompressor(String sCompressedData) {
			HelperUtils.parseSections(sCompressedData);
			sDecompressedData = decompress(sCompressedData.substring(0, sCompressedData.length() - HelperUtils.sections.get(3).length() - 1), new Dictionary(HelperUtils.sections.get(3), 3));
		}
	}
}
