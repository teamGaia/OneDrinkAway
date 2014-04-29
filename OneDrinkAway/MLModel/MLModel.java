import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface MLModel {
	/**
	 * 
	 * @param instances: the instances used to train the model
	 */
	public double predictRating(List<Drink> trainingSet, Drink sample);
}
