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
	@Override 
	public void fill_in_data(JSONObject o) {
		if(o.get("mate_strategy") == null) { //lo de get type se puso solo no se si habraia que pasarle "mate_strategy"
			//SelectFirst
		}
		if(o.get("pos") == null) {
		
		} 
		
	}

}
