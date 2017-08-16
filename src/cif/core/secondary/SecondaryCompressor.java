package cif.core.secondary;

import java.util.Map;

import cif.convenience.HelperUtils;
import cif.core.Dictionary;

public class SecondaryCompressor {
	private String sCompressedData = "";
	
	public SecondaryCompressor() {}
	
	public SecondaryCompressor(String pCompressedData) {
		sCompressedData = new USecondaryCompressor(pCompressedData).getSCompressedData();
		sCompressedData = new CSecondaryCompressor(sCompressedData).getSCompressedData();
	}
	
	public String compress(String data, Dictionary dictionary) {
		for(Map.Entry<String, String> entry : dictionary.entrySet()) {
			data = HelperUtils.replace(data, entry.getKey(), entry.getValue());
		}
		
		data += (":" + dictionary.toString());
		
		return data;
	}

	public String getSCompressedData() {
		return sCompressedData;
	}
	
	public class USecondaryCompressor extends SecondaryCompressor {
		public USecondaryCompressor(String pCompressedData) {
			sCompressedData = compress(pCompressedData, new Dictionary(new USecondaryPatternSet(pCompressedData), pCompressedData.charAt(pCompressedData.length() - 1)));
		}
	}
	
	public class CSecondaryCompressor extends SecondaryCompressor {
		public CSecondaryCompressor(String uCompressedData) {
			sCompressedData = compress(uCompressedData, new Dictionary(new CSecondaryPatternSet(uCompressedData), uCompressedData.charAt(uCompressedData.length() - 1)));
		}
	}
}
