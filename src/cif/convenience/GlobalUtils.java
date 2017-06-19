package cif.convenience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class GlobalUtils {
	public static List<List<Integer>> pixelData = null;
	public static final ArrayList<Integer> reservedChars = new ArrayList<Integer>(Arrays.asList(0x002D, 0x0030, 0x0031, 0x0032, 0x0033, 0x0034, 0x0035, 0x0036, 0x0037, 0x0038, 0x0039, 0x003A));
	
	public static List<List<Object>> group(List<Object> ungroupedList, int groupSize) {
		int size = ungroupedList.size();
		int fullChunks = (size - 1) / groupSize;
	    return IntStream.range(0, fullChunks + 1).mapToObj(n -> ungroupedList.subList(n * groupSize, n == fullChunks ? size : (n + 1) * groupSize)).collect(Collectors.toList());
	}
	
	public static List<Integer> flatten2DArrayToList(int[][] array, Sort sort) {
		if(sort == Sort.ASCENDING) {
			return Arrays.stream(array).flatMapToInt((x -> Arrays.stream(x))).boxed().sorted().collect(Collectors.toList());
		}else if(sort == null) {
			return Arrays.stream(array).flatMapToInt((x -> Arrays.stream(x))).boxed().collect(Collectors.toList());
		}else if(sort == Sort.DESCENDING) {
			return null;
		}
		
		return new ArrayList<>();
	}
	
	public static List<Integer> flatten2DListToList(List<List<Integer>> data, Sort sort) {
		if(sort == Sort.ASCENDING) {
			return data.stream().flatMap(l -> l.stream().sorted()).collect(Collectors.toList());
		}else if(sort == null) {
			return data.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
		}else if(sort == Sort.DESCENDING) {
			return null;
		}
		
		return new ArrayList<>();
	}
	
	//flips the signs of data values, e.g. if the input is negative, the output will be positive
	public static List<List<Integer>> flipData(List<List<Integer>> data) {
		data = data.stream().map(l -> l.stream().map(i -> i * -1).collect(Collectors.toList())).collect(Collectors.toList());
			
		return data;
	}
	
	
}
