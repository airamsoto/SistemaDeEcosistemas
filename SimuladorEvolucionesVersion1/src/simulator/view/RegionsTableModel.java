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
	// private List<MapInfo.RegionData> _region;
	private Map<MapInfo.RegionData, Map<Integer, Integer>> _regionsMap;

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
	    MapInfo.RegionData regionData = (MapInfo.RegionData) _regionsMap.keySet().toArray()[rowIndex];
	    System.out.print(_regionsMap.keySet().toArray()[rowIndex].toString());
	    
	    switch (columnIndex) {
	        case 0:
	            return regionData.row();
	        case 1:
	            return regionData.col();
	        case 2:
	             return ""; //tenenmos que buscar la manera de que imprima el tipo de region
	        default:
	          
	            int dietIndex = columnIndex - 3;
	            Diet diet = Diet.values()[dietIndex];
	            Map<Integer, Integer> dietCounts = _regionsMap.get(regionData);
	            int animalCount = dietCounts.getOrDefault(diet.ordinal(), 0);
	            return animalCount;
	    }
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
	   
	}



	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		// TODO Auto-generated method stub

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
