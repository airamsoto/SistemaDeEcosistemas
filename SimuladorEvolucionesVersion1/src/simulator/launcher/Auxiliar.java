package simulator.launcher;
import java.util.*;
import simulator.model.*;
import simulator.view.SimpleObjectViewer.ObjInfo;

public class Auxiliar {
	private List<ObjInfo> to_animals_info(List<? extends AnimalInfo> animals) {
		List<ObjInfo> ol = new ArrayList<>(animals.size());
		for (AnimalInfo a : animals)
		ol.add(new ObjInfo(a.get_genetic_code(),
		(int) a.get_position().getX(),
		(int) a.get_position().getY(),8));
		return ol;
		}
//main
	List<Animal> l = new LinkedList<>();
	SelectionStrategy jose = new Selectionx 800 y 600 //MATE DANGER 15 filas 20 columnas
			//

}
