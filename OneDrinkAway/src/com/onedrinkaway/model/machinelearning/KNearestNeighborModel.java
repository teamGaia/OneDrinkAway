/**
 * Class for predicting ratings of drinks based off of 
 * their similarity to the k most similar drinks in the 
 * training set. The distances are calculated based off
 * of the Euclidean distance between the attributes of
 * the drinks.
 */

package com.onedrinkaway.model.machinelearning;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.onedrinkaway.model.Drink;

public class KNearestNeighborModel implements MLModel {
	private List<Drink> trainingSet;

	private int K;

	/**
	 * @effect construct a 5 nearest neighbor classfier
	 */
	public KNearestNeighborModel() {
		this(5);
	}

	/**
	 * @effect construct a K nearest neighbor classfier
	 * @param K number of nearest neighbor
	 */
	public KNearestNeighborModel(int K) {
		this.K = K;
	}

	public void train(List<Drink> trainingSet) {
		this.trainingSet = trainingSet;
	}

	public double predictRating(final Drink sample) {
		Queue<Drink> topK = new PriorityQueue<Drink>(1,
				new Comparator<Drink>() {
					@Override
					public int compare(Drink arg0, Drink arg1) {
						double dis0 = getDis(arg0, sample);
						double dis1 = getDis(arg1, sample);
						if (dis0 < dis1) {
							return 1;
						} else if (dis0 > dis1) {
							return -1;
						}
						return 0;
					}
				});
		// <get K nearest neighbours>
		for (Drink ins : trainingSet) {
			topK.add(ins);
			if (topK.size() > K) {
				topK.remove();
			}
		}
		// </get K nearest neighbours>

		// <get average>
		double[] labels = new double[topK.size()];
		double[] distances = new double[topK.size()];
		int i = 0;
		while (!topK.isEmpty()) {
			Drink curIns = topK.remove();
			labels[i] = curIns.getUserRating();
			distances[i] = getDis(curIns, sample);
			i++;
		}
		double sumDis = 0.0;
		for (int j = 0; j < distances.length; j++) {
			sumDis += distances[j];
		}
		double predicted = 0.0;
		for (int j = 0; j < distances.length; j++) {
			predicted += labels[j]
					* (distances[distances.length - 1 - j] / sumDis);
		}
		return predicted;
	}

	/**
	 * get distance between two instance
	 * 
	 * @param thisIns
	 * @return
	 */
	private double getDis(Drink thisIns, Drink curIns) {
		double sumDis = 0.0;
		for (int i = 0; i < thisIns.attributes.length
				&& i < curIns.attributes.length; i++) {
			sumDis += (thisIns.attributes[i] - curIns.attributes[i])
					* (thisIns.attributes[i] - curIns.attributes[i]);
		}
		return Math.sqrt(sumDis);
	}
}
