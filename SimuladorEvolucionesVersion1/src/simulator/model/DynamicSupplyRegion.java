package simulator.model;

public class DynamicSupplyRegion extends Region {
	public DynamicSupplyRegion(double initialFood, double growhtFactor) {

	}

	@Override
	public double get_food(Animal a, double dt) {
		if(a._diet == Diet.CARNIVORE) return 0.0;
		/*
		 * hace falta comprobar que se herviboro?
		n es el numero de animales herviboros y food la cantidad de comida actual
		 * return Math.min(_food(a, dt),60.0*Math.exp(-Math.max(0,n-5.0)*2.0)*dt);

		 */
		int _food;
		int n = 0;
		for (int i = 0; i < this.animalList.size(); i++) {
			if(this.getAnimals().get(i)._diet == Diet.HERBIVORE) {
				
				n++;
			}
			//no se si es this.getanimals o animal list
		}
		//return Math.min(this.d(a, dt),60.0*Math.exp(-Math.max(0,n-5.0)*2.0)*dt);
		return 0.0;
		
	}

	@Override
	public void update(double dt) {
		/*
		 * Además quita el valor devuelto a la cantidad de comida _food que tiene la
		 * región actualmente. Su método update incrementa, con probabilidad 0.5, la
		 * cantidad de comida por dt*_factor donde _factor es el factor de crecimiento.
		 * 
		 * 
		 */
		

	}

}
