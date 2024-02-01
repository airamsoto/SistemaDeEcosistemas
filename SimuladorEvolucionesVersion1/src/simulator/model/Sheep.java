package simulator.model;

import simulator.misc.Vector2D;

public class Sheep extends Animal {

	private Amimal _danger_source;
	private SelectionStrategy _danger_strategy;

	// 1constructora
	public Sheep(SelectionStrategy mate_strategy, SelectionStrategy danger_strategy, Vector2D pos) {
		this._danger_strategy = danger_strategy;

	}

	// sheep born
	protected Sheep(Sheep p1, Animal p2) {
		this._danger_strategy = p1._danger_strategy;
		this._danger_source = null;
	}

	void update() {
		if (true) { // si esta muerto no hace nada

		} else if (false) { // actualizar segun estado

		} else if (false) { // si esta fuera del mapa ajustar y estado a normal

		} else if (false) { // si energy = 0.0 y age > 8.0 cambiar a dead

		} else if (false) { // si estado != dead pide comida con get_food(this, dt) y añade energy(0.0 -
							// 100.0)

		}

	}
	
	//Buscar anmial peligroso con animals_in_range y elegir uno con seleccion 
	
	//Buscar animal emparejarse pedir al gestor de regiones lista de animales con mismo codigo 
	//genetico en el campo visual con get_animals_in range y seleccionar 
	
	//cuando se actualiza estado a NORMAL _danger_source y _mate_target a null
	//estado MATE _danger_source a null
	//estado DANGER _mate_target a null

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub

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
}