package simulator.factories;

import simulator.model.*;

import org.json.JSONObject;

public class DefaultRegionBuilder extends Builder<Region> {

	public DefaultRegionBuilder() {
		super("default", "Genera DefaultRegion");
	}

	@Override
	protected Region create_instance(JSONObject data) {

		return new DefaultRegion();

	}
}
