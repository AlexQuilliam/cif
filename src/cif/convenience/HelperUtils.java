package cif.convenience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A collection of public, static helper methods and variables that are used throughout the program. 
 */
public final class HelperUtils {
	/**
	 * A variable meant to store the pixel data when it is created. This is only meant to be used in the {@link Compressor} class.
	 * It is initialized to {@code null}.
	 */
	public static List<List<Integer>> pixelData = null;
	/**
	 * A variable meant to store sections of parsed compressed data. There are 5 sections upon initial initialization, each delimited with a colon (":"); the 
	 * image metadata (length and height), the compressed pixel data (the body), the primary dictionary, the usecondary dictionary,
	 * and the csecondary dictionary. A section may or may not exist in the list, depending on the stage. It is initialized to {@code null}.
	 * <br>
	 * <br>
	 * The index of each section is as follows:
	 * 
	 * <ul>
	 * <li>0: image metadata</li>
	 * <li>1: compressed pixel data</li>
	 * <li>2: primary dictionary</li>
	 * <li>3: usecondary dictionary</li>
	 * <li>4: csecondary dictionary</li>
	 * </ul>
	 */
	public static List<String> sections = null;
	/**
	 * A {@code constant} list of characters that are reserved, preventing use as dictionary entries or other uses. It includes
	 * the comma, numbers 0 through 9, and the colon.
	 */
	public static final ArrayList<Integer> reservedChars = new ArrayList<Integer>(Arrays.asList(0x002C, 0x0030, 0x0031, 0x0032, 0x0033, 0x0034, 0x0035, 0x0036, 0x0037, 0x0038, 0x0039, 0x003A));
	public static final ArrayList<Integer> numeralReplacements = new ArrayList<Integer>(Arrays.asList(0x08A0, 0x08A1, 0x08A2, 0x08A3, 0x08A4, 0x08A5, 0x08A6, 0x08A7, 0x08A8, 0x08A9));
	
	/**
	 * Groups a 1D list into {@code (ungroupedList/size() - 1) / groupSize} chunks, stored in a 2D list.
	 * @param ungroupedList one-dimensional list to be grouped
	 * @param groupSize the size of each group
	 * @return the grouped list
	 */
	public static List<List<Object>> group(List<Object> ungroupedList, int groupSize) {
		int size = ungroupedList.size();
		int fullChunks = (size - 1) / groupSize;
	    return IntStream.range(0, fullChunks + 1).mapToObj(n -> ungroupedList.subList(n * groupSize, n == fullChunks ? size : (n + 1) * groupSize)).collect(Collectors.toList());
	}
	
	/**
	 * Repeats the given character sequence n number of times.
	 * @param sequnce the string to be repeated
	 * @param numberOfRepititions the number of times the string is to be repeated
	 * @return the repeated string
	 */
	public static String repeat(String sequnce, int numberOfRepititions) {
	    return Stream.generate(() -> sequnce).limit(numberOfRepititions).collect(Collectors.joining(""));
	}
	
	/**
	 * Equivalent to String.replace(CharSequence target, CharSequence replacement), except much faster. This method
	 * is a modified version of Apaches {@code StringUtils.replace(String text, String searchString, String replacement)} method.
	 * @param text the text to search
	 * @param searchString the string to be replaced
	 * @param replacement the string {@code searchString} is to be replaced with
	 * @return the replaced string
	 */
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
	
	/**
	 * Flips the signs of the data.
	 * @param data the data to be flipped
	 * @return the flipped data
	 */
	public static List<List<Integer>> flipData(List<List<Integer>> data) {
		data = data.stream().map(l -> l.stream().map(i -> i * -1).collect(Collectors.toList())).collect(Collectors.toList());
		return data;
	}
	
	/**
	 * Parses the data into sections, which are then added to {@link HelperUtils.sections}. See the javadoc for {@link HelperUtils.sections}
	 * for more information.
	 * @param data the data to be parsed
	 * @return void
	 */
	public static void parseSections(String data) {
		HelperUtils.sections = Arrays.asList(data.split(":"));
	}
}
