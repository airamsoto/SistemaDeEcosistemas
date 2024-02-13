package simulator.model;

import simulator.misc.Vector2D;

public class Wolf extends Animal {
	private Animal _hunt_target;
	private Animal _hunting_strategy;
	

	public Wolf(SelectionStrategy mate_strategy, SelectionStrategy hunting_strategy, Vector2D pos) throws Exception {
		super("wolf", Diet.CARNIVORE, 50.0, 60.0, mate_strategy, pos);
	}

	protected Wolf (Wolf p1, Animal p2) {
		super (p1, p2); 
		this._hunt_target = null;
		this._hunting_strategy = p1._hunting_strategy;
		
	}

	@Override
	public void update(double dt) {
		if (this._state == State.DEAD) {

		}

		if (this._state == State.NORMAL) {
			if (this._pos.distanceTo(_dest) < 0.8) {
				// elegir otro destino de manera aleatoria dentro del mapa
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

		} else if (this._state == State.HUNGER) {
			if (this._hunt_target == null) {
				// falta hacer || no es null pero su estado es dead
				// o esta fuera del campo visual ---> buscar otro animal para cazarlo
			}
			if (this._hunt_target == null) {
				this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
			} else {
				this._dest = this._hunt_target.get_position();
				this.move(3.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
				this._age += dt;
				this._energy -= 18.0 * 1.2 * dt;
				this._desire += 30.0 * dt;
				if (this._dest.minus(this._hunt_target._pos).magnitude() < 8.0) {
					this._hunt_target._state = State.DEAD;
					this._hunt_target = null;
					this._energy += 50.0;
					// no se si lo de que no sobrepase 100 de energia esta bien
					this._energy = Math.max(0.0, Math.min(this._energy, 100.0));
				}

			}
			if (this._energy > 50.0) {
				if (this._desire < 65.0) {
					this._state = State.NORMAL;
				} else {
					this._state = State.MATE;
				}
			}

		} else if (this._state == State.MATE) {
			if (this._mate_target != null && (this._mate_target._state == State.DEAD)) {
				// falta hacer la comprobacio de que este dfuera del campo visual
				this._mate_target = null;
			} else {
				// buscar un animal para emparejartse y si no --->
				this.move(3.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
				this._dest = this._mate_target.get_position();
				this.move(3.0 * _speed * dt * Math.exp((_energy - 100.0) * 0.007));
				this._age += dt;
				this._energy -= 18.0 * 1.2 * dt;
				this._energy = Math.max(0.0, Math.min(this._energy, 100.0)); // no se si es asi
				this._desire += 30.0 * dt;
				this._desire = Math.max(0.0, Math.min(this._desire, 100.0));
				// falta mantener desire entre 0 y 100
				if (this._dest.minus(this._mate_target._pos).magnitude() < 8.0) {
					this._desire = 0.0;
					this._mate_target._desire = 0.0;
					if (this._mate_target._baby == null) {
						if(!this.is_pregnant()) {
							
						}
						/*
						 * Si el animal no lleva un bebe ya, con probabilidad de 0.9 va a llevar a un
						 * nuevo bebe usando new Wolf(this, _mate_target).
						 */
						this.deliver_baby();
						this._energy -= 10.0;
						this._energy = Math.max(0.0, Math.min(this._energy, 100.0));
						this._mate_target = null;
					}
				}
			}
			if (this._energy < 50.0) {
				this._state = State.HUNGER;
			} else if (this._desire < 65.0) {
				this._state = State.NORMAL;
			}

		}

		if (this._pos == null) { // cambiar lo de null por una posicion fuera del tablero
			this._state = State.NORMAL;
			// falta ajustar la posicion
		}
		if (this._energy == 0.0 || this._age > 14.0) {
			this._state = State.DEAD;

		}
		if (this._state != State.DEAD) {
			//comprobar si es hunt targets
			this._region_mngr.get_food(_hunt_target, dt);
			// llama a get food y la anyade a la su energy manteniendolo entre 0 y 100
		}

	}
}
