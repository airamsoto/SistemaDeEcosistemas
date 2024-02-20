package simulator.factories;

import org.json.JSONObject;

public class SheepBuilder extends Builder<Object> {

//creo que lo del constructor vacio era aui
	public SheepBuilder() {
		super("Sheep", "Genera Oveja");
		//NO PONER LOS DOS PARAMTREOS POR DEFECTO
	
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

}
