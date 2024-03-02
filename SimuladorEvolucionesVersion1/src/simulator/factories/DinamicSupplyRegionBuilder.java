package simulator.factories;

import org.json.JSONObject;

import simulator.model.DynamicSupplyRegion;
import simulator.model.Region;

public class DinamicSupplyRegionBuilder extends Builder<Region> {

	public DinamicSupplyRegionBuilder() {
		super("dynamic", "Genera DinamicSupplyRegion");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Region create_instance(JSONObject data) {
		double food = 100.0;
		double factor = 2.0;
		if(data.has("food")) {
			food = data.getDouble("food");
		}
		if(data.has("factor")) {
			factor = data.getDouble("factor");
		}
		return new DynamicSupplyRegion(food, factor);
	}

}
