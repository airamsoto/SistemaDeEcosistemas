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
	private Map<String, Integer> _regionsMap;

	RegionsTableModel(Controller ctrl) {
		this._regionsMap = new HashMap<>();
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {

		return this._regionsMap.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
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
	           // return regionData.row();
	        case 1:
	           // return regionData.col();
	        case 2:
	             return ""; //TODO tenenmos que buscar la manera de que imprima el tipo de region
	        default:
	          
	          return 0;
	    }
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Auto-generated method stub

	}
}
