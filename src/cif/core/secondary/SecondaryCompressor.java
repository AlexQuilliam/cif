package cif.core.secondary;

import cif.core.secondary.compressed.CSecondaryCompressor;
import cif.core.secondary.uncompressed.USecondaryCompressor;

public class SecondaryCompressor {
	private String uSCompressedData = "";

	public SecondaryCompressor(String pCompressedData) {
		uSCompressedData = new USecondaryCompressor(pCompressedData).getSCompressedData();
		uSCompressedData = new CSecondaryCompressor(uSCompressedData).getSCompressedData();
	}
	
	public String getUSCompressedData() {
		return uSCompressedData;
	}
}
