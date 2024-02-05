package simulator.control;

import java.io.OutputStream;

import org.json.JSONObject;

import simulator.model.MapInfo;
import simulator.model.Simulator;
import simulator.view.SimpleObjectViewer;

public class Controller {
	private Simulator _sim;
	
	public Controller(Simulator sim) {
		this._sim = sim;
	}
	public void load_data(JSONObject data) {
		
	}
	public void run(double t, double dt, boolean sv, OutputStream out) {
		JSONObject init_state = new JSONObject();
		JSONObject final_state = new JSONObject();
		JSONObject return_state = new JSONObject();
		init_state = this._sim.as_JSON();
		while (this._sim.get_time()>t) {
			this._sim.advance(dt);
		}
		final_state = this._sim.as_JSON();
		return_state.put("in:", init_state);
		return_state.put("out:", final_state);
		
		//NO SE SI ES ASI, MIRAR BIEN EL VISOR DE OBJETOS
		
		if(sv) {
			SimpleObjectViewer view = null;
			MapInfo m = _sim.get_map_info();
			view = new SimpleObjectViewer("[ECOSYSTEM]",
			m.get_width(), m.get_height(),
			m.get_cols(), m.get_rows());
			view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
			

		}
	}

}
