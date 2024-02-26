package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectFirst;
import simulator.model.SelectionStrategy;

public class SelectFirstBuilder extends Builder <SelectionStrategy>{

	public SelectFirstBuilder () {
		super ("hola", "hola");
		// TODO Auto-generated constructor stubS
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		return new SelectFirst();
	}

}
