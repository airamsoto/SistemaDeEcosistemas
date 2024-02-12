package simulator.model;

import java.util.List;

public class SelectClosest implements SelectionStrategy {
	//el cast no se si estara bien y habria que hacerlo todo con comparatos
		 
	@Override
	public Animal select(Animal a, List<Animal> as) {
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
	

}
