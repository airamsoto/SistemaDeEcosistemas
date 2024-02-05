package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.factories.Factory;

public class Simulator implements JSONable {
	private Factory<Animal> _animalFactory;
	private Factory<Region> _regionFactory;
	private RegionManager _regionManager;
	private double _time;

	public Simulator(int cols, int rows, int width, int height, Factory<Animal> animals_factory,
			Factory<Region> regions_factory) {
		this._time = 0.0;

	}

	private set_region(int row, int col, JSONObject r) {
		
	}

	void set_region(int row, int col, JSONObject r_json) {

	}

	private void add_animal(Animal a) {

	}

	public void add_animal(JSONObject a_json) {

	}

	public MapInfo get_map_info() {
		return this._regionManager;
	}

	public List<? extends Animalnfo> get_animals() {

	}

	public double get_time() {
		return this._time;
	}

	public void advance(double dt) {
		this._time += dt;
		/*
		 * Quitar todos los animales con estado DEAD de la lista de animales y
		 * eliminarlos del gestor de regiones. ○ Para cada animal: llama a su update(dt)
		 * y pide al gestor de regiones que actualice su región. ○ Pedir al gestor de
		 * regiones actualizar todas las regiones. ○ Para cada animal: si is_pregnant()
		 * devuelve true, obtenemos el bebé usando su método 14 deliver_baby() y lo
		 * añadimos a la simulación usando add_animal.
		 */

	}

	public JSONObject as_JSON() {
		JSONObject JSONreturn = new JSONObject();
		JSONreturn.put("time: ", this._time);
		JSONreturn.put("state: ", this._regionManager.as_JSON());

	}

	/*
	 * 
	 * Como puedes observar, hay dos versiones de los métodos add_animal y
	 * set_region, unas reciben la entrada como JSON mientras la otras reciben los
	 * objetos correspondientes después de crearlos. Las que reciben los objetos son
	 * private. El objetivo de tener las 2 versiones es facilitar el desarrollo y la
	 * depuración de la práctica: en tu primera implementación, antes de implementar
	 * las factorías, cambia esos métodos de private a public y úsalos directamente
	 * para añadir animales y regiones desde fuera. Solo cuando implementes las
	 * factorías cambialas a private de nuevo. De esta manera puedes depurar el
	 * programa sin haber implementado las factorías.
	 */

}
