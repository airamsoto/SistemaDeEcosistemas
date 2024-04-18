package simulator.view;

import simulator.control.*;
import java.util.List;
import java.util.*;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;
import simulator.model.State;

public class SpeciesTableModel extends AbstractTableModel implements EcoSysObserver {

	private Controller _ctrl;
	private Map<String, Map<String, Integer>> _animals;

	SpeciesTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		this._animals = new HashMap<>();
		this._ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {

		return this._animals.size();

	}

	@Override
	public int getColumnCount() {

		return State.values().length + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String nombreEspecie = this._animals.keySet().toArray()[rowIndex].toString();
		if (columnIndex == 0)
			return nombreEspecie;
		else {
			Map<String, Integer> mapaEspecies = this._animals.get(nombreEspecie);
			int contador = mapaEspecies.getOrDefault(State.values()[columnIndex - 1].toString(), 0);
			return contador;
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return "Species";
		else
			return State.values()[column - 1].toString();
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		this._animals.clear();
		for (AnimalInfo animal : animals) {
			if (!this._animals.containsKey(animal.get_genetic_code())) {
				this._animals.put(animal.get_genetic_code(), new HashMap<>());
			}
			Map<String, Integer> estadosYContador = this._animals.get(animal.get_genetic_code());
			estadosYContador.put(animal.get_state().toString(),
					estadosYContador.getOrDefault(animal.get_state().toString(), 0) + 1);
		}
		fireTableDataChanged();

	}

//TODO REVISAR TODOS LO OVERRIDES EVITAR RECORRER TODA LA LA LISTA EN EL ONANIMALADED

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		this.onRegister(time, map, animals);
		fireTableDataChanged();

	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		/*if (!this._animals.containsKey(a.get_genetic_code())) {
			this._animals.put(a.get_genetic_code(), new HashMap<>());
		}
		Map<String, Integer> estadosYContador = this._animals.get(a.get_genetic_code());
		estadosYContador.put(a.get_state().toString(),
				estadosYContador.getOrDefault(a.get_state().toString(), 0) + 1);
		fireTableDataChanged();
		*/
		this.onRegister(time, map, animals);
	}
	

	

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		this._animals.clear();
		this.onRegister(time, map, animals);
		fireTableDataChanged();

	}

}
