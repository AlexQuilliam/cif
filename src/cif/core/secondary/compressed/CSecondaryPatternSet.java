package cif.core.secondary.compressed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cif.convenience.GlobalUtils;
import cif.core.bases.PatternSet;

public class CSecondaryPatternSet extends PatternSet {
	private static final long serialVersionUID = 1645872516312319309L;

	public CSecondaryPatternSet(String data) {
		data = getCompressedData(data);
		extractPatterns(calculateOccurences(filterValues(getGroups(data))));
		removeOversizedPatterns(this);
	}
	
	private List<String> removeOversizedPatterns(List<String> data) {
		List<Character> knownPatterns = new ArrayList<Character>();
		
		for(String s : data) {
			if(!(knownPatterns.contains(s.charAt(0)))) {
				knownPatterns.add(s.charAt(0));
			}
		}
		
		data.clear();
		
		for(char c : knownPatterns) {
			data.add(GlobalUtils.repeat(Character.toString(c), 2));
		}
		
		return data;
	}
	
	private String[] getGroups(String data) {
		return data.split("(?<=(.))(?!\\1)");
	}
	
	private String[] filterValues(String[] data) {
		return Arrays.stream(data).filter(s -> s.length() > 1).toArray(String[]::new);
	}
	
	private String getCompressedData(String data) {
		data = data.split(":")[1];
		data = data.codePoints().filter(c -> !(GlobalUtils.reservedChars.contains(c))).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return data;
	}
} 
