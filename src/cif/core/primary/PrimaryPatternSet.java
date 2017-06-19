package cif.core.primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrimaryPatternSet extends ArrayList<String> {
	private static final long serialVersionUID = -9174986184619366609L;
	
	public PrimaryPatternSet(List<List<String>> data) {
		extractPatterns(calculateOccurences(data));
	}
	
	//create the patterns
	private void extractPatterns(Map<String, Integer> occurences) {
		int limit = decideLimit();
		this.addAll(occurences.entrySet().stream().filter(key -> key.getValue() >= limit).map(Map.Entry::getKey).collect(Collectors.toList()));
	}
	
	//METHOD ON HOLD
	//TODO: find out why the limit can't be less than 6
	//if there are grey patches in the decompressed image, this method could be the issue
	//dynamically calculate the number of times a value has to occur for it to be included in the dictionary
	private int decideLimit() {
		return 6;
	}
	
	//calculates the number of occurences of each value, as well as calculating the number of unique values in the dataset
	private Map<String, Integer> calculateOccurences(List<List<String>> data) {
		return data.stream().flatMap(l -> l.stream()).collect(Collectors.toMap(Function.identity(), v -> 1, (x, y) -> x + y));
	}
}