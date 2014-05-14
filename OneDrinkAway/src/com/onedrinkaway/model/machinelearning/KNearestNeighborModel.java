package com.onedrinkaway.model.machinelearning;


import java.util.*;

import com.onedrinkaway.common.Drink;



public class KNearestNeighborModel implements MLModel {
  private List<Drink> trainingSet;
  
  private int K;
	//private List<Instance> instances;
  private Drink curIns;
  
  public void train(List<Drink> trainingSet) {
    this.trainingSet = trainingSet;
  }
  
  /**
   * @effect construct a 5 nearest neighbor classfier
   */
  public KNearestNeighborModel() {
	  this(5);
  }
  
  /**
	 * @effect construct a K nearest neighbor classfier
	 * @param K: number of nearest neighbor
	 */
  public KNearestNeighborModel(int K) {
	this.K = K;
  }
	
	/**
	 * @effect construct a trained K nearest neighbor classfier
	 * @param K: number of nearest neighbor
	 */
  public KNearestNeighborModel(int K, List<Drink> instances) {
	  this(K);
	  train(instances);
  }
  
  public double predictRating(Drink sample) {
    //return 0.0;
	  this.curIns = sample;
		Queue<Drink> topK = new PriorityQueue<Drink>(1, new Comparator<Drink>() {
			@Override
			public int compare(Drink arg0, Drink arg1) {
				double dis0 = getDis(arg0);
				double dis1 = getDis(arg1);
				if(dis0 < dis1) {
					return 1;
				} else if(dis0 > dis1) {
					return -1;
				}
				return 0;
			}
		});
		//<get K nearest neighbours>
		for(Drink ins : trainingSet) {
			topK.add(ins);
			if(topK.size() > K) {
				topK.remove();
			}
		}
		//</get K nearest neighbours>
		
		//<get average>
		int count = topK.size();
		double sum = 0.0;
		while(!topK.isEmpty()) {
			sum += topK.remove().getRating();
		}
		double ave = sum/count;
		return ave;
		//<get average>
		//<build them into matrix>
		/*
		double[][] X = new double[topK.size()][];
		double[][] Y = new double[topK.size()][1];
		int i = 0;
		while(!topK.isEmpty()) {
			Drink curSelected = topK.remove();
			//X[i] = curSelected.attributes;
			double[] curVec = new double[curSelected.attributes.length];
			for(int j = 0; j < curVec.length; j++) {
				curVec[j] = (double)curSelected.attributes[j];
			}
			X[i] = curVec;
			Y[i][0] = curSelected.getRating();
			i++;
		}
		Matrix Xm = new Matrix(X);
		Matrix Ym = new Matrix(Y);
		Matrix Xtrans = Xm.transpose();
		try {
			Matrix b = Xtrans.times(Xm).inverse().times(Xtrans).times(Ym);
			System.out.println("a");
			double[][] x = new double[1][curIns.attributes.length];
			Matrix xm = new Matrix(x);
			return xm.times(b).get(0, 0);
		} catch(Exception e) {
			System.out.println("b");
			return 0.0;
		}
		*/
		//</build them into matrix>
  }
  
  /**
	 * get distance between two instance
	 * @param thisIns
	 * @return
	 */
	private double getDis(Drink thisIns) {
		double sumDis = 0.0;
		for(int i = 0; i < thisIns.attributes.length && i < curIns.attributes.length; i++) {
			sumDis += (thisIns.attributes[i]-curIns.attributes[i])*(thisIns.attributes[i]-curIns.attributes[i]);
		}
		return Math.sqrt(sumDis);
	}
}
