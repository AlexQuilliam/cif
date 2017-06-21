package cif.core.secondary.compressed;

import cif.core.bases.Dictionary;
import cif.core.bases.SecondaryCompressor;

public class CSecondaryCompressor extends SecondaryCompressor{
	public CSecondaryCompressor(String pCompressedData) {
		super(pCompressedData);
	}

	public Dictionary createDictionary(String data, char lastEntry) {
		return new Dictionary(new CSecondaryPatternSet(data), data.charAt(data.length() - 1));
	}
}
