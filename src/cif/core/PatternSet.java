package cif.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PatternSet extends ArrayList<String> {
	private static final long serialVersionUID = -9174986184619366609L;

	//create the patterns
	public void extractPatterns(Map<String, Integer> occurences) {
		this.addAll(occurences.entrySet().stream().filter(key -> key.getValue() >= 2).map(Map.Entry::getKey).collect(Collectors.toList()));
	}
	
	//calculates the number of occurences of each value
	public Map<String, Integer> calculateOccurences(List<List<String>> data) {
		return data.stream().flatMap(l -> l.stream()).collect(Collectors.toMap(Function.identity(), v -> 1, (x, y) -> x + y));
	}
	
	public Map<String, Integer> calculateOccurences(String[] data) {
		return Arrays.stream(data).collect(Collectors.toMap(Function.identity(), v -> 1, (x, y) -> x + y));
	}
}