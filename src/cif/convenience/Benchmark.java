package cif.convenience;

import java.util.TreeMap;

public class Benchmark {
	private static TreeMap<Integer, Long> startTimes = new TreeMap<Integer, Long>();
	private static TreeMap<Integer, Long> elapsedTime = new TreeMap<Integer, Long>();
	private static boolean disable = false;
	
	public static void disable() {
		disable = true;
	}
	
	public static void enable() {
		disable = false;
	}
	
	public static void start(int id){
		if(disable) return;
		startTimes.put(id, System.nanoTime());
	}
	
	public static void end(int id){
		if(disable) return;
		elapsedTime.put(id, System.nanoTime() - startTimes.get(id));
	}
	
	public static void endAndPrint(int id, String message, Unit unit) {
		if(disable) return;
		end(id);
		if(unit == Unit.SECONDS) {
			Print.ln(message + elapsedTime.get(id) / 1000000000 + "s");
		}else if(unit == Unit.MILLISECONDS) {
			Print.ln(message + elapsedTime.get(id) / 1000000 + "ms");
		}else if(unit == Unit.NANOSECONDS) {
			Print.ln(message + elapsedTime.get(id) + "ns");
		}else {
			try {
				throw new Exception("Invalid unit code \'" + unit + "\'");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void print(int id, String message, Unit unit) {
		if(disable) return;
		if(unit == Unit.SECONDS) {
			Print.ln(message + elapsedTime.get(id) / 1000000000 + "s");
		}else if(unit == Unit.MILLISECONDS) {
			Print.ln(message + elapsedTime.get(id) / 1000000 + "ms");
		}else if(unit == Unit.NANOSECONDS) {
			Print.ln(message + elapsedTime.get(id) + "ns");
		}else {
			try {
				throw new Exception("Invalid unit code \'" + unit + "\'");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
