package cif.core;

import java.util.List;
import java.util.stream.Collectors;

//combines pixels of similar values
public class PixelBlender {
	private int previousValue = 0;
	private List<List<Integer>> data = null;
	
	public PixelBlender(List<List<Integer>> data) {
		this.data = applyBlending(data);
	}
	
	public List<List<Integer>> getBlendedData() {
		return this.data;
	}
	
	//inserts shifted pixel values into data
	private List<List<Integer>> applyBlending(List<List<Integer>> data) {
		previousValue = data.get(0).get(0);
		data = data.stream().map(l -> l.stream().map(i -> blend(previousValue, i, 10000)).collect(Collectors.toList())).collect(Collectors.toList());
		
		return data;
	}
	
	private int blend(int previousValue, int currentValue, int maxDifference) {
		if((currentValue - previousValue < maxDifference) && (currentValue - previousValue > -maxDifference)) {
			this.previousValue = currentValue;
			return previousValue;
		}else {
			this.previousValue = currentValue;
			return currentValue;
		}
	}
}

/*
package cif.core;

import java.util.ArrayList;

import cif.convenience.GlobalUtils;

//combines pixels of similar values
public class PixelShifter {
	public PixelShifter(int[][] data) {
		data = insertShiftedValues(flipData(data));
	}
	
	//inserts shifted pixel values into data
	@SuppressWarnings("unchecked")
	private int[][] insertShiftedValues(int[][] data) {
		ArrayList<Integer> sortedList = new ArrayList<Integer>(GlobalUtils.array2DToList(data, true));
		
		for(int[] a : data) {
			int i = 0;
			for(int b : a) {
				int previousValue = 0;
				for(int k : sortedList) {
					if((b == k || b == previousValue) && (k - previousValue < 5 && k - previousValue > 0)) {
						a[i] = previousValue;
					}
					
					previousValue = k;
				}
				
				i++;
			}
		}
		
		return data;
	}
	
	//flips the signs of data values, e.g. if the input is negative, the output will be positive
	private int[][] flipData(int[][] data) {
		for(int[] a : data) {
			int i = 0;
			for(int b : a) {
				a[i] = b * (-1);
				i++;
			}
		}
		
		return data;
	}
}
*/