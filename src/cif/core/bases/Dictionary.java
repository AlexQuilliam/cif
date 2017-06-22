package cif.core.bases;

import java.util.ArrayList;

import cif.convenience.GlobalUtils;

public class Dictionary extends ArrayList<String> {
	private static final long serialVersionUID = -1524456894288348683L;
	
	public Dictionary(PatternSet patterns, char lastEntry) {
		createDictionaryFromPatterns(patterns, lastEntry);
	}
	
	public Dictionary(String compiledDictionary, int entryLength) {
		createDictionaryFromCompiledData(compiledDictionary, entryLength);
	}
	
	private void createDictionaryFromCompiledData(String compiledDictionary, int entryLength) {
		String regex = "(?<=\\G";
		
		for(int i = 0; i < entryLength; i++) {
			regex += ".";
		}
		
		regex += ")";
		
		String[] entries = compiledDictionary.split(regex);
		
		for(String s : entries) {
			this.add(s);
		}
	}
	
	private void createDictionaryFromPatterns(ArrayList<String> patterns, char lastEntry) {
		for(int i = lastEntry + 1, index = 0; index < patterns.size(); i++) {
			if(Character.isDefined(i) && isPrintable(i) && !(GlobalUtils.reservedChars.contains(i)) && !(GlobalUtils.numeralReplacements.contains(i))) {
				this.add((patterns.get(index)) + (char) i);
				index++;
			}
		}
	}
	
	private boolean isPrintable(int i) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(i);
	    return ((!Character.isISOControl(i)) && block != null && block != Character.UnicodeBlock.SPECIALS);
	}
}
