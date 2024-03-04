package simulator.model;

import org.json.JSONObject;

import simulator.misc.Utils;
import simulator.misc.Vector2D;
import simulator.model.*;

public abstract class Animal implements Entity, AnimalInfo {
	public static final int INITIAL_ENERGY = 100;
	public static final double INITIAL_SPEED = 0.1;
	public static final double RANDOM_POS = 60.0;
	public static final double RANDOM_SIGHT_RANGE = 0.2;
	public static final double RANDOM_SPEED = 0.2;
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
	protected SelectionStrategy _mate_strategy;

	protected Animal(String genetic_code, Diet diet, double sight_range, double init_speed,
			SelectionStrategy mate_strategy, Vector2D pos) throws Exception {
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
		this._speed = Utils.get_randomized_parameter(init_speed, INITIAL_SPEED);
		this._state = State.NORMAL;
		this._energy = INITIAL_ENERGY;
		this._desire = 0.0;
		this._dest = null;
		this._mate_target = null;
		this._baby = null;
		this._region_mngr = null;
	}

	protected Animal(Animal p1, Animal p2) {
		this._dest = null;
		this._baby = null;
		this._mate_target = null;
		this._mate_strategy = p2._mate_strategy;
		this._region_mngr = null;
		this._state = State.NORMAL;
		this._desire = 0.0;
		this._genetic_code = p1._genetic_code;
		this._diet = p1._diet;
		this._energy = (p1._energy + p2._energy) / 2;
		this._pos = p1.get_position()
				.plus(Vector2D.get_random_vector(-1, 1).scale(RANDOM_POS * (Utils._rand.nextGaussian() + 1)));
		this._sight_range = Utils.get_randomized_parameter((p1.get_sight_range() + p2.get_sight_range()) / 2,
				RANDOM_SIGHT_RANGE);
		this._speed = Utils.get_randomized_parameter((p1.get_speed() + p2.get_speed()) / 2, RANDOM_SPEED);

	}

	void init(AnimalMapView reg_mngr) {
		this._region_mngr = reg_mngr;
		if (this._pos == null) {
			double x = Utils._rand.nextDouble(800);
			double y = Utils._rand.nextDouble(600);
			Vector2D v = new Vector2D(x, y);
			this._pos = v;
		} else {
			if(this.isOut()) {
				this._pos.ajustar(reg_mngr.get_height(), reg_mngr.get_width());
			}
			
		}
		this._dest = getRandomVector();

	}

	Animal deliver_baby() {
		Animal babyToReturn = this._baby;
		this._baby = null;
		return babyToReturn;

	}

	protected void move(double speed) {
		
		this._pos = _pos.plus(_dest.minus(_pos).direction().scale(speed));
		if(this.isOut()) {
			this.setNormalState();
			this._pos.ajustar(this._region_mngr.get_height(), this._region_mngr.get_width());
		
		}
		
			
	
		
	}

	public JSONObject as_JSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pos", this._pos);
		jsonObject.put("gcode", this._genetic_code);
		jsonObject.put("diet", this._diet);
		jsonObject.put("state", this._state);
		return jsonObject;
	}

	protected Vector2D getRandomVector() {
		return new Vector2D(Utils._rand.nextDouble(_region_mngr.get_width()),
				Utils._rand.nextDouble(_region_mngr.get_height()));
	}

	@Override
	public State get_state() {
		
		return this._state;
	}

	@Override
	public Vector2D get_position() {
		
		return this._pos;
	}

	@Override
	public String get_genetic_code() {
		
		return this._genetic_code;
	}

	@Override
	public Diet get_diet() {
		
		return this._diet;
	}

	@Override
	public double get_speed() {
		
		return this._speed;
	}

	@Override
	public double get_sight_range() {
		
		return this._sight_range;
	}

	@Override
	public double get_energy() {
		
		return this._energy;
	}

	@Override
	public double get_age() {
		
		return this._age;
	}

	@Override
	public Vector2D get_destination() {
		
		return this._dest;
	}

	@Override
	public boolean is_pregnant() {
		
		return this._baby != null;
	}

	@Override
	public void update(double dt) {
		if(this._state == State.DEAD) return;
		else if(this._state == State.NORMAL) {
			this.normalState(dt);
		} else if(this._state == State.MATE) {
			this.mateState(dt);
		} else if(this._state == State.HUNGER) {
			this.hungerState(dt);
		} else if(this._state == State.DANGER) {
			this.dangerState(dt);
		}
		if (this.isOut()) {
			this.setNormalState();
			this._pos.ajustar(this._region_mngr.get_height(), this._region_mngr.get_width());
			
		}
		if (this._energy == 0.0 || this._age == 8.0)
			this._state = State.DEAD;
		if (this._state != State.DEAD) {
			this._energy += this._region_mngr.get_food(this, dt);
			this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
		}
		

	}
	// funcion is out
	protected boolean isOut() {
		return (this._pos.getX() < 0 || this._pos.getY() < 0 || this._pos.getX() >= this._region_mngr.get_width() || this._pos.getY() >= this._region_mngr.get_height());		
	}
	
	protected  void setNormalState() {
		
	}
	
	protected void normalState(double dt) {
		
	}
	protected void mateState (double dt) {
		
	}
	protected void hungerState (double dt) {
		
	}
	
	protected void dangerState (double dt) {
		
	}
	

}
