/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.machinelearning;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;
import java.io.PrintStream;

// save before change to  extend comparable
public class c {
	public static String outPath = null;
	public static String debugPath = null;
	public static PrintStream outPrint = null;
	public static PrintStream debugPrint = null;
	public static StringBuilder outBuffer = null;
	public static StringBuilder debugBuffer = null;
	
	public static String o(Object s) throws IOException {
		if(outBuffer != null) {
			outBuffer.append(s.toString()+"\n");
		} else if(outPath != null) {
			//write2File(s.toString(), outPath);
			if(outPrint == null) {
				outPrint = new PrintStream(outPath);
			}
			outPrint.println(s.toString());
		} else {
			System.out.println(s.toString());
		}
		return s.toString();
	}
	
	public static String d(Object s) throws IOException {
		
		if(debugBuffer != null) {
			debugBuffer.append(s.toString()+"\n");
		} else if(debugPath != null) {
			//write2File(s.toString(), debugPath);
			if(debugPrint == null) {
				debugPrint = new PrintStream(debugPath);
			}
			debugPrint.println(s.toString());
		} else {
			System.out.println(s.toString());
		}
		
		return s.toString();
	}
	
	public static String o1(Object s) throws IOException {
		if(outBuffer != null) {
			outBuffer.append(s.toString());
		} else if(outPath != null) {
			write2File(s.toString(), outPath);
		} else {
			System.out.print(s.toString());
		}
		return s.toString();
	}
	
	public static String d1(Object s) throws IOException {
		if(debugBuffer != null) {
			debugBuffer.append(s.toString());
		} else if(debugPath != null) {
			write2File(s.toString(), debugPath);
		} else {
			System.out.print(s.toString());
		}
		return s.toString();
	}
	
	public static String readFile(String file) throws IOException {
		StringBuilder ret = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			ret.append(line+"\n");
		}
		reader.close();
		return ret.toString();
	}
	
	public static BufferedReader readFileByLine(String file) throws FileNotFoundException {
		return new BufferedReader(new FileReader(file));
	}
	
	public static void write2File(String content, String file) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		out.print(content);
	    out.close();
	}
	
	public static void write2Fileln(String content, String file) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		out.println(content);
	    out.close();
	}
	
	public static <T extends Comparable<T>> SortedMap<T, List<String>> produceFreqToToken(Map<String, T> gramMap) {   //This version may have problems
		SortedMap<T, List<String>> ret = new TreeMap<T, List<String>>();
		for(String token : gramMap.keySet()) {    
			T freq = gramMap.get(token);
			if(ret.containsKey(freq)) {
				ret.get(freq).add(token);
			} else {
				List<String> tokenList = new LinkedList<String>();
				tokenList.add(token);
				ret.put(freq, tokenList);
			}
		}
		return ret;
	}
	
	//pre: input word to count map, the map does not need to be sorted
	//post: return the stack which can give you the decent word to frequency result by keep on popping it
	//note: all the pairs are expressed by "word frequency", seperated by space
	public static Stack<String> getWordDecentFreqOrder(Map<String, Integer> gramMap) {
		SortedMap<Integer, List<String>> orderMap = produceFreqToToken(gramMap);
		Stack<String> ret = new Stack<String>();
		for(int freq : orderMap.keySet()) {
			List<String> wordList = orderMap.get(freq);
			for(String cur : wordList) {
				ret.push(cur+" "+freq);
			}
		}
		return ret;
	}
	
	public static <T extends Comparable<T>> Stack<String> getWordDecentOrder(Map<String, T> gramMap) {
		SortedMap<T, List<String>> orderMap = produceFreqToToken(gramMap);
		Stack<String> ret = new Stack<String>();
		for(T order : orderMap.keySet()) {
			List<String> wordList = orderMap.get(order);
			for(String cur : wordList) {
				ret.push(cur+" "+order);
			}
		}
		return ret;
	}
	
	
	//pre: input word to count map, the map does not need to be sorted
		//post: return the stack which can give you the decent word to frequency result by keep on popping it
		//note: all the pairs are expressed by "word frequency", seperated by space
		public static Stack<String> getWordDecentProbOrder(Map<String, Integer> gramMap) {
			SortedMap<Integer, List<String>> orderMap = produceFreqToToken(gramMap);
			int totalCount = 0;
			Iterator<Integer> i = orderMap.keySet().iterator();
			while(i.hasNext()) {
				totalCount += i.next();
			}
			Stack<String> ret = new Stack<String>();
			for(int freq : orderMap.keySet()) {
				List<String> wordList = orderMap.get(freq);
				for(String cur : wordList) {
					ret.push(cur+" "+(double)freq/totalCount);
				}
			}
			return ret;
		}
	
	//pre: none of the inputs should be null
	//post: increase the count of element by one
	public static void increaseCount(Map<String, Integer> countMap, String element) {
		if(countMap == null || element == null) {
			throw new IllegalArgumentException("none of the inputs should be null");
		}
		if(countMap.containsKey(element)) {
			countMap.put(element, countMap.get(element)+1);
		} else {
			countMap.put(element, 1);
		}
	}
	
	//pre: input the path of the directory, the path is expressed by parentDir/dirNeedToCreated
	//post: make the directory according to the dirPath. print the error log, if it's not created
	//note: to ensure that the current directory is created, you must ensure the path to the directory, or say the parent directory exists
	public static void makDirifNotExist(String dirPath) throws IOException {
		File theDir = new File(dirPath);
		  // if the directory does not exist, create it
		if (!theDir.exists()) {
			//System.out.println("creating directory: " + directoryName);
			boolean result = theDir.mkdir();  
			if(!result) {    
				d("DIR:"+dirPath+" not created");  
			}
		} else {
			d("DIR:"+dirPath+" already exists");  
		}
	}

		//pre: input the path of directory
	//post: output the files in this directory in assending natural order.
	public static PriorityQueue<String> getAllFiles(String dirPath) {
		PriorityQueue<String> allFiles = new PriorityQueue<String>();
		String file;
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				file = listOfFiles[i].getName();
				allFiles.add(file);
			}
		}
		return allFiles;
	}
	
	public static double log(double x, double base) {
	    return (Math.log(x) / Math.log(base));
	}
}
