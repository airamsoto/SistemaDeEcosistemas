package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Sheep;
import simulator.misc.Utils;
import simulator.model.*;
import simulator.misc.*;

public class SheepBuilder extends Builder<Animal> {
	private Factory<SelectionStrategy> selectionFactory;

	public SheepBuilder(Factory<SelectionStrategy> selectionStrategy) {
		super("sheep", "Genera Oveja");
		this.selectionFactory = selectionStrategy; 
	}

	@Override
	protected Animal create_instance(JSONObject data) throws IllegalArgumentException {

		SelectionStrategy mate = new SelectFirst();
		SelectionStrategy danger = new SelectFirst();

		if (data.has("mate_strategy")) {
			mate = this.selectionFactory.create_instance(data.getJSONObject("mate_strategy"));	
		}
		if (data.has("danger_strategy")) {
			danger = this.selectionFactory.create_instance(data.getJSONObject("danger_strategy"));
		}

		Vector2D pos = null;
		if (data.has("pos")) {
			JSONObject jPos = data.getJSONObject("pos");
			JSONArray jX = jPos.getJSONArray("x_range");
			JSONArray jY = jPos.getJSONArray("y_range");
			try {
				double x1 = jX.getDouble(0);
				double x2 = jX.getDouble(1);
				double y1 = jY.getDouble(0);
				double y2 = jY.getDouble(1);
				pos = new Vector2D(Utils._rand.nextDouble(x1, x2), Utils._rand.nextDouble(y1, y2));
			} catch (Exception e) {
				throw new IllegalArgumentException ("Invalid argument pos");
			}	
		}
		return new Sheep(mate, danger, pos);
	}

	@Override
	public void fill_in_data(JSONObject o) {
		JSONObject jpos = new JSONObject();
		JSONArray jAx = new JSONArray();
		JSONArray jAy = new JSONArray();
		jAx.put(100.0);
		jAx.put(200.0);
		jAy.put(100.0);
		jAy.put(200.0);
		jpos.put("x_range", jAx);
		jpos.put("y_range", jAy);

		JSONObject j = new JSONObject();
		o.put("mate_strategy", j);
		o.put("danger_strategy", j);
		o.put("pos", jpos);

	}

}
