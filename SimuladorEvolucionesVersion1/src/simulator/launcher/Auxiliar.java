package simulator.launcher;

import java.util.*;

import simulator.misc.Utils;
import simulator.model.*;
import simulator.view.SimpleObjectViewer;
import simulator.view.SimpleObjectViewer.ObjInfo;
import simulator.misc.*;

public class Auxiliar {

	private static List<ObjInfo> to_animals_info(List<? extends AnimalInfo> animals) {
		List<ObjInfo> ol = new ArrayList<>(animals.size());
		for (AnimalInfo a : animals)
			ol.add(new ObjInfo(a.get_genetic_code(), (int) a.get_position().getX(), (int) a.get_position().getY(), 8));
		return ol;
	}

	public static void main(String[] args) throws Exception {
		List<Animal> l = new LinkedList<>();
		SelectionStrategy hunt_strategy = new SelectClosest();
		SelectionStrategy mate_strategy = new SelectClosest();

		for (int i = 0; i < 3; i++) {
			double x = Utils._rand.nextDouble(800);
			double y = Utils._rand.nextDouble(600);
			Vector2D v = new Vector2D(x, y);
			l.add(new Wolf(mate_strategy, hunt_strategy, v));

		}

		for (int i = 0; i < 2; i++) {
			double x = Utils._rand.nextDouble(800);
			double y = Utils._rand.nextDouble(600);
			Vector2D v = new Vector2D(x, y);
			l.add(new Sheep(mate_strategy, hunt_strategy, v));
		}

		double dt = 0.003;
		double time = 0.0;
		SimpleObjectViewer sv = new SimpleObjectViewer("JOSE", 800, 600, 15, 20);
		while (time < 10) {
			time += dt;
			for (Animal a : l)
				a.update(dt);
			sv.update(to_animals_info(l));
		}

	}

	// 800 y 600 //MATE DANGER 15 filas 20 columnas

}
