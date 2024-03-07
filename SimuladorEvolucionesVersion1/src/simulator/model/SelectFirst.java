package simulator.model;

import java.util.List;

public class SelectFirst implements SelectionStrategy {

	@Override
	public Animal select(Animal a, List<Animal> as) {
		if (!as.isEmpty() && as.size() > 1) {
			if (!a.equals(as.get(0))) {
				return as.get(0);
			} else {
				return as.get(1);
			}

		}
		return null;
	}

}
