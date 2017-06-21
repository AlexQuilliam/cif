package cif.core.secondary;

import cif.core.secondary.compressed.CSecondaryDecompressor;
import cif.core.secondary.uncompressed.USecondaryDecompressor;

public class SecondaryDecompressor {
	private String uSDecompressedData = "";

	public SecondaryDecompressor(String sCompressedData) {
		uSDecompressedData = new CSecondaryDecompressor(sCompressedData).getSDecompressedData();
		uSDecompressedData = new USecondaryDecompressor(uSDecompressedData).getSDecompressedData();
	}

	public String getUSDecompressedData() {
		return uSDecompressedData;
	}
}
