package cif.core.primary;

import java.util.ArrayList;

import cif.convenience.GlobalUtils;

public class PrimaryDictionary extends ArrayList<String> {
	private static final long serialVersionUID = -3361705036269364227L;
	
	public PrimaryDictionary(PrimaryPatternSet patterns) {
		createDictionaryFromPatterns(patterns);
	}
	
	public PrimaryDictionary(String compressedDictionary) {
		createDictionaryFromCompressedData(compressedDictionary);
	}

	public PrimaryDictionary(int initialCapacity) {
		super(initialCapacity);
	}
	
	private void createDictionaryFromPatterns(ArrayList<String> patterns) {
		for(int i = 0x0000, index = 0; index < patterns.size(); i++) {
			if(Character.isDefined(i) && isPrintable(i) && !(GlobalUtils.reservedChars.contains(i))) {
				this.add((patterns.get(index)) + (char) i);
				index++;
			}
		}
	}
	
	private void createDictionaryFromCompressedData(String compressedDictionary) {
		String[] entries = compressedDictionary.split("(?<=\\G.........)");
		
		for(String s : entries) {
			this.add(s);
		}
	}
	
	private boolean isPrintable(int i) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(i);
	    return ((!Character.isISOControl(i)) && block != null && block != Character.UnicodeBlock.SPECIALS);
	}
}
