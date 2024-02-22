package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.Wolf;

public class WolfBuilder extends Builder<Wolf> {

	public WolfBuilder(String type_tag, String desc) {
		super("Wolf", "Genera Lobo");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Wolf create_instance(JSONObject data) {
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

}
