package cif.core;

import java.util.ArrayList;
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
		//horizontal blending
		data = data.stream().map(l -> l.stream().map(i -> blend(previousValue, i, 10000)).collect(Collectors.toList())).collect(Collectors.toList());
		
		//vertical blending
		List<List<Integer>> verticalData = convertToVerticalList(data);
		previousValue = verticalData.get(0).get(0);
		verticalData = verticalData.stream().map(l -> l.stream().map(i -> blend(previousValue, i, 10000)).collect(Collectors.toList())).collect(Collectors.toList());
		
		return verticalData;
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
	
	private List<List<Integer>> convertToVerticalList(List<List<Integer>> data) {
		List<List<Integer>> verticalList = new ArrayList<>();
		
		int k = 0;
		
		while(k < data.get(0).size()) {
			ArrayList<Integer> sublist = new ArrayList<>();
			for(int i = 0; i < data.size(); i++) {
				sublist.add(data.get(i).get(k));
			}
			
			verticalList.add(sublist);
			k++;
		}
		return data;
	}
}