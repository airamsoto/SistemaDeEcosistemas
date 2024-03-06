package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectClosest;
import simulator.model.SelectionStrategy;

public class SelectClosestBuilder extends Builder<SelectionStrategy> {

	public SelectClosestBuilder() {
		super("SelectClosest", "Genera SelectClosest");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {
		// if(data == null) throw new IllegalArgumentException ("'info' cannot be
		// null");
		return new SelectClosest();
	}

}
