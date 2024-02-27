package simulator.control;

import java.io.OutputStream;
import java.util.List;
import java.util.*;
import org.json.JSONObject;

import simulator.model.AnimalInfo;
import simulator.model.MapInfo;
import simulator.model.Simulator;
import simulator.view.SimpleObjectViewer;
import simulator.view.SimpleObjectViewer.ObjInfo;

public class Controller {
	private Simulator _sim;

	public Controller(Simulator sim) {
		this._sim = sim;
	}

	public void load_data(JSONObject data) {
		// Hacer lectura de los arrayList de region y animal

		// _sim.set_region();

		// _sim.st_animal();
	}

	public void run(double t, double dt, boolean sv, OutputStream out) {

		SimpleObjectViewer view = null;
		if (sv) {
			MapInfo m = _sim.get_map_info();
			view = new SimpleObjectViewer("[ECOSYSTEM]", m.get_width(), m.get_height(), m.get_cols(), m.get_rows());
			view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
		}

		JSONObject init_state = new JSONObject();
		JSONObject final_state = new JSONObject();
		JSONObject return_state = new JSONObject();
		init_state = this._sim.as_JSON();
		while (this._sim.get_time() > t) {
			this._sim.advance(dt);
			if (sv)
				view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
		}
		final_state = this._sim.as_JSON();
		return_state.put("in:", init_state);
		return_state.put("out:", final_state);

		// NO SE SI ES ASI, MIRAR BIEN EL VISOR DE OBJETOS
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
