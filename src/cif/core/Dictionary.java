package cif.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cif.convenience.HelperUtils;
import cif.core.bases.PatternSet;

//value, placeholder
public class Dictionary extends TreeMap<String, String> {
	private static final long serialVersionUID = -1524456894288348683L;
	private static int k = 20;
	
	public Dictionary(PatternSet patterns, char lastEntry) {
		createDictionaryFromPatterns(patterns, lastEntry);
	}
	
	public Dictionary(String compiledDictionary, int entryLength) {
		createDictionaryFromCompiledData(compiledDictionary, entryLength);
	}
	
	/**
	 * <strong>Overridden: </strong>
	 * <br>
	 * Returns a string representation of the dictionary with no delimiters, in the following format: key + value + key + value, etc.
	 */
	@Override
	public String toString() {
		String stringRepresentation = "";
		
		for(Map.Entry<String, String> entry : this.entrySet()) {
			stringRepresentation += (entry.getKey() + entry.getValue());
		}
		
		return stringRepresentation;
	}
	
	private void createDictionaryFromCompiledData(String compiledDictionary, int entryLength) {
		String regex = "(?<=\\G";
		
		for(int i = 0; i < entryLength; i++) {
			regex += ".";
		}
		
		regex += ")";
		
		String[] entries = compiledDictionary.split(regex);
		
		for(String s : entries) {
			this.put(s.substring(0, s.length() - 1), s.substring(s.length() - 1));
		}
	}
	
	private void createDictionaryFromPatterns(ArrayList<String> patterns, char lastEntry) {
		for(int i = k, index = 0; index < patterns.size(); i++) {
			if(Character.isDefined(i) && isPrintable(i) && !(HelperUtils.reservedChars.contains(i)) && !(HelperUtils.numeralReplacements.contains(i))) {
				this.put((patterns.get(index)), String.valueOf((char) i));
				index++;
			}
		}
		
		k += 300;
	}
	
	/*private void createDictionaryFromPatterns(ArrayList<String> patterns, char lastEntry) {
		for(int i = (int) lastEntry + 1, index = 0; index < patterns.size(); i++) {
			if(Character.isDefined(i) && isPrintable(i) && !(HelperUtils.reservedChars.contains(i)) && !(HelperUtils.numeralReplacements.contains(i))) {
				this.put((patterns.get(index)), String.valueOf((char) i));
				index++;
			}
		}
	}*/
	
	private boolean isPrintable(int i) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(i);
	    return ((!Character.isISOControl(i)) && block != null && block != Character.UnicodeBlock.SPECIALS);
	}
}
