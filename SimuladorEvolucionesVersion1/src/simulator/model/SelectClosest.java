package simulator.model;

import java.util.List;

public class SelectClosest implements SelectionStrategy {
	
	@Override
	public Animal select(Animal a, List<Animal> as) {
		if (!as.isEmpty()) {
			double minDis = a._pos.distanceTo(as.get(0)._pos);
			Animal animalRet = as.get(0);
			double currentDistance;
			for(Animal an : as) {
				currentDistance = an._pos.distanceTo(a._pos);
				if(currentDistance < minDis) {
					minDis = currentDistance;
					animalRet = an;
				}
			}
			return animalRet;
		}	
		return null;
	}
	

}
