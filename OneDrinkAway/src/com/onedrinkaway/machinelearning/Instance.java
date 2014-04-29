package com.onedrinkaway.machinelearning;

import java.util.HashMap;
import java.util.Map;

public class Instance {
	public String label;
	public Object name;   //this field just serve as a reference
	public Map<String, Double> features;
	
	public Instance() {
		features = new HashMap<String, Double>();
	}
	
	public Instance(String[] info) {
		this();
		if(info == null || info.length < 2) {
			throw new IllegalArgumentException();
		}
		label = info[0];
		for(int i = 1; i < info.length; i++) {
			features.put("f"+i, Double.parseDouble(info[i]));
		}
	}
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("[");
		for(String featName : features.keySet()) {
			res.append(featName+" : "+features.get(featName)+", ");
		}
		res.append("]");
		return res.toString();
	}
}
