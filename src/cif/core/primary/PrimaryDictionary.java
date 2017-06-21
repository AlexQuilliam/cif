package cif.core.primary;

import cif.core.bases.Dictionary;

public class PrimaryDictionary extends Dictionary {
	private static final long serialVersionUID = -3361705036269364227L;
	
	public PrimaryDictionary(PrimaryPatternSet patterns) {
		super(patterns, (char) 0);
	}
	
	public PrimaryDictionary(String compressedDictionary) {
		super(compressedDictionary, 9);
	}
}
