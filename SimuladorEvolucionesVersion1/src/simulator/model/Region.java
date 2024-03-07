package simulator.model;

import java.util.Collections;
import java.util.*;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

public abstract class Region implements Entity, FoodSupplier, RegionInfo {

	protected List<Animal> animalList;

	public Region() {
		this.animalList = new ArrayList<Animal>();
	}

	final void add_animal(Animal a) {
		this.animalList.add(a);
	}

	final void remove_animal(Animal a) {
		this.animalList.remove(a);
	}

	final List<Animal> getAnimals() {
		return Collections.unmodifiableList(this.animalList);
	}

	public JSONObject as_JSON() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonList = new JSONArray();
		for (Animal animal : this.animalList) {
			jsonList.put(animal.as_JSON());
		}
		jsonObject.put("animal", jsonList);
		return jsonObject;
	}

}
