package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;

public class SelectYoungestBuilder extends Builder<SelectionStrategy> {

	public SelectYoungestBuilder() {
		super("youngest", "Genera SelectYoungest");
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {

		return new SelectYoungest();
	}

}
