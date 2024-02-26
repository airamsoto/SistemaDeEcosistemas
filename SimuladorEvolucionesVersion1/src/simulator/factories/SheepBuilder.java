package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Sheep;
import simulator.misc.Utils;
import simulator.model.*;
import simulator.misc.*;
public class SheepBuilder extends Builder<Animal> {
	private Factory <SelectionStrategy> selectionFactory;

	public SheepBuilder(Factory <SelectionStrategy> selectionStrategy) {
		super("Sheep", "Genera Oveja");
		//NO PONER LOS DOS PARAMTREOS POR DEFECTO
	}

	@Override
	protected Sheep create_instance(JSONObject data) throws Exception {
		SelectionStrategy mate= new SelectFirst();
		SelectionStrategy danger = new SelectFirst();
		if(data.has("mate_strategy")) {
			mate = this.selectionFactory.create_instance(data.getJSONObject("mate_strategy"));
		}
		if(data.has("danger_strategy")) {
			danger = this.selectionFactory.create_instance(data);
		}
		Vector2D pos = null;
		if(data.has("pos")) {
			JSONObject jPos = data.getJSONObject("pos");
			JSONArray jX = jPos.getJSONArray ("x_range");
			JSONArray jY = jPos.getJSONArray ("y_range");
			double pedro1 = jX.getDouble(0);
			double pedro2 = jX.getDouble(1);
			double sara1 = jY.getDouble(0);
			double sara2 = jY.getDouble(1);
			pos = new Vector2D(Utils._rand.nextDouble(pedro1, pedro2), Utils._rand.nextDouble(sara1, sara2));
		} 
		return new Sheep (mate, danger, pos);
	}
	@Override 
	public void fill_in_data(JSONObject o) {
		o.accumulate("mate_strategy", null);
		o.accumulate("hunt_strategy", null);
		o.accumulate("pos" + ":" + "[", 100.0);
		
	}

}
