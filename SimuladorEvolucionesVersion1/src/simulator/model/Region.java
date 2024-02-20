package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public abstract class Region implements Entity, FoodSupplier, RegionInfo {
	// atributo con la lista de animales que se encuentran en la region, haciendolo
	// protected
	protected List<Animal> animalList;

	public Region() {
		//inicializar la region
	}

	final void add_animal(Animal a) {
		this.animalList.add(a);
	}

	final void remove_animal(Animal a) {
		this.animalList.remove(a);
	}

	final List<Animal> getAnimals() {
		return Collections.unmodifiableList(this.animalList); // preguntar si es asi
		// devuelve una versión inmodificable de la lista de animales.
	}

	public JSONObject as_JSON() {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonList = new JSONObject();
		for (Animal animal : this.animalList) {
			jsonList.put(null, animal.as_JSON());
		}
		jsonObject.put("animal", jsonList);
		// aqui haria falta devolver lo que le corresponda a cada animal
		// no se si lo que tengo del bucle esta bien, preguntar profe

		return jsonObject;
	}

}
