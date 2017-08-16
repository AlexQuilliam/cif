package cif.core.secondary;

import cif.convenience.HelperUtils;
import cif.core.bases.PatternSet;

public class USecondaryPatternSet extends PatternSet {
	private static final long serialVersionUID = 1645872516312319309L;

	public USecondaryPatternSet(String data) {
		data = getUncompressedData(data);
		extractPatterns(calculateOccurences(getProcessedData(data)));
	}
	
	private String[] getProcessedData(String data) {
		return data.split("(?<=\\G..)");
	}
	
	private String getUncompressedData(String data) {
		String[] sections = data.split(":");
		data = sections[1] + sections[2];
		data = data.codePoints().filter(c -> HelperUtils.reservedChars.contains(c)).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return data;
	}
} 
