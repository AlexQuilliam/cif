package cif.core.secondary.compressed;

import cif.core.Dictionary;

public class CSecondaryDictionary extends Dictionary{
	private static final long serialVersionUID = 3019532954407067270L;

	public CSecondaryDictionary(CSecondaryPatternSet patterns, char lastEntry) {
		super(patterns, lastEntry);
	}
	
	public CSecondaryDictionary(String compiledDictionary) {
		super(compiledDictionary);
	}

	
	public void createDictionaryFromCompiledData(String compiledDictionary) {
		String[] entries = compiledDictionary.split("(?<=\\G...)");
		
		for(String s : entries) {
			this.add(s);
		}
	}
}
