package cif.convenience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class GlobalUtils {
	public static List<List<Integer>> pixelData = null;
	public static final ArrayList<Integer> reservedChars = new ArrayList<Integer>(Arrays.asList(0x002C, 0x0030, 0x0031, 0x0032, 0x0033, 0x0034, 0x0035, 0x0036, 0x0037, 0x0038, 0x0039, 0x003A));
	
	public static List<List<Object>> group(List<Object> ungroupedList, int groupSize) {
		int size = ungroupedList.size();
		int fullChunks = (size - 1) / groupSize;
	    return IntStream.range(0, fullChunks + 1).mapToObj(n -> ungroupedList.subList(n * groupSize, n == fullChunks ? size : (n + 1) * groupSize)).collect(Collectors.toList());
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
	
	public static String repeat(String s, int n) {
	    return Stream.generate(() -> s).limit(n).collect(Collectors.joining(""));
	}
	
	//edited version of Apache Commons StringUtils.replace (Why import a whole library when you only need 1 method?)
	//used because it is faster than the standered library replace
	public static String replace(String text, String searchString, String replacement) {
		int max = -1;
		int start = 0;
		int end = text.indexOf(searchString, start);
		int replaceStringLength = searchString.length();
		int increase = replacement.length() - replaceStringLength;
			 
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
			 
		StringBuilder buffer = new StringBuilder(text.length() + increase);
		while (end != -1) {
			buffer.append(text.substring(start, end)).append(replacement);
			start = end + replaceStringLength;
			     
			if (--max == 0) {
			    break;
			}
			     
			end = text.indexOf(searchString, start);
		}
			 
		buffer.append(text.substring(start));
		return buffer.toString();
	}
	
	//flips the signs of data values, e.g. if the input is negative, the output will be positive
	public static List<List<Integer>> flipData(List<List<Integer>> data) {
		data = data.stream().map(l -> l.stream().map(i -> i * -1).collect(Collectors.toList())).collect(Collectors.toList());
		return data;
	}
}
