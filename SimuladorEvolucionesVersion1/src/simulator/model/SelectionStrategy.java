package simulator.model;

import java.util.List;

public interface SelectionStrategy {
	
	Animal select(Animal a, List<Animal> as);
	
	default public Animal selectFirst (List<Animal> as) {
		if (!as.isEmpty()) return as.get(0);
		return null;
		
	}
	//el cast no se si estara bien y habria que hacerlo todo con comparatos
	default public Animal selectCloset (Animal a, List<Animal> as) {
		if (!as.isEmpty()) {
			int minDif = (int) (a._pos.minus(as.get(0)._pos)).magnitude();
			Animal ret = as.get(0);
		
			for (int i = 1; i < as.size(); i++) {
				if(minDif > (a._pos.minus(as.get(i)._pos)).magnitude()) {
					minDif = (int) a._pos.minus(as.get(i)._pos).magnitude();
					ret = as.get(i);
				}
			
			}
			return ret;
		}
		return null;
	}
	default public Animal selectYoungest (List<Animal> as) {
		if (!as.isEmpty()) return as.get(0);
		return null;
		
	}
//	SelectClosest: devuelve el animal más cercano al animal “a” de la lista “as”.
	//SelectYoungest: devuelve el animal más joven de la lista “as”.

}
