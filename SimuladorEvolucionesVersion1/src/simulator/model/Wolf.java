package simulator.model;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Wolf extends Animal {
	private static final double INIT_CAMPOVISUAL = 50.0;
	private static final double INIT_SPEED = 60.0;
	private static final double ENERGY_TO_HUNGER = 50.0;
	private static final double AGE_TO_DIE = 14.0;
	private static final double PLUS_SPEED = 3.0;
	private static final double REST_ENERGY = 18.0;
	private static final double PLUS_DESIRE = 30.0;
	private Animal _hunt_target;
	private SelectionStrategy _hunting_strategy;

	public Wolf(SelectionStrategy mate_strategy, SelectionStrategy hunting_strategy, Vector2D pos)
			throws IllegalArgumentException {
		super("wolf", Diet.CARNIVORE, INIT_CAMPOVISUAL, INIT_SPEED, mate_strategy, pos);
		this._hunt_target = null;
		this._hunting_strategy = hunting_strategy;
	}

	protected Wolf(Wolf p1, Animal p2) {
		super(p1, p2);
		this._hunting_strategy = p1._hunting_strategy;
		this._hunt_target = null;

	}

	private void normalAdvance(double dt) {
		if (this._pos.distanceTo(_dest) < 8.0) {
			this._dest = this.getRandomVector();
		}
		this.move(this._speed * dt * Math.exp((this._energy - MAXIMUM_DOUBLE) * MATH_DOUBLE));
		this._age += dt;
		this._energy -= REST_ENERGY * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
		this._desire += PLUS_DESIRE * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
	}

	private void plusAdvance(double dt) {
		this.move(PLUS_SPEED * _speed * dt * Math.exp((_energy - 100.0) * MATH_DOUBLE));
		this._age += dt;
		this._energy -= REST_ENERGY * 1.2 * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
		this._desire += PLUS_DESIRE * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);

	}

	protected void normalState(double dt) {
		this.normalAdvance(dt);
		if (this._energy < ENERGY_TO_HUNGER) {
			this.setHungerState();
		} else if (this._desire > DESIRE_TO_MATE) {
			this.setMateState();
		}
	}

	protected void hungerState(double dt) {
		if (this._hunt_target == null || (this._hunt_target.get_state() == State.DEAD
				|| (this._pos.distanceTo(this._hunt_target.get_position()) > this._sight_range))) {
			this.searchHunger();
		}
		if (this._hunt_target == null) {
			this.normalAdvance(dt);
		} else {
			this._dest = this._hunt_target.get_position();
			this.plusAdvance(dt);
			if (this._pos.distanceTo(this._hunt_target.get_position()) < DISTANCE_TO_DEST) {
				this._hunt_target._state = State.DEAD; 
				this._hunt_target = null;
				this._energy += 50.0;
				this._energy = Utils.constrain_value_in_range(this._energy, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
			}

		}
		if (this._energy > ENERGY_TO_HUNGER) {
			if (this._desire < DESIRE_TO_MATE) {
				this.setNormalState();
			} else {
				this.setMateState();
			}
		}
	}

	protected void mateState(double dt) {
		if (this._mate_target != null && (this._mate_target.get_state() == State.DEAD
				|| (this._pos.distanceTo(this._mate_target.get_position()) > this._sight_range))) {
			this._mate_target = null;
		}
		if (this._mate_target == null) {
			this.searchMate();
		}
		if (this._mate_target == null) {
			this.normalAdvance(dt);
		} else {
			this._dest = this._mate_target.get_position();
			this.plusAdvance(dt);
			if (this._pos.distanceTo(this._mate_target.get_position()t) < DISTANCE_TO_DEST) {
				this._desire = 0.0;
				this._mate_target._desire = 0.0;

				if (!this.is_pregnant() && Utils._rand.nextDouble() < BABY_PROBABILITY) {
					this._baby = new Wolf(this, this._mate_target);
				}

				this._energy -= 10.0;
				this._energy = Utils.constrain_value_in_range(this._energy, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
				this._mate_target = null;

			}
		}
		if (this._energy < ENERGY_TO_HUNGER) {
			this.setHungerState();
		} else if (this._desire < DESIRE_TO_MATE) {
			this.setNormalState();
		}

	}

	@Override
	protected void setNormalState() {
		this._state = State.NORMAL;
		this._hunt_target = null;
		this._mate_target = null;
	}

	private void setMateState() {
		this._state = State.MATE;
		this._hunt_target = null;

	}

	private void setHungerState() {
		this._state = State.HUNGER;
		this._mate_target = null;
	}

	private void searchHunger() {
		this._hunt_target = this._hunting_strategy.select(this,
				this._region_mngr.get_animals_in_range(this, e -> e.get_diet() == Diet.HERBIVORE));
	}

	@Override
	protected boolean isDead() {
		return this._energy == ENERGY_TO_DIE || this._age > AGE_TO_DIE;

	}

}
