/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.machinelearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class RunML {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		MLModel model = new KNN_Naive(5);
		BufferedReader trainBr = c.readFileByLine("train.csv");
		String line = null;
		List<Instance> insList = new LinkedList<Instance>();
		while((line = trainBr.readLine()) != null) {
			String[] curLine = line.split(",");
			insList.add(new Instance(curLine));
		}
		model.train(insList);
		trainBr.close();
		
		BufferedReader testBr = c.readFileByLine("test.csv");
		line = null;
		int rightCount = 0;
		int wrongCount = 0;
		int insLength = 0;
		while((line = testBr.readLine()) != null) {
			String[] curLine = line.split(",");
			insLength = curLine.length;
			Instance curIns = new Instance(curLine);
			Map<String, Double> res = model.predict(curIns);   
			Stack<String> resStr = c.getWordDecentOrder(res);
			String label = resStr.peek().split(" ")[0];
			if(curIns.label.equals(label)) {
				rightCount++;
			} else {
				wrongCount++;
			}
		}
		testBr.close();
		long endTime = System.currentTimeMillis();
		c.o("The accuracy is: "+((double)rightCount/(rightCount+wrongCount)));
		c.o("The time consumption is: "+((double)(endTime - startTime)/1000));
		c.o("One instance length is: "+insLength);
	}

}
