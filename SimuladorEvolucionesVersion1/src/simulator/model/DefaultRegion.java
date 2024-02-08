package simulator.model;

public class DefaultRegion extends Region{
	//no se si se extiende o se implementan las otras interfaces

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double get_food(Animal a, double dt) {
		//no se todavia a que se supone que se refuere dt
		if(a.get_diet() == Diet.CARNIVORE) return 0.0;
		//se supone que n es el numero de animales herbivoros de la region
		int n = 0;
		for (int i = 0; i < this.animalList.size(); i++) {
			if(this.getAnimals().get(i)._diet == Diet.HERBIVORE) n++;
			//no se si es this.getanimals o animal list
		}
		return 60.0*Math.exp(-Math.max(0,n-5.0)*2.0)*dt;
		
	}
}
