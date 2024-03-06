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
	protected Region create_instance(JSONObject data) { // throws Exception{
		// Esta excepcion tampoco hace falta??
		// aunque sea nulo el JSON se crea la region
		// if(data == null) throw new IllegalArgumentException ("'info' cannot be
		// null");
		double food = 100.0;
		double factor = 2.0;
		if (data.has("food")) {
			food = data.getDouble("food");
		}
		if (data.has("factor")) {
			factor = data.getDouble("factor");
		}
		return new DynamicSupplyRegion(food, factor);
		// No se lanza excepcion porque food y factor siempre son validos
		// esto no genera instancia del tipo T asi que no puede dar fallo
		// throw new IllegalArgumentException ("");
	}

	@Override
	protected void fill_in_data(JSONObject o) {
		o.put("food", 2.5);
		o.put("factor", 1250.0);

	}

}
