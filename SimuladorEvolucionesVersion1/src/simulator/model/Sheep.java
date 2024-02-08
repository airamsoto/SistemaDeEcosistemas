package simulator.model;

import simulator.misc.Vector2D;

public class Sheep extends Animal {

	
	private SelectionStrategy _danger_strategy;
	private Animal _danger_source;

	// 1constructora
	public Sheep(SelectionStrategy mate_strategy, SelectionStrategy danger_strategy, Vector2D pos) throws Exception {
		super("Sheep", Diet.HERBIVORE, 40.0, 35.0, mate_strategy, pos);
		this._danger_strategy = danger_strategy;
		this._danger_source = null;

	}

	// sheep born
	protected Sheep(Sheep p1, Animal p2) {
		super(p1, p2);
		this._danger_strategy = p1._danger_strategy;
		this._danger_source = null;
	}

	
	//Buscar anmial peligroso con animals_in_range y elegir uno con seleccion 
	
	//Buscar animal emparejarse pedir al gestor de regiones lista de animales con mismo codigo 
	//genetico en el campo visual con get_animals_in range y seleccionar 
	
	//cuando se actualiza estado a NORMAL _danger_source y _mate_target a null
	//estado MATE _danger_source a null
	//estado DANGER _mate_target a null
	
	
	@Override
	public void update(double dt) {
		if (this._state == State.DEAD) { // si esta muerto no hace nada
			
		} else {
			if (this._state == State.NORMAL) {
				if (this._pos.minus(this._dest).magnitude() < 0.8) {
					// elegir otro destino de manera aleatoria dentro del mapa
				}
				this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
				this._age += dt;
				if(this._energy - 20.0 * dt > 0)
					this._energy -= 20.0 * dt;
				else this._energy = 0.0;
				if(this._desire + 40.0 * dt < 100)
				this._desire += 40.0 * dt;
				
				if(this._danger_source == null) { //pedir lista de peligrosos y elegir 
					this._danger_source = this._region_mngr.get_animals_in_range(this, ); //lamda funcion con carnivoro
					//elegir de la lista con un metodo select 
				}
				if(this._danger_source != null) this._state = State.DANGER;
				if(this._desire > 65.0 && this._danger_source == null) this._state = State.MATE;
				
			} else if (this._state == State.DANGER) {
				if (this._danger_source != null && this._danger_source._state == State.DEAD) {
					this._danger_source = null;
				} else if(this._danger_source == null) {
					
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
						// falta comprobar que la energia no pase de 100
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
					// falta mantener desire entre 0 y 100
					if (this._dest.minus(this._mate_target._pos).magnitude() < 8.0) {
						this._desire = 0.0;
						this._mate_target._desire = 0.0;
						if (this._mate_target._baby == null) {
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
			
			
			if (this._pos.isOut())) { // Hacer metodo fuera ddel mapa
			//THIS.POS AJUSTARLA
			this._state = State.NORMAL;
			}
			if (this._energy == 0.0 && this._age > 8.0) { // si energy = 0.0 y age > 8.0 cambiar a dead
				this._state = State.DEAD;
			if (this._state != State.DEAD) { // si estado != dead pide comida con get_food(this, dt) y añade energy(0.0 -
				if(this._energy + this._region_mngr.get_food(this, dt) < 100.0)
					this._energy +=  this._region_mngr.get_food(this, dt);// 100.0)
				else this._energy = 100.0;
			//COMPROBAR SI PUEDE SER MENOR A 0;
			}
		}
	}	
	

	/*@Override
	public State get_state() {
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
	}*/

}