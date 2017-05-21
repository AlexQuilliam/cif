package cif.backend;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Patterns extends ArrayList<String> {
	private static final long serialVersionUID = -9174986184619366609L;
	private String[][] data = null;
	private Map<String, Integer> occurences = null;
	private int numberOfUniqueValues = 0;
	private int compressionLevel = 0;
	
	public Patterns(String[][] data, int compressionLevel) {
		this.data = data;
		this.compressionLevel = compressionLevel;
		extractPatterns();
	}
	
	//create the patterns
	private void extractPatterns() {
		calculateOccurences();
		int limit = decideLimit();
		this.addAll(occurences.entrySet().stream().filter(key -> key.getValue() >= limit).map(Map.Entry::getKey).collect(Collectors.toList()));
	}
	
	//dynamically calculate the number of times a value has to occur for it to be included in the dictionary
	private int decideLimit() {
		int limit = 0;
		
		double i = 0;
		
		while(i < numberOfUniqueValues) {
			limit++;
			i += 20 * (compressionLevel);
		}
		
		return limit;
	}
	
	//calculates the number of occurences of each value, as well as calculating the number of unique values in the dataset
	private void calculateOccurences() {
		ArrayList<String> values = new ArrayList<String>();
		
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				values.add(data[i][j]);
			}
		}
		
		numberOfUniqueValues = values.stream().distinct().collect(Collectors.toList()).size();
		occurences = Stream.of(data).flatMap(Stream::of).collect(Collectors.toMap(Function.identity(), v -> 1, (x, y) -> x + y));
	}
}