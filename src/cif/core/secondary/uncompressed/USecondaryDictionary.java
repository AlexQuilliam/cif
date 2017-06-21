package cif.core.secondary.uncompressed;

import cif.core.Dictionary;
import cif.core.secondary.uncompressed.USecondaryPatternSet;

public class USecondaryDictionary extends Dictionary {
	private static final long serialVersionUID = -2768848852119501601L;

	public USecondaryDictionary(USecondaryPatternSet patterns, char lastEntry) {
		super(patterns, lastEntry);
	}

	public USecondaryDictionary(String compiledDictionary) {
		super(compiledDictionary);
	}
	
	public void createDictionaryFromCompiledData(String compiledDictionary) {
		String[] entries = compiledDictionary.split("(?<=\\G.....)");
		
		for(String s : entries) {
			this.add(s);
		}
	}
}
