package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;

public class SelectYoungestBuilder extends Builder<SelectionStrategy> {

	public SelectYoungestBuilder(String type_tag, String desc) {
		super(type_tag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {
		return new SelectYoungest();
	}

	

}
