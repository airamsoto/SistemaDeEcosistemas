package simulator.model;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.json.JSONObject;

public class RegionManager implements AnimalMapView {
	private int _cols;
	private int _rows;
	private int _width;
	private int _height;
	private Region _regions;
	private Map<Animal, Region> _animal_region;

	
	public RegionManager(int cols, int rows, int width, int height) {
		this._cols = cols;
		this._rows = rows;
		this._width = width;
		this._height = height;
		//falta hacer lo de dividir altura entre numero de columna
		//pone que hay que usar la constructora por defectoq
		DefaultRegion df = new DefaultRegion();
		this._regions = df;
		this._animal_region = new HashMap();
		//falta inicializar animal region
		
	}
	
	void set_region(int row, int col, Region r) {
		this._cols = col;
		this._rows = row;
		this._animal_region.keySet(); //no se si directamaente coge lo de animal
		
		//falta ponerle la region y lo de animAL

	}

	void register_animal(Animal a) {
		this._animal_region.put(a, _regions); //falta ver si el segundo parametro es regions

	}

	void unregister_animal(Animal a) {

	}

	void update_animal_region(Animal a) {

	}

	public double get_food(Animal a, double dt) {
		return dt;

	}

	void update_all_regions(double dt) {

	}

	public List<Animal> get_animals_in_range(Animal a, Predicate<Animal> filter) {
		return null;

	}

	public JSONObject as_JSON() {
		return null;
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



}
