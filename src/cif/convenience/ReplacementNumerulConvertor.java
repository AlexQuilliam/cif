package cif.convenience;

public class ReplacementNumerulConvertor {
	public static String normalToReplacement(int normal) {
		String replacementNumerul = Integer.toString(normal);
		char[] digits = replacementNumerul.toCharArray();
		
		for(char c : digits) {
			replacementNumerul = GlobalUtils.replace(replacementNumerul, Character.toString(c), Character.toString(((char) GlobalUtils.numeralReplacements.get(Integer.parseInt(Character.toString(c))).intValue())));
		}
		
		return replacementNumerul;
	}
	
	public static int replacementToNormal(String replacement) {
		String normal = replacement;
		char[] digits = normal.toCharArray();
		
		for(char c : digits) {
			normal = GlobalUtils.replace(normal, Character.toString(c), Integer.toString(GlobalUtils.numeralReplacements.indexOf((int) c)));
		}
		
		return Integer.parseInt(normal);
	}
}
