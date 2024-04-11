package simulator.view;

import simulator.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.model.*;
import javax.swing.table.AbstractTableModel;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {

	private Controller _ctrl;
	private Map<String,  Integer> _regions;

	RegionsTableModel(Controller ctrl) {
		this._regions = new HashMap<>();
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.mapaa.size() +2;
	}

	@Override
	public int getColumnCount() {
		return Diet.values().length + 3;
	}

	@Override
	public String getColumnName(int column) {

		switch (column) {
		case 0:
			return "Row";
		case 1:
			return "Col";
		case 2:
			return "Desc.";
		default:
			return Diet.values()[column - 3].toString();
		}

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch (columnIndex) {
		case 0:
		return 
			
		case 1:
			return 0;
			
		case 2:
		
		default:

			return 0;
		}
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		this.mapaa = map;
		this.mapaa.
		
		

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
		// TODO Auto-generated method stub

	}
}
