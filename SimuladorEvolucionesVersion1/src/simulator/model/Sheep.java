package simulator.model;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Sheep extends Animal {
	private SelectionStrategy _danger_strategy;
	private Animal _danger_source;
	private static final double init_campoVisual = 40.0;
	private static final double init_speed = 35.0;
	

	public Sheep(SelectionStrategy mate_strategy, SelectionStrategy danger_strategy, Vector2D pos) throws Exception {
		super("Sheep", Diet.HERBIVORE, init_campoVisual, init_speed, mate_strategy, pos);
		this._danger_strategy = danger_strategy;
		this._danger_source = null;

	}

	protected Sheep(Sheep p1, Animal p2) {
		super(p1, p2);
		this._danger_strategy = p1._danger_strategy;
		this._danger_source = null;
	}

	
	@Override
	protected void normalState(double dt) {

		if (this._pos.distanceTo(this._dest) < 8.0) {
			this._dest = this.getRandomVector();
		}
		this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
		this._age += dt;
		this._energy -= 20.0 * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
		this._desire += 40 * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, 0.0, 100.0);
		if (this._danger_source == null) {
			this._danger_source = this._danger_strategy.select(this,
					this._region_mngr.get_animals_in_range(this, e -> e._diet == Diet.CARNIVORE));
		}
		if (this._danger_source != null) {
			this.setDangerState();
		} else if (this._desire > 56.0) {
			this.setMateState();
		}

	}
	@Override
	protected void mateState(double dt) {
		if (this._mate_target != null && (this._mate_target._state == State.DEAD
				|| this._pos.distanceTo(this._mate_target._pos) > this._sight_range)) {
			this._mate_target = null;
		}
		if (this._mate_target == null) {
			this._mate_target = this._mate_strategy.select(this,
					this._region_mngr.get_animals_in_range(this, e -> e._genetic_code == this._genetic_code));
			

		}
		if (this._mate_target != null) {
			this._dest = this._mate_target.get_position();
			this.move(2.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
			this._age += dt;
			this._energy -= 20.0 * 1.2 * dt;
			this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
			this._desire += 40.0 * dt;
			this._desire = Utils.constrain_value_in_range(this._desire, 0.0, 100.0);
			if (this._pos.distanceTo(this._mate_target._pos) < 8.0) {
				this._desire = 0.0;
				this._mate_target._desire = 0.0;
				if (!this.is_pregnant() && Utils._rand.nextDouble() < 0.9) {
						this._baby = new Sheep(this, this._mate_target);
				}
				this._mate_target = null;
			}
		} else {
			if(this._pos.distanceTo(this._dest) < 8.0) {
				this._dest = this.getRandomVector();
			}
			this.move(_speed * dt * Math.exp((_energy - 100.0) * 0.007));
			this._age += dt;
			this._energy -= 20.0 * dt;
			this._energy = Utils.constrain_value_in_range(_energy, 0.0, 100.0);
			this._desire += 40.0 * dt;
			this._desire = Utils.constrain_value_in_range(_desire, 0.0, 100.0);

		}
		if (this._danger_source == null) {

			this._danger_source = this._danger_strategy.select(this,
					this._region_mngr.get_animals_in_range(this, e -> e._diet == Diet.CARNIVORE));
		}
		if (this._danger_source != null) {
			// this._state = State.DANGER;
			this.setDangerState();
		} else if (this._desire < 65.0) {
			this.setNormalState();
			// this._state = State.NORMAL;
		}
	}
	@Override
	protected void dangerState(double dt) {
		if (this._danger_source != null && this._danger_source._state == State.DEAD) {
			this._danger_source = null;
		}
		if (this._danger_source == null) {
			if(_dest.distanceTo(_pos) < 8.0) {
				 this._dest = this.getRandomVector();
			}
			
			this.move(_speed * dt * Math.exp((_energy - 100.0) * 0.007));
			this._age += dt;
			
			this._energy -= 20.0 * dt;
			this._energy = Utils.constrain_value_in_range(_energy, 0.0, 100.0);
			
			this._desire += 40.0 * dt;
			this._desire = Utils.constrain_value_in_range(_desire, 0.0, 100.0);
		} else {
			this._dest = _pos.plus(_pos.minus(_danger_source.get_position()).direction());
			this.move(2.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
			this._age += dt;
			this._energy -= 20.0 * 1.2 * dt;
			this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
			this._desire += 40.0 * dt;
			this._desire = Utils.constrain_value_in_range(this._desire, 0.0, 100.0);
		}

		if (this._danger_source == null || (this._pos.distanceTo(this._danger_source._pos) > this._sight_range)) {
			
			this._danger_source = this._danger_strategy.select(this,
					this._region_mngr.get_animals_in_range(this, e -> e._diet == Diet.CARNIVORE));
			if (this._danger_source == null) {
				if (this._desire < 65.0)
					// this._state = State.NORMAL;
					this.setNormalState();
				else
					// this._state = State.MATE;
					this.setMateState();
			}
		}
	}

	@Override
	protected void setNormalState() {
		this._state = State.NORMAL;
		this._danger_source = null;
		this._mate_target = null;

	}

	private void setMateState() {
		this._state = State.MATE;
		this._danger_source = null;
	}

	private void setDangerState() {
		this._state = State.DANGER;
		this._mate_target = null;
	}
}