package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectionStrategy;

public class SelectFirstBuilder extends Builder <SelectionStrategy>{

	public SelectFirstBuilder () {
		super ("hola", "hola");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SelectionStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

}
