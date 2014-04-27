import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface MLModel {
	/**
	 * 
	 * @param instances: the instances used to train the model
	 */
	public void train(List<Instance> instances);
	
	/**
	 * 
	 * @param instance
	 * @return: a map with score of each predicted class, higher the score,
	 * more probably the class instance belongs to this class
	 * @throws IOException 
	 */
	public Map<String, Double> predict(Instance instance) throws IOException;
}
