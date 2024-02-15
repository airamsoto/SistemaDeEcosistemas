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

	@Override
	public T create_instance(JSONObject info) {
		if(info == null) throw new IllegalArgumentException ("'info' cannot be null");
		this._builders.containsKey(info.getString("type"));
		
		
		///Data optional
		//info.has("data") ? info.getJSONObject("data") : new JSONObject();
		
		
		throw new IllegalArgumentException ("Unrecognized 'info': " +  info.toString());
	
	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(this._builders_info);
	}
	
	//crear e inicializar las factorias
	List<Builder<SelectionStrategy>> selection_strategy_builders = new ArrayList<>();
	selection_strategy_builders.add(new SelectFirstBuilder());
	selection_strategy_builders.add(new SelectClosestBuilder());
	Factory<SelectionStrategy> selection_strategy_factory = new BuilderBasedFactory<SelectionStrategy>(selection_strategy_builders);


}
