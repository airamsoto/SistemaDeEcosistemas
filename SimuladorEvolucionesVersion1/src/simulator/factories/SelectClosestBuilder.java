package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectClosest;
import simulator.model.SelectionStrategy;

public class SelectClosestBuilder extends Builder<SelectionStrategy>{

	public SelectClosestBuilder() {
		super("SelectClosest", "Genera SelectClosest");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		return new SelectClosest ();
	}

}
