package cif.core.primary;

import java.util.List;

import cif.core.PatternSet;

public class PrimaryPatternSet extends PatternSet {
	private static final long serialVersionUID = -9174986184619366609L;
	
	public PrimaryPatternSet(List<List<String>> data) {
		extractPatterns(calculateOccurences(data));
	}
}