package simulator.model;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Wolf extends Animal {
	private Animal _hunt_target;
	private SelectionStrategy _hunting_strategy;
	private static final double init_campoVisual = 50.0;
	private static final double init_speed = 60.0;
	private static final double energyToHunger = 50.0;
	private static final double ageToDie = 14.0;
	private static final double plusSpeed = 3.0;
	private static final double mathDouble = 0.007;
	private static final double restEnergy = 18.0;
	private static final double plusDesire = 30.0;

	public Wolf(SelectionStrategy mate_strategy, SelectionStrategy hunting_strategy, Vector2D pos) throws Exception {
		super("wolf", Diet.CARNIVORE, init_campoVisual, init_speed, mate_strategy, pos);
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
		this.move(this._speed * dt * Math.exp((this._energy - maximumDouble) * mathDouble));
		this._age += dt;
		this._energy -= restEnergy * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, minimumDouble, maximumDouble);
		this._desire += plusDesire * dt;
		this._desire = Utils.constrain_value_in_range(this._desire, minimumDouble, maximumDouble);
	}
	
	private void plusAdvance (double dt) {
		this.move(plusSpeed * _speed * dt * Math.exp((_energy - 100.0) * mathDouble));
		this._age += dt;
		this._energy -= restEnergy * 1.2 * dt;
		this._energy = Utils.constrain_value_in_range(this._energy, minimumDouble, maximumDouble);
		this._desire += plusDesire * dt;
		this._desire = Utils.constrain_value_in_range(this._desire,minimumDouble, maximumDouble);
		
	}

	
	

	protected void normalState(double dt) {
		this.normalAdvance(dt);
		if (this._energy < energyToHunger) {
			this.setHungerState();
		} else if (this._desire > desireToMate) {
			this.setMateState();
		}
	}

	protected void hungerState(double dt) {
		if (this._hunt_target == null || (this._hunt_target._state == State.DEAD
				|| (this._pos.distanceTo(this._hunt_target._pos) > this._sight_range))) {
			this.searchHunger();
		}
		if (this._hunt_target == null) {
			this.normalAdvance(dt);
		} else {
			this._dest = this._hunt_target.get_position();
			this.plusAdvance(dt);
			if (this._pos.distanceTo(this._hunt_target._pos) < distanceToDest) {
				this._hunt_target._state = State.DEAD;
				this._hunt_target = null;
				this._energy += 50.0;
				this._energy = Utils.constrain_value_in_range(this._energy, minimumDouble, maximumDouble);
			}

		}
		if (this._energy > energyToHunger) {
			if (this._desire < desireToMate) {
				this.setNormalState();
			} else {
				this.setMateState();
			}
		}
	}

	protected void mateState(double dt) {
		if (this._mate_target != null && (this._mate_target._state == State.DEAD
				|| (this._pos.distanceTo(this._mate_target._pos) > this._sight_range))) {
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
			if (this._pos.distanceTo(this._mate_target._pos) < distanceToDest) {
				this._desire = 0.0;
				this._mate_target._desire = 0.0;

				if (!this.is_pregnant() && Utils._rand.nextDouble() < babyProbability) {
					this._baby = new Wolf(this, this._mate_target);
				}

				this._energy -= 10.0;
				this._energy = Utils.constrain_value_in_range(this._energy, minimumDouble, maximumDouble);
				this._mate_target = null;

			}
		}
		if (this._energy < energyToHunger) {
			this.setHungerState();
		} else if (this._desire < desireToMate) {
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
		return this._energy == energyToDie || this._age > ageToDie;

	}

}
