package com.onedrinkaway.machinelearning;

/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */


public class Drink {
	//public String label;
	public Object name;   //this field just serve as a reference
	//public Map<String, Double> features;
	
	private double rate;
	private int[] attributes;
	
	public Drink() {
		//features = new HashMap<String, Double>();
	}
	
	public int[] getAttributes() {
		return attributes;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
	public Drink(String[] info) {
		this();
		if(info == null || info.length < 2) {
			throw new IllegalArgumentException();
		}
		
		rate = Double.parseDouble(info[0]);
		attributes = new int[info.length-1];
		
		for(int i = 1; i < info.length; i++) {
			int val = Integer.parseInt(info[i]);
			//features.put("f"+i, val);
			attributes[i-1] = val;
		}
	}
	
	public String toString() {
		/*
		StringBuilder res = new StringBuilder();
		res.append("[");
		for(String featName : features.keySet()) {
			res.append(featName+" : "+features.get(featName)+", ");
		}
		res.append("]");
		return res.toString();
		*/
		return "";
	}
}
