package simulator.model;

import java.util.List;

public interface SelectionStrategy {
	
	Animal select(Animal a, List<Animal> as);
	//SelectFirst devuelve el primer animal de la lista as
//	SelectClosest: devuelve el animal más cercano al animal “a” de la lista “as”.
	//SelectYoungest: devuelve el animal más joven de la lista “as”.

}
