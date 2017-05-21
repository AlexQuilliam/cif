package cif.convenience;

public class Print {
	public static void arr(Object[] array) {for(int i = 0; i < array.length; i++){System.out.println(i);}}
	public static void arr2D(Object object) {for(int i = 0; i < ((int[][]) object).length; i++){for(int j = 0; j < ((int[][]) object)[0].length; j++){System.out.println(((int[][]) object)[i][j]);}}};
	public static void ln(Object o) {System.out.println(o);}
}