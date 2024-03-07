package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.factories.Factory;

public class Simulator implements JSONable {
	private Factory<Animal> _animalFactory;
	private Factory<Region> _regionFactory;
	private double _time;
	private RegionManager _regionManager;
	private List<Animal> _animalList;

	public Simulator(int cols, int rows, int width, int height, Factory<Animal> animals_factory,
			Factory<Region> regions_factory) {
		this._time = 0.0;
		this._regionFactory = regions_factory;
		this._animalFactory = animals_factory;
		this._animalList = new ArrayList<Animal>();
		this._regionManager = new RegionManager(cols, rows, width, height);

	}

	private void set_region(int row, int col, Region r) {
		this._regionManager.set_region(row, col, r);
	}

	public void set_region(int row, int col, JSONObject r_json) {
		this.set_region(row, col, this._regionFactory.create_instance(r_json));
	}

	private void add_animal(Animal a) {
		this._animalList.add(a);
		this._regionManager.register_animal(a);

	}

	public void add_animal(JSONObject a_json) {
		this.add_animal(this._animalFactory.create_instance(a_json));

	}

	public MapInfo get_map_info() {
		return this._regionManager;
	}

	public List<? extends AnimalInfo> get_animals() {
		return Collections.unmodifiableList(this._animalList);

	}

	public double get_time() {
		return this._time;
	}

	public void advance(double dt) {

		this._time += dt;

		List<Animal> animals = new ArrayList<Animal>();
		for (Animal animal : _animalList) {
			if (animal.get_state() == State.DEAD) {
				animals.add(animal);
				this._regionManager.unregister_animal(animal);
			}
		}
		_animalList.removeAll(animals);
		for (Animal animal : _animalList) {
			animal.update(dt);
			this._regionManager.update_animal_region(animal);
		}

		List<Animal> babys = new ArrayList<Animal>();
		for (Animal animal : _animalList) {
			if (animal.is_pregnant()) {
				babys.add(animal.deliver_baby());
			}
		}

		for (Animal animal : babys) {
			this.add_animal(animal);
		}

		this._regionManager.update_all_regions(dt);
	}

	public JSONObject as_JSON() {
		JSONObject JSONreturn = new JSONObject();
		JSONreturn.put("time: ", this._time);
		JSONreturn.put("state: ", this._regionFactory.get_info());
		return JSONreturn;
	}

}