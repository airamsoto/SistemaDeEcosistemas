package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Sheep;
import simulator.misc.Utils;
import simulator.model.*;
import simulator.misc.*;
public class SheepBuilder extends Builder<Sheep> {
	private Factory <SelectionStrategy> SelectionFactory;

//creo que lo del constructor vacio era aui
	public SheepBuilder(Factory <SelectionStrategy> selectionStrategy) {
		super("Sheep", "Genera Oveja");
		//NO PONER LOS DOS PARAMTREOS POR DEFECTO
	
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Sheep create_instance(JSONObject data) {
	/*	SelectionStrategy jose
		if(data.has("mate_strategy")) { //lo de get type se puso solo no se si habraia que pasarle "mate_strategy"
			 = data.getJSONObject("mate_strategy");
		} else {
			
		}*/
		if(data.has("pos")) {
			JSONObject jPos = data.getJSONObject("pos");
			JSONArray jX = jPos.getJSONArray ("x_range");
			JSONArray jY = jPos.getJSONArray ("y_range");
			double pedro1 = jX.getDouble(0);
			double pedro2 = jX.getDouble(1);
			double sara1 = jY.getDouble(0);
			double sara2 = jY.getDouble(1);
			Vector2D pos = new Vector2D(Utils._rand.nextDouble(pedro1, pedro2), Utils._rand.nextDouble(sara1, sara2));
		} 
		return null;
	}
	@Override 
	public void fill_in_data(JSONObject o) {
		if(o.has("mate_strategy")) { //lo de get type se puso solo no se si habraia que pasarle "mate_strategy"
			o.get("mate_strategy");
		} else {
			
		}
		if(o.get("pos") == null) {
		
		} 
		
	}

}
