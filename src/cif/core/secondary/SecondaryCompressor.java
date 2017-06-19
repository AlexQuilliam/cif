package cif.core.secondary;

public class SecondaryCompressor {
	private String pCompressedData = "";
	
	public SecondaryCompressor(String pCompressedData) {
		this.pCompressedData = pCompressedData;
	}

	public String getSCompressedData() {
		return pCompressedData;
	}
}
