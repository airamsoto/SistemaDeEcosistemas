package simulator.model;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Sheep extends Animal {
	private static final double INIT_CAMPOVISUAL = 40.0;
	private static final double INIT_SPEED = 35.0;
	private static final double PLUS_SPEED = 2.0;
	private static final double AGE_TO_DIE = 8.0;
	private static final double ENERGY_TO_DIE = 0.0;
	private static final double REST_ENERGY = 18.0;
	private static final double PLUS_DESIRE = 30.0;
	private SelectionStrategy _danger_strategy;
	private Animal _danger_source;

	public Sheep(SelectionStrategy mate_strategy, SelectionStrategy danger_strategy, Vector2D pos) {
		super("sheep", Diet.HERBIVORE, INIT_CAMPOVISUAL, INIT_SPEED, mate_strategy, pos);
		this._danger_strategy = danger_strategy;
		this._danger_source = null;

	}

	protected Sheep(Sheep p1, Animal p2) {
		super(p1, p2);
		this._danger_strategy = p1._danger_strategy;
		this._danger_source = null;
	}

	private void normalAdvance(double dt) {
		if (this._pos.distanceTo(this._dest) < DISTANCE_TO_DEST) {
			this._dest = this.getRandomVector();
		}
		this.move(this._speed * dt * Math.exp((this._energy - MAXIMUM_DOUBLE) * MATH_DOUBLE));
		this._age += dt;
		this._energy -= REST_ENERGY * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
		this._desire += PLUS_DESIRE * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
	}

	private void rareAdvance(double dt) {
		this.move(PLUS_SPEED * _speed * dt * Math.exp((_energy - MAXIMUM_DOUBLE) * MATH_DOUBLE));
		this._age += dt;
		this._energy -= REST_ENERGY * PLUS_ENERGY * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
		this._desire += PLUS_DESIRE * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, MINIMUM_DOUBLE, MAXIMUM_DOUBLE);
	}

	@Override
	protected void normalState(double dt) {
		this.normalAdvance(dt);

		if (this._danger_source == null) {
			this.searchDanger();
		}
		if (this._danger_source != null) {
			this.setDangerState();
		} else if (this._desire > DESIRE_TO_MATE) {
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
			searchMate();

		}
		if (this._mate_target != null) {
			this._dest = this._mate_target.get_position();
			this.rareAdvance(dt);
			if (this._pos.distanceTo(this._mate_target._pos) < DISTANCE_TO_DEST) {
				this._desire = 0.0;
				this._mate_target._desire = 0.0;
				if (!this.is_pregnant() && Utils._rand.nextDouble() < BABY_PROBABILITY) {
					this._baby = new Sheep(this, this._mate_target);
				}
				this._mate_target = null;
			}
		} else {
			this.normalAdvance(dt);

		}
		if (this._danger_source == null) {

			this.searchDanger();
		}
		if (this._danger_source != null) {

			this.setDangerState();
		} else if (this._desire < DESIRE_TO_MATE) {
			this.setNormalState();

		}
	}

	@Override
	protected void dangerState(double dt) {
		if (this._danger_source != null && this._danger_source._state == State.DEAD) {
			this._danger_source = null;
		}
		if (this._danger_source == null) {
			this.normalAdvance(dt);
		} else {
			this._dest = _pos.plus(_pos.minus(_danger_source.get_position()).direction());
			this.rareAdvance(dt);
		}

		if (this._danger_source == null || (this._pos.distanceTo(this._danger_source._pos) > this._sight_range)) {

			this.searchDanger();
			if (this._danger_source == null) {
				if (this._desire < DESIRE_TO_MATE)
					this.setNormalState();
				else
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

	private void searchDanger() {
		this._danger_source = this._danger_strategy.select(this,
				this._region_mngr.get_animals_in_range(this, e -> e._diet == Diet.CARNIVORE));
	}

	@Override
	protected boolean isDead() {
		return this._energy == ENERGY_TO_DIE || this._age > AGE_TO_DIE;
	}
}