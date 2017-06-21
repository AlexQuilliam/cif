package cif.core.secondary.uncompressed;

import cif.core.bases.SecondaryDecompressor;

public class USecondaryDecompressor extends SecondaryDecompressor {
	public USecondaryDecompressor(String compressedData) {
		super(compressedData, 5);
	}
}
 