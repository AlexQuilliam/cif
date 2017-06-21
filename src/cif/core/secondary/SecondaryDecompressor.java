package cif.core.secondary;

import cif.core.secondary.compressed.CSecondaryDecompressor;
import cif.core.secondary.uncompressed.USecondaryDecompressor;

public class SecondaryDecompressor {
	private String uSDecompressedData = "";

	public SecondaryDecompressor(String sCompressedData) {
		uSDecompressedData = new CSecondaryDecompressor(sCompressedData).getCSDecompressedData();
		uSDecompressedData = new USecondaryDecompressor(uSDecompressedData).getUSDecompressedData();
	}

	public String getUSDecompressedData() {
		return uSDecompressedData;
	}
}
