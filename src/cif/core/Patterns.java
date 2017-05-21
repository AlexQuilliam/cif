package cif.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Patterns extends ArrayList<String> {
	private static final long serialVersionUID = -9174986184619366609L;
	private String[][] data = null;
	private Map<String, Integer> occurences = null;
	
	public Patterns(String[][] data) {
		this.data = data;
		extractPatterns();
	}
	
	//create the patterns
	private void extractPatterns() {
		calculateOccurences();
		int limit = decideLimit();
		this.addAll(occurences.entrySet().stream().filter(key -> key.getValue() >= limit).map(Map.Entry::getKey).collect(Collectors.toList()));
	}
	
	//METHOD ON HOLD
	//TODO: find out why the limit can't be less than 5
	//if there are grey patches in the decompressed image, this method could be the issue
	//dynamically calculate the number of times a value has to occur for it to be included in the dictionary
	private int decideLimit() {
		return 5;
	}
	
	//calculates the number of occurences of each value, as well as calculating the number of unique values in the dataset
	private void calculateOccurences() {
		occurences = Stream.of(data).flatMap(Stream::of).collect(Collectors.toMap(Function.identity(), v -> 1, (x, y) -> x + y));
	}
}