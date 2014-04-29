package com.onedrinkaway.machinelearning;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;


public class KNN_Naive implements MLModel{
	private int K;
	private List<Instance> instances;
	private Instance curIns;
	
	public KNN_Naive(int K) {
		this.K = K;
	}
	
	public KNN_Naive(int K, List<Instance> instances) {
		this(K);
		train(instances);
	}
	
	@Override
	public void train(List<Instance> instances) {
		this.instances = instances;
	}

	@Override
	public Map<String, Double> predict(Instance instance) throws IOException {
		this.curIns = instance;
		Queue<Instance> topK = new PriorityQueue<Instance>(1, new Comparator<Instance>() {
			@Override
			public int compare(Instance arg0, Instance arg1) {
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
		for(Instance ins : instances) {
			topK.add(ins);
			if(topK.size() > K) {
				topK.remove();
			}
		}
		//</get K nearest neighbours>
		//<build the result map>
		Map<String, Double> res = new TreeMap<String, Double>();
		double maxDis = 0.0;
		for(Instance curIns : topK) {
			double curDis = getDis(curIns);
			if(curDis > maxDis) {
				maxDis = curDis;
			}
		}
		while(!topK.isEmpty()) {
			Instance curNear = topK.remove();
			double curDis = getDis(curNear);
			if(!res.containsKey(curNear.label)) {
				res.put(curNear.label, maxDis-curDis);
			} else {
				res.put(curNear.label, res.get(curNear.label)+maxDis-curDis);
			}
		}
		return res;
	}
	
	private double getDis(Instance thisIns) {
		double sumDis = 0.0;
		for(String featName : thisIns.features.keySet()) {
			double thisInsVal = thisIns.features.get(featName);
			double curInsVal = curIns.features.get(featName);
			sumDis += (thisInsVal-curInsVal)*(thisInsVal-curInsVal);
		}
		return Math.sqrt(sumDis);
	}
}
