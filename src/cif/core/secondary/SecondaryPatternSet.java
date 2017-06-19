package cif.core.secondary;

import java.util.ArrayList;

import cif.convenience.GlobalUtils;

public class SecondaryPatternSet extends ArrayList<String> {
	private static final long serialVersionUID = 1645872516312319309L;

	public SecondaryPatternSet(String data) {
		data = getCompressionTargets(data);
	}
	
	private String getCompressionTargets(String data) {
		data = data.split(":")[0];
		data = data.codePoints().parallel().filter(c -> !(GlobalUtils.reservedChars.contains(c))).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return data;
	}
} 
