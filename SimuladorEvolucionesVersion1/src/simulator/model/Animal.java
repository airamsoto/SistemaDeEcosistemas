package simulator.model;

import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.*;

public abstract class Animal implements Entity, AnimalInfo {
	protected String _genetic_code;
	protected Diet _diet;
	protected State _state;
	protected Vector2D _pos;
	protected Vector2D _dest;
	protected double _energy;
	protected double _speed;
	protected double _age;
	protected double _desire;
	protected double _sight_range;
	protected Animal _mate_target;
	protected Animal _baby;
	protected AnimalMapView _region_mngr;
	protected _region_mngrSelectionStrategy _mate_strategy;

	protected Animal(String genetic_code, Diet diet, double sight_range, double init_speed,
			SelectionStrategy mate_strategy, Vector2D pos) {
		if (sight_range <= 0 || init_speed <= 0)
			throw new Exception("no puede ser negativo");
		else if (mate_strategy == null)
			throw new Exception("no puede ser null");
		else if (genetic_code.length() == 0)
			throw new Exception("no puede ser una cadena vacia");
		this._genetic_code = genetic_code;
		this._diet = diet;
		this._sight_range = sight_range;
		this._pos = pos;
		this._mate_strategy = mate_strategy;
		this._speed = Utils.get_randomized_parameter(init_speed, 0.1);
		this._state = NORMAL;
		this._energy = 100.0;
		this._desire = 0.0;
		this._dest = null;
		this._mate_target = null;
		this._baby = null;
		this._region_mngr = null;

	}

	protected Animal(Animal p1, Animal p2) {
		this._dest = null;
		this._baby = null;
		this._mate_strategy = null;
		this._region_mngr = null;
		this._state = NORMAL;
		this._desire = 0.0;
		this._genetic_code = p1._genetic_code;
		this._diet = p1._diet;
		this._energy = (p1._energy + p2._energy) / 2;
		this._pos = p1.get_position()
				.plus(Vector2D.get_random_vector(-1, 1).scale(60.0 * (Utils._rand.nextGaussian() + 1)));
		this._sight_range = Utils.get_randomized_parameter((p1.get_sight_range() + p2.get_sight_range()) / 2, 0.2);
		this._speed = Utils.get_randomized_parameter((p1.get_speed() + p2.get_speed()) / 2, 0.2);

	}

	void init(AnimalMapView reg_mngr) {
		this._region_mngr = reg_mngr;
		if (this._pos == null) {
			// this._pos = (1, 1);
			/*
			 * hay que elegir una posición aleatoria dentro del rango del mapa (X entre 0 y
			 * _region_mngr.get_width()-1 y Y entre 0 y _region_mngr.get_height()-1).
			 */
		} else {
			while (x >= width)
				x = (x - width);
			while (x < 0)
				x = (x + width);
			while (y >= height)
				y = (y - height);
			while (y < 0)
				y = (y + height);
		}

	}

	Animal deliver_baby() {
		return this._baby;
		this._baby = null;
	}

	protected void move(double speed) {
		/*
		 * las subclases usan este método para actualizar la posición del animal (para
		 * que se mueva hacia _dest con velocidad speed). Esto se puede hace usando _pos
		 * = _pos.plus(_dest.minus(_pos).direction().scale(speed))
		 */
		this._pos = _pos.plus(_dest.minus(_pos).direction().scale(speed));
	}

	public JSONObject as_JSON() {
		"pos": [28.90696391797469,22.009772194487613],
		"gcode": "Sheep",
		"diet": "HERBIVORE",
		"state": "NORMAL"
	 }

	@Override
	public State get_state() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2D get_position() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_genetic_code() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Diet get_diet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double get_speed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double get_sight_range() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double get_energy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double get_age() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2D get_destination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean is_pregnant() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub

	}

}
