package simulator.view;

import simulator.control.*;

import java.util.ArrayList;
import java.util.List;

import simulator.model.*;
import javax.swing.table.AbstractTableModel;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {
	// TODO definir atributos necesarios
	private Controller _ctrl;
	private List<MapInfo.RegionData> _region;
	
	RegionsTableModel(Controller ctrl) {

		this._region = new ArrayList<>();
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
	}
	// TODO el resto de métodos van aquí…

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this._region.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Diet.values().length + 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(rowIndex == 0) {
		
			switch (columnIndex) {
			case 0:
				return "Row";
			case 1:
				return "Col";
			case 2:
				return "Desc.";
			default:
				return Diet.values()[columnIndex -3].toString();
				
			}
		} else {
			
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub

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
