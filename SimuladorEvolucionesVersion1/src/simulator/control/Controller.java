package simulator.control;

import java.io.OutputStream;
import java.io.PrintStream;
import org.json.JSONArray;
import java.util.List;
import java.util.*;
import org.json.JSONObject;

import simulator.model.AnimalInfo;
import simulator.model.MapInfo;
import simulator.model.Simulator;
import simulator.view.SimpleObjectViewer;
import simulator.view.SimpleObjectViewer.ObjInfo;
//
public class Controller {
	private Simulator _sim;

	public Controller(Simulator sim) {
		this._sim = sim;
	}

	public void load_data(JSONObject data) {
		if (data.has("regions")) {
			JSONArray jRegions = data.getJSONArray("regions");
			for (int i = 0; i < jRegions.length(); i++) {
				JSONObject jRegion = jRegions.getJSONObject(i);
				JSONArray jRow = jRegion.getJSONArray("row");
				JSONArray jCol = jRegion.getJSONArray("col");
				JSONObject jSpec = jRegion.getJSONObject("spec");
				int rf = jRow.getInt(0);
				int rt = jRow.getInt(1);
				int cf = jCol.getInt(0);
				int ct = jCol.getInt(1);
				for (int r = rf; r <= rt; r++) {
					for (int c = cf; c <= ct; c++) {
						this._sim.set_region(r, c, jSpec);

					}
				}
			}
		}
		JSONArray jAnimals = data.getJSONArray("animals");
		for (int j = 0; j < jAnimals.length(); j++) {
			JSONObject jAnimal = jAnimals.getJSONObject(j);
			int amount = jAnimal.getInt("amount");
			JSONObject jSpecA = jAnimal.getJSONObject("spec");
			for (int i = 0; i < amount; i++) {
				this._sim.add_animal(jSpecA);
			}
		}
	}

	public void run(double t, double dt, boolean sv, OutputStream out) {

		SimpleObjectViewer view = null;
		PrintStream printer = new PrintStream(out);
		if (sv) {
			MapInfo m = _sim.get_map_info();
			view = new SimpleObjectViewer("[ECOSYSTEM]", m.get_width(), m.get_height(), m.get_cols(), m.get_rows());
			view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
		}

		JSONObject init_state = new JSONObject();
		JSONObject final_state = new JSONObject();
		JSONObject return_state = new JSONObject();
		init_state = this._sim.as_JSON();
		while (this._sim.get_time() < t) {
			this._sim.advance(dt);
			if (sv)
				view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
		}
		final_state = this._sim.as_JSON();
		return_state.put("in:", init_state);
		return_state.put("out:", final_state);
		printer.println(return_state);

		if (sv)
			view.close();
	}

	private List<ObjInfo> to_animals_info(List<? extends AnimalInfo> animals) {
		List<ObjInfo> ol = new ArrayList<>(animals.size());
		for (AnimalInfo a : animals)
			ol.add(new ObjInfo(a.get_genetic_code(), (int) a.get_position().getX(), (int) a.get_position().getY(),
					(int) Math.round(a.get_age()) + 2));
		return ol;
	}

}
