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
	private Map<String, Map <String, Integer>> _animals;
	
	SpeciesTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		this._animals = new HashMap<>();
		
		for (State state : State.values()) {
			this._animals.put(state.toString(), new HashMap<>());
		}
		this._ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		
		return this._animals.size();
	}

	@Override
	public int getColumnCount() {

		return State.values().length +1;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
 //COMPLETAR
		return "";
	}
	@Override
	public String getColumnName(int column) {
		if(column == 0) return "Species";
		else return State.values()[column -1].toString();	
	}
	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {

		
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {

		
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {

		
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {

		
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {

		
	}
	
}
