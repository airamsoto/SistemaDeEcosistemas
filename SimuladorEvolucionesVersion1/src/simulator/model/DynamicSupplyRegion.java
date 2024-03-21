package simulator.model;

import simulator.misc.Utils;

public class DynamicSupplyRegion extends Region {
	private double _food;
	private double _factor;

	public DynamicSupplyRegion(double initialFood, double growhtFactor) {
		this._food = initialFood;
		this._factor = growhtFactor;
	}

	@Override
	public double get_food(Animal a, double dt) {
		if (a._diet == Diet.CARNIVORE)
			return 0.0;
		int n = 0;
		for (Animal animal : this.animalList) {
			if (animal.get_diet() == Diet.HERBIVORE)
				n++;
		}
		double ret = Math.min(_food, 60.0 * Math.exp(-Math.max(0, n - 5.0) * 2.0) * dt);
		this._food -= ret;
		return ret;

	}

	@Override
	public void update(double dt) {

		if (Utils._rand.nextDouble() < 0.5) {
			this._food += dt * this._factor;

		}

	}

	@Override
	public String toString() {
		return "Dynamic region";
	}

}
