package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {
	public T create_instance(JSONObject info) throws Exception;

	public List<JSONObject> get_info();
}
