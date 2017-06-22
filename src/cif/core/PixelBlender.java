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