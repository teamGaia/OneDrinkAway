package com.onedrinkaway.model.machinelearning;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.onedrinkaway.model.Drink;


public class TestKNN {

	public static void main(String[] args) throws IOException {
		BufferedReader brTrain = new BufferedReader(new FileReader("train.csv"));
		String line;
		List<Drink> trainingSet = new LinkedList<Drink>();
		while ((line = brTrain.readLine()) != null) {
		   Drink curDrink = buildDrink(line);
		   trainingSet.add(curDrink);
		}
		KNearestNeighborModel model = new KNearestNeighborModel(5); 
		brTrain.close();
		
		BufferedReader brTest = new BufferedReader(new FileReader("test.csv"));
		line = null;
		int allCount = 0;
		int rightCount = 0;
		while((line = brTest.readLine()) != null) {
			Drink curDrink = buildDrink(line);
			double res = model.predictRating(curDrink);
			
			if((res < 0 && curDrink.getAvgRating() < 0) ||
				(res >= 0 && curDrink.getAvgRating() >= 0)) {
				rightCount++;
			}
			allCount++;
		}
		brTest.close();
		System.out.println("accuracy: "+((double) rightCount/allCount));
	}
	
	private static Drink buildDrink(String line) {
		line = line.trim();
		   String[] info = line.split(",");
		   int[] attributes = new int[info.length-1];
		   for(int i = 1; i < info.length; i++) {
				attributes[i-1] = Integer.parseInt(info[i]);
		   }
		   return new Drink("aaa", 1, Double.parseDouble(info[0]), attributes, new LinkedList<String>(), "aaa");
	}

}
