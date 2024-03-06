package simulator.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.json.JSONObject;

import simulator.model.SelectionStrategy;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _builders_info;

	public BuilderBasedFactory() {

		this._builders = new HashMap();
		this._builders_info = new LinkedList();

	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder<T> builder : builders) {
			this.add_builder(builder);
		}

	}

	public void add_builder(Builder<T> b) {
		// add an entry “b.getTag() |−> b” to _builders.
		// ...
		// add b.get_info() to _buildersInfo
		// ..
		this._builders.put(b.get_type_tag(), b);
		this._builders_info.add(b.get_info());

	}

	@Override
	public T create_instance(JSONObject info) throws Exception {

		if (info == null)
			throw new IllegalArgumentException("'info' cannot be null");
		String js = info.getString("type");
		if (this._builders.containsKey(js)) {
			Builder<T> builder = this._builders.get(js);
			JSONObject data = info.has("data") ? info.getJSONObject("data") : new JSONObject();
			T instance = builder.create_instance(data);
			if (instance != null) {
				return instance;
			}
		}
		throw new IllegalArgumentException("Unrecognized 'info': " + info.toString());

	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(this._builders_info);
	}

}
