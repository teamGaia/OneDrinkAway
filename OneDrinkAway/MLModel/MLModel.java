import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface MLModel {
	/**
	 * @param trainingSet: List of training examples to create the model
	 **/ 
	public void train(List<Drink> trainingSet);
	
	/**
	 * @param sample: the Drink to predict the rating
	 * @return the predicted rating (0.0-5.0)
	 **/
	public double predictRating(Drink sample);
}
