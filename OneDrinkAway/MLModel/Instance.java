import java.util.Map;

public class Instance {
	public String label;
	public Object name;   //this field just serve as a reference
	public Map<String, Double> features;
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("[");
		for(String featName : features.keySet()) {
			res.append(featName+" : "+features.get(featName)+", ");
		}
		res.append("]");
		return res.toString();
	}
}
