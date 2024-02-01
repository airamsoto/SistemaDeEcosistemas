package simulator.model;

import java.util.List;

public abstract class Region implements Entity, FoodSupplier, RegionInfo{
	//atributo con la lista de animales que se encuentran en la region, haciendolo protected
	protected List<Animal> animalList;
	public Region () {
		//inicializa la lista de los animales
	}
	final void add_animal(Animal a) {
		this.animalList.add(a);
		//comprobar que se incrementaba solo lo del contador
		//añade el animal a la lista de animales.
	}
	final void remove_animal(Animal a) {
		this.animalList.remove(a);
		//igaul que arriba
		//quita el animal de la lista de animales.
	}
	final List<Animal> getAnimals(){
		//devuelve una versión inmodificable de la lista de animales.
	}

}
