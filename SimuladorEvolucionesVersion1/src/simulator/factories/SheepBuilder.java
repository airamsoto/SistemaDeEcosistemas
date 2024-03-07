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
		// mirar pos expeciones
		Vector2D pos = null;
		if (data.has("pos")) {
			JSONObject jPos = data.getJSONObject("pos");
			JSONArray jX = jPos.getJSONArray("x_range");
			JSONArray jY = jPos.getJSONArray("y_range");
			double pedro1 = jX.getDouble(0);
			double pedro2 = jX.getDouble(1);
			double sara1 = jY.getDouble(0);
			double sara2 = jY.getDouble(1);
			pos = new Vector2D(Utils._rand.nextDouble(pedro1, pedro2), Utils._rand.nextDouble(sara1, sara2));
		}
		return new Sheep(mate, danger, pos);
		// throw new IllegalArgumentException("Unrecognized 'info': " +
		// data.toString());
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
