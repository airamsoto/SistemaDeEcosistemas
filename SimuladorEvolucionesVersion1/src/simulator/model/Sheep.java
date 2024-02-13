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

	@Override
	public void update(double dt) {
		if (this._state == State.DEAD) { // si esta muerto no hace nada
			
		} else {
			
		}
			if (this._state == State.NORMAL) {
				if (this._pos.minus(this._dest).magnitude() < 0.8) {
					
				}
				this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
				this._age += dt;
				if(this._energy - 20.0 * dt > 0)
					this._energy -= 20.0 * dt;
				else this._energy = 0.0;
				if(this._desire + 40.0 * dt < 100)
				this._desire += 40.0 * dt;
				
				if(this._danger_source == null) { 
					this._danger_source = this._region_mngr.get_animals_in_range(this, ); 
				}
				if(this._danger_source != null) this._state = State.DANGER;
				if(this._desire > 65.0 && this._danger_source == null) this._state = State.MATE;
				
			} 
			
			
			
			else if (this._state == State.DANGER) {
				if (this._danger_source != null && this._danger_source._state == State.DEAD) {
					this._danger_source = null;
				} 
				if(this._danger_source == null) {
					this.move(this._speed * dt * Math.exp((this._energy - 100.0) * 0.007));
				} 
				if (this._danger_source != null) {
					this._dest = _pos.plus(_pos.minus(_danger_source.get_position()).direction());
					this.move(2.0*_speed*dt*Math.exp((_energy-100.0)*0.007));
					this._age+=dt;
					this._energy -= 20.0*1.2*dt; //falta lo de mantenerlo y abajo igual
					this._desire += 40.0 * dt;
				}
				
		
				if (this._danger_source == null || true) { //lo de true hay que cambiarlo por damger source fuera de su campo visual
					this._danger_source = this._danger_strategy.select(_danger_source, null); // no es asi pero hay que buscarle un nuevo animal que le peuda poner el peligro
					if (this._danger_source == null) {
						if (this._desire < 65) this._state = State.NORMAL;
						else this._state = State.MATE; 
					}
				} 
			}	
			
			
			
			else if (this._state == State.MATE) {
				if(this._mate_target != null && (this._mate_target._state == State.DEAD || true)) { // el triue deberia ser está fuera del campo visual
					this._mate_target = null;
				} 
				if(this._mate_target != null) {
					
				}
			}
	}

	
	//Buscar anmial peligroso con animals_in_range y elegir uno con seleccion 
	
	//Buscar animal emparejarse pedir al gestor de regiones lista de animales con mismo codigo 
	//genetico en el campo visual con get_animals_in range y seleccionar 
	
	//cuando se actualiza estado a NORMAL _danger_source y _mate_target a null
	//estado MATE _danger_source a null
	//estado DANGER _mate_target a null
	
	
	
	
	

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