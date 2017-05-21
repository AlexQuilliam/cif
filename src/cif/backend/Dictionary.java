package cif.backend;

import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary extends ArrayList<String> {
	private static final long serialVersionUID = -3361705036269364227L;
	
	public Dictionary(Patterns patterns) {
		createDictionaryFromPatterns(patterns);
	}
	
	public Dictionary(String compressedDictionary) {
		createDictionaryFromCompressedData(compressedDictionary);
	}

	public Dictionary(int initialCapacity) {
		super(initialCapacity);
	}
	
	private void createDictionaryFromPatterns(ArrayList<String> patterns) {
		final ArrayList<Integer> reservedValues = new ArrayList<Integer>(Arrays.asList(0x002D, 0x0030, 0x0031, 0x0032, 0x0033, 0x0034, 0x0035, 0x0036, 0x0037, 0x0038, 0x0039, 0x003A));
		
		for(int i = 0x0000, index = 0; index < patterns.size(); i++) {
			if(Character.isDefined(i) && isPrintable(i) && !(reservedValues.contains(i))) {
				this.add((patterns.get(index)) + (char) i);
				index++;
			}
		}
	}
	
	private void createDictionaryFromCompressedData(String compressedDictionary) {
		String[] entries = compressedDictionary.split("(?<=\\G..........)");
		
		for(String s : entries) {
			this.add(s);
		}
	}
	
	private boolean isPrintable(int i) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(i);
	    return ((!Character.isISOControl(i)) && block != null && block != Character.UnicodeBlock.SPECIALS);
	}
}
