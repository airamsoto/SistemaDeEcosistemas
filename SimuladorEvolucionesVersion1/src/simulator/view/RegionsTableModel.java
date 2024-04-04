package simulator.view;

import simulator.control.*;

import java.util.ArrayList;
import java.util.List;

import simulator.model.*;
import javax.swing.table.AbstractTableModel;

import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {
	// TODO definir atributos necesarios
	private Controller _ctrl;
	private List<MapInfo.RegionData> _region;
//	private  {"rpñ"};
	
	RegionsTableModel(Controller ctrl) {
		// TODO inicializar estructuras de datos correspondientes
		// TODO registrar this como 
		Diet.values();
		this._region = new ArrayList<>();
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
	}
	// TODO el resto de métodos van aquí…

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
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
