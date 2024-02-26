package simulator.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.json.JSONObject;

import simulator.model.SelectionStrategy;

public class BuilderBasedFactory<T> implements Factory <T>{
	private Map<String, Builder <T>> _builders;
	private List<JSONObject> _builders_info;
	
	public BuilderBasedFactory() {
		// Create a HashMap for _builders, and a LinkedList _builders_info
		this._builders = new HashMap();
		this._builders_info = new LinkedList();
		// …
		}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder <T> builder : builders) {
			this.add_builder(builder);
		}
		// call add_builder(b) for each builder b in builder
		// …
		}
		public void add_builder(Builder<T> b) {
		// add an entry “b.getTag() |−> b” to _builders.
		// ...
		// add b.get_info() to _buildersInfo
		// ..
		this._builders_info.add(b.get_info());		}
	

	@Override
	public T create_instance(JSONObject info) {
		if(info == null) throw new IllegalArgumentException ("'info' cannot be null");
		
		if(this._builders.containsKey(info.getString("type"))) {
			if(this.create_instance(info.has("data") ? info.getJSONObject("data") : new JSONObject()) != null) {
				return null; //habria que retornar la instancia creada
			} else {
				//creo que aqui habria que lanzar la exception tb
			}
			
		}
		
		
		

		throw new IllegalArgumentException ("Unrecognized 'info': " +  info.toString());
	
	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(this._builders_info);
	}
	

}
