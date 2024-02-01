package simulator.model;

import java.util.List;
import java.util.function.Predicate;

import org.json.JSONObject;

public class RegionManager implements AnimalMapView {
	
	void set_region(int row, int col, Región r) {

	}

	void register_animal(Animal a) {

	}

	void unregister_animal(Animal a) {

	}

	void update_animal_region(Animal a) {

	}

	public double get_food(Animal a, double dt) {

	}

	void update_all_regions(double dt) {

	}

	public List<Animal> get_animals_in_range(Animal a, Predicate<Animal> filter) {

	}

	public JSONObject as_JSON() {
	}

	@Override
	public int get_cols() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_rows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_region_width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_region_height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Animal> get_animals_in_range(Animal e, Predicate<Animal> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
