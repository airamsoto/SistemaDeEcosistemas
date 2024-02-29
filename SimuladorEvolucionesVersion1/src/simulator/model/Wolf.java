package simulator.model;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Wolf extends Animal {
	private Animal _hunt_target;
	private Animal _hunting_strategy;

	public Wolf(SelectionStrategy mate_strategy, SelectionStrategy hunting_strategy, Vector2D pos) throws Exception {
		super("wolf", Diet.CARNIVORE, 50.0, 60.0, mate_strategy, pos);
	}

	protected Wolf(Wolf p1, Animal p2) {
		super(p1, p2);
		this._hunt_target = null;
		this._hunting_strategy = p1._hunting_strategy;

	}

	@Override
	public void update(double dt) {
		if (this._state == State.DEAD) {

		}

		else if (this._state == State.NORMAL) {
			this.normalState(dt);

		} else if (this._state == State.HUNGER) {
			this.hungerState(dt);

		} else if (this._state == State.MATE) {
			this.mateState(dt);
		}

		// esto es la parte de encima de los estados del update
		if (this.isOut()) { // cambiar lo de null por una posicion fuera del tablero
			this._state = State.NORMAL; // arreglar esta parte
			this._pos.ajustar(this._region_mngr.get_height(), this._region_mngr.get_width());
		}
		if (this._energy == 0.0 || this._age > 14.0) {
			this._state = State.DEAD;

		}
		if (this._state != State.DEAD) {
			// comprobar si es hunt targets
			// la funcion de get food da error
			// this._energy = this._region_mngr.get_food(this, dt);
			this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
			// llama a get food y la anyade a la su energy manteniendolo entre 0 y 100
		}

	}

	private void normalState(double dt) {
		if (this._pos.distanceTo(_dest) < 0.8) {
			this._dest = this.getRandomVector();
		}
		this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
		this._age += dt;
		this._energy -= 18.0 * dt;
		this._desire += 30.0 * dt;
		if (this._energy < 50.0) {
			this._state = State.HUNGER;
		} else if (this._desire > 65.0) {
			this._state = State.MATE;
		}
	}

	private void hungerState(double dt) {
		if (this._hunt_target == null || (this._hunt_target._state == State.DEAD
				|| (this._pos.distanceTo(this._hunt_target._pos) < this._sight_range))) {
		}
		if (this._hunt_target == null) {
			this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
		} else {
			this._dest = this._hunt_target.get_position();
			this.move(3.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
			this._age += dt;
			this._energy -= 18.0 * 1.2 * dt;
			this._desire += 30.0 * dt;
			if (this._dest.distanceTo(this._hunt_target._pos) < 8.0) {
				this._hunt_target._state = State.DEAD;
				this._hunt_target = null;
				this._energy += 50.0;
				this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
			}

		}
		if (this._energy > 50.0) {
			if (this._desire < 65.0) {
				this._state = State.NORMAL;
			} else {
				this._state = State.MATE;
			}
		}
	}

	private void mateState(double dt) {
		if (this._mate_target != null && (this._mate_target._state == State.DEAD
				|| (this._pos.distanceTo(this._hunt_target._pos) < this._sight_range))) {
			this._mate_target = null;
		}
		if (this._mate_target == null) {
			// buscar un animal para emparejartse
			this._mate_target = this._mate_strategy.select(this,
					this._region_mngr.get_animals_in_range(this, e -> e._genetic_code == this._genetic_code));
			if (this._mate_target == null) {
				// si no lo encuentra se mueve asi
				this.move(3.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
			} else {
				// si mate target ya no es null
				this._dest = this._mate_target.get_position();
				this.move(3.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
				this._age += dt;
				this._energy -= 18.0 * 1.2 * dt;
				this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
				this._desire += 30.0 * dt;
				this._desire = Utils.constrain_value_in_range(this._desire, 0.0, 100.0);
				if (this._dest.distanceTo(this._mate_target._pos) < 8.0) {
					this._desire = 0.0;
					this._mate_target._desire = 0.0;
					if (this._mate_target._baby == null) {
						if (!this.is_pregnant()) {
							if (Utils._rand.nextDouble() < 0.9) {
								this._baby = new Wolf(this, this._mate_target);
							}
						}

						this.deliver_baby(); // que es eso
						this._energy -= 10.0;
						this._energy = Utils.constrain_value_in_range(this._energy, 0.0, 100.0);
						this._mate_target = null;
					}
				}
			}

		}
		if (this._energy < 50.0) {

			this._state = State.HUNGER;
		} else if (this._desire < 65.0) {

			this._state = State.NORMAL;
		}

	}

}
