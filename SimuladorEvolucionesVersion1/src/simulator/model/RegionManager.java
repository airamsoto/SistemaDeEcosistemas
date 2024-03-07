package simulator.model;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;

public class RegionManager implements AnimalMapView {
	private int _cols;
	private int _rows;
	private int _width;
	private int _height;
	private Region[][] _regions;
	private Map<Animal, Region> _animal_region;
	private int widthcol;
	private int heightrow;

	public RegionManager(int cols, int rows, int width, int height) {
		this._cols = cols;
		this._rows = rows;
		this._width = width;
		this._height = height;
		this._regions = new Region[rows][cols];
		for (int i = 0; i < this._rows; i++) {
			for (int j = 0; j < this._cols; j++) {
				this._regions[i][j] = new DefaultRegion();
			}
		}
		this._animal_region = new HashMap<Animal, Region>();
		this.widthcol = (this._width / this._cols);
		this.heightrow = (this._height / this._rows);
	}

	void set_region(int row, int col, Region r) {

		if (row >= this._rows) {
			row = this._rows - 1;
		}
		if (col >= this._cols) {
			col = this._cols - 1;
		}
		if (row <= 0) {
			row = 0;
		}
		if (col <= 0) {
			col = 0;
		}
		for (Animal a : this._regions[row][col].animalList) {
			r.add_animal(a);
			this._animal_region.put(a, r);
		}

		this._regions[row][col] = r;

	}

	void register_animal(Animal a) {
		a.init(this);
		int i = (int) (a._pos.getX() / this.widthcol);
		int j = (int) (a._pos.getY() / this.heightrow);

		if (i >= this._cols) {
			i = this._cols - 1;
		}
		if (j >= this._rows) {
			j = this._rows - 1;
		}

		if (i <= 0) {
			i = 0;
		}
		if (j <= 0) {
			j = 0;
		}

		this._regions[j][i].add_animal(a);
		this._animal_region.put(a, this._regions[j][i]);

	}

	void unregister_animal(Animal a) {
		Region region = this._animal_region.get(a);
		region.remove_animal(a);
		this._animal_region.remove(a);

	}

	void update_animal_region(Animal a) {
		int i = (int) (a._pos.getX() / this.widthcol);
		int j = (int) (a._pos.getY() / this.heightrow);

		if (i >= this._cols) {
			i = this._cols - 1;
		}
		if (j >= this._rows) {
			j = this._rows - 1;
		}

		if (i <= 0) {
			i = 0;
		}
		if (j <= 0) {
			j = 0;
		}
		if (this._regions[j][i] != this._animal_region.get(a)) {
			this._animal_region.get(a).remove_animal(a);
			this._regions[j][i].add_animal(a);
			this._animal_region.put(a, this._regions[j][i]);

		}

	}

	public double get_food(Animal a, double dt) {
		return this._animal_region.get(a).get_food(a, dt);
	}

	void update_all_regions(double dt) {
		for (int i = 0; i < this._rows; i++) {
			for (int j = 0; j < this._cols; j++) {
				if (this._regions[i][j] != null)
					this._regions[i][j].update(dt);
			}
		}

	}

	public List<Animal> get_animals_in_range(Animal a, Predicate<Animal> filter) {
		List<Animal> al = new ArrayList<>();

		double upY = (a.get_position().getY() < a.get_sight_range()) ? 0
				: a.get_position().getY() - a.get_sight_range();
		double leftX = (a.get_position().getX() < a.get_sight_range()) ? 0
				: a.get_position().getX() - a.get_sight_range();
		double downY = ((a.get_position().getY() + a.get_sight_range()) >= this._height) ? _height - 1
				: (a.get_position().getY() + a.get_sight_range());
		double rightX = ((a.get_position().getX() + a.get_sight_range()) >= this._width) ? _width - 1
				: a.get_position().getX() + a.get_sight_range();

		int topR = (int) (upY / this.heightrow);
		int leftR = (int) (leftX / this.widthcol);
		int downR = (int) (downY / this.heightrow);
		int rightR = (int) (rightX / this.widthcol);

		topR = (topR > this._rows - 1) ? this._rows - 1 : topR;
		leftR = (leftR > this._cols - 1) ? this._cols - 1 : leftR;
		downR = (downR > this._rows - 1) ? this._rows - 1 : downR;
		rightR = (rightR > this._cols - 1) ? this._cols - 1 : rightR;

		for (int i = topR; i <= downR; i++) {
			for (int j = leftR; j <= rightR; j++) {
				for (Animal other : this._regions[i][j].getAnimals()) {
					if (other != a && filter.test(other)
							&& a.get_position().distanceTo(other.get_position()) < a.get_sight_range()) {
						al.add(other);
					}
				}
			}
		}
		return al;

	}

	public JSONObject as_JSON() {
		JSONObject json = new JSONObject();
		JSONObject jose = new JSONObject();
		JSONArray jlist = new JSONArray();
		for (int i = 0; i < this._rows; i++) {
			for (int j = 0; j < this._cols; j++) {
				json.put("row", i);
				json.put("col", j);
				json.put("data", this._regions[i][j].as_JSON());
				jlist.put(json);
			}
		}

		jose.put("regiones: ", jlist);
		return json;
	}

	@Override
	public int get_cols() {
		return this._cols;
	}

	@Override
	public int get_rows() {
		return this._rows;
	}

	@Override
	public int get_width() {
		return this._width;
	}

	@Override
	public int get_height() {
		return this._height;
	}

	@Override
	public int get_region_width() {
		return this.widthcol;
	}

	@Override
	public int get_region_height() {
		return this.heightrow;
	}

}