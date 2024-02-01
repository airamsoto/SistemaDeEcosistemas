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
		if(a.get_diet() == CANRNIVORO) return 0.0;
		//se supone que n es el numero de animales herbivoros de la region
		return 60.0*Math.exp(-Math.max(0,n-5.0)*2.0)*dt;
	}
}
