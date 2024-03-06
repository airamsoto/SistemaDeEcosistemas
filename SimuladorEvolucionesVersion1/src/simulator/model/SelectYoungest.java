package simulator.model;

import java.util.List;

public class SelectYoungest implements SelectionStrategy {

	@Override
	public Animal select(Animal a, List<Animal> as) {
		if (!as.isEmpty()) {

			double currentAge;
			Animal animalRet = null;
			if (a != as.get(0))
				animalRet = as.get(0);
			else
				animalRet = as.get(1);
			double minAge = animalRet.get_age();
			for (Animal an : as) {
				currentAge = an.get_age();
				if (currentAge < minAge) {
					minAge = currentAge;
					animalRet = an;
				}
			}

			return animalRet;
		}
		return null;
	}

}
