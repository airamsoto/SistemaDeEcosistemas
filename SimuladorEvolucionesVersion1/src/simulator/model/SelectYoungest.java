package simulator.model;

import java.util.List;

public class SelectYoungest implements SelectionStrategy {

	@Override
	public Animal select(Animal a, List<Animal> as) {
		if(!as.isEmpty()) {
			double minAge = as.get(0)._age;
			double currentAge;
			Animal animalRet = as.get(0);
			for (Animal an : as) {
				currentAge = an._age;
				if(currentAge < minAge) {
					minAge = currentAge;
					animalRet = an;
				}
			}
			return animalRet;
		}
		return null;
	}

}
