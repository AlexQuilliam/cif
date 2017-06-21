package cif.core.secondary.uncompressed;

import cif.core.bases.Dictionary;
import cif.core.bases.SecondaryCompressor;

public class USecondaryCompressor extends SecondaryCompressor{
	public USecondaryCompressor(String pCompressedData) {
		super(pCompressedData);
	}
	
	public Dictionary createDictionary(String data, char lastEntry) {
		return new Dictionary(new USecondaryPatternSet(data), data.charAt(data.length() - 1));
	}
}
