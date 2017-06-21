package cif.core.secondary.compressed;

import cif.core.bases.SecondaryDecompressor;

public class CSecondaryDecompressor extends SecondaryDecompressor {
	public CSecondaryDecompressor(String compressedData) {
		super(compressedData, 3);
	}
}