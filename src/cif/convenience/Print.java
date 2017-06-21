package cif.convenience;

public class Print {
	private static boolean disable = false;
	
	public static void disable() {
		disable = true;
	}
	
	public static void enable() {
		disable = false;
	}
	
	public static void arr(Object[] array) {
		if(disable) return;
		
		for(int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
	
	public static void arr2D(Object[][] array) {
		if(disable) return;
		
		for(int i = 0; i < ((Object[][]) array).length; i++) {
			for(int j = 0; j < ((Object[][]) array)[0].length; j++) {
				System.out.println(((Object[][]) array)[i][j]);
			}
		}
	}
	
	public static void arr(int[] array) {
		if(disable) return;
		
		for(int i = 0; i < array.length; i++) {
			System.out.println(i);
		}
	}
	
	public static void arr2D(int[][] array) {
		if(disable) return;
		
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				System.out.println(array[i][j]);
			}
		}
	}
	
	public static void ln(Object o) {
		if(disable) return;
		
		System.out.println(o);
	}
	
	public static void ln() {
		if(disable) return;
		
		System.out.println();
	}
}