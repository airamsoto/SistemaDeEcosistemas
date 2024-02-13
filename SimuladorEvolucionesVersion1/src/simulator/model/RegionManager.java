package simulator.model;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.*;

import org.json.JSONObject;

public class RegionManager implements AnimalMapView {
	private int _cols;
	private int _rows;
	private int _width;
	private int _height;
	private Region[][] _regions;
	private Map<Animal, Region> _animal_region;

	
	public RegionManager(int cols, int rows, int width, int height) {
		this._cols = cols;
		this._rows = rows;
		this._width = width;
		this._height = height;
		this._regions = new Region [rows][cols];
		this._animal_region = new HashMap <Animal, Region>();

		
	}
	
	void set_region(int row, int col, Region r) {
		for(Animal a:  this._regions[row][col].animalList) {
			r.add_animal(a);
			this._animal_region.put(a,r);
			
		}
		r = this._regions[row][col]; //no se si es asi o al reves
		
	}

	void register_animal(Animal a) {
		this._animal_region.put(a, _regions[this._rows][this._cols]); //falta ver si el segundo parametro es regions
		

	}

	void unregister_animal(Animal a) {
		this._animal_region.remove(a, this._regions); //igual que arriba saber si el segundo parametro es regions o no

	}

	void update_animal_region(Animal a) {
		
		/*
		 * comprobar si el animal esta en la region en la que pertence.
		 * si no lo esta -> eliminarlo de la region en la que se encuentra y añadirlo a la que pertence
		 * 
		 */
		

	}

	public double get_food(Animal a, double dt) {
		return dt;

	}

	void update_all_regions(double dt) {//doble
		for (int i = 0; i < this._rows; i++) {
			for (int j = 0; j < this._cols; j++) {
				this._regions[i][j].update(dt);
			}
		}
		
		
	}

 public List<Animal> get_animals_in_range(Animal a, Predicate<Animal> filter) {
		//hay que calcular que regiones toco  con mi campo visual a parte de la propia region dl animal
	 	//calculando los extremos de los ejes de la cirunferencia que se forma (haceindo un bucle)
	 	// cuando tengo las regiones observo las listas de animales de cada regiona porque esos
	 	// animales pueden interesar (los que esten cerca y que cumplen el filter) estan suficientemenre 
	 	//cerca si la distancia del animal a mi es menor que el radio
	 	//p.test (a`) lo que cumplan el test sera 	QUE EL CODIGO GENETICO SEA IGUAL SI ESTOY BUSCANDO EMPAREAJR,E
	 	// lo de las landa funciones se usaara cunado llamaemos a esta funcion
	 	// el animal soy yo y el predicado sera la landa funcion, va a recibir un atributo a sobre el que comprobar cosas, que sera de tipo animal
	 	// y  -> {lo de genetic code por ejemplo} a.diet == herviboro
	 	
		return null;

	}

	public JSONObject as_JSON() {
		return null;
	}

	@Override
	public int get_cols() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_rows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_region_width() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_region_height() {
		// TODO Auto-generated method stub
		return 0;
	}





}
