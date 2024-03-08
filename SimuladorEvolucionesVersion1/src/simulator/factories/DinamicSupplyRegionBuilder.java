package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.DynamicSupplyRegion;
import simulator.model.Region;

public class DinamicSupplyRegionBuilder extends Builder<Region> {

	public DinamicSupplyRegionBuilder() {
		super("dynamic", "Genera DinamicSupplyRegion");

	}

	@Override
	protected Region create_instance(JSONObject data) {
		double food = 100.0;
		double factor = 2.0;
		try {
			if (data.has("food")) {
				food = data.getDouble("food");
			}
			if (data.has("factor")) {
				factor = data.getDouble("factor");
			}
		} catch(JSONException e) {
			throw new IllegalArgumentException("Invalid argument for food or factor");
		}
		

		return new DynamicSupplyRegion(food, factor);
	}

	@Override
	protected void fill_in_data(JSONObject o) {
		o.put("food", 2.5);
		o.put("factor", 1250.0);

	}

}
