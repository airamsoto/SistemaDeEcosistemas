package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;

public class SelectYoungestBuilder extends Builder<SelectionStrategy> {

	public SelectYoungestBuilder() {
		super("SelectYoungest", "Genera SelectYoungest");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {
		// if(data == null) throw new IllegalArgumentException ("'info' cannot be
		// null");
		return new SelectYoungest();
	}

}
