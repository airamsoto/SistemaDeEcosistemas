package simulator.view;

import simulator.control.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import simulator.model.*;
import simulator.model.MapInfo.RegionData;

import javax.swing.table.AbstractTableModel;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {

	private Controller _ctrl;
	private Map<RegionData, Map<String, Integer>> mapa_regiones;

	RegionsTableModel(Controller ctrl) {
		this.mapa_regiones = new TreeMap<>(new Comparator <>() {

			@Override
			public int compare(RegionData o1, RegionData o2) {
				if(o1.get_col() == o2.get_col() && o1.get_row() == o2.get_row() ) return 0;
				else if (o1.get_col()< o2.get_col() && o1.get_row()< o2.get_row()) return -1; 
				else return 1;
			}
			
		});
		
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.mapa_regiones.size();
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
		List<RegionData> it = new ArrayList<>(this.mapa_regiones.keySet());
		MapInfo.RegionData rd = it.get(rowIndex);
		RegionInfo r = rd.get_r();

		if (r != null) {
			switch (columnIndex) {
			case 0:
				return rd.get_row();
			case 1:
				return rd.get_col();
			case 2:
				return rd.r().toString();
			default:
				return this.getAnimalDietCount(rd.get_r(), Diet.values()[columnIndex - 3]);
				// TODO intentar sacarlo desde el mapa
			}
		}

		return null;
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		Iterator<RegionData> it = map.iterator();
		while (it.hasNext()) {
			RegionData r = it.next();
			this.change(r);
		}
		this.fireTableDataChanged();

	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		this.mapa_regiones.clear();
		Iterator<RegionData> it = map.iterator();
		while (it.hasNext()) {
			RegionData r = it.next();
			this.change(r);
		}
		
		this.fireTableDataChanged();
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {

		int i = (int) (a.get_position().getX() / map.get_region_width());
		int j = (int) (a.get_position().getY() / map.get_region_height());
		ArrayList <RegionData> arrayRD = new ArrayList<>(mapa_regiones.keySet());
		int pos =  i+ map.get_cols() * j;
		this.change(arrayRD.get(pos));
		this.fireTableDataChanged();
		
	
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		this.mapa_regiones.clear();
		Iterator<RegionData> it = map.iterator();
		while (it.hasNext()) {
			RegionData ri = it.next();
			this.change(ri);
		}
		int pos = col + map.get_cols() * row;
		if( pos < this.mapa_regiones.size() &&r.equals(this.mapa_regiones.get(pos))) {
			this.mapa_regiones.remove(this.mapa_regiones.get(pos));

		}
		RegionData rt = new RegionData(row, col, r);
		this.change(rt);
		this.fireTableDataChanged();

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		this.mapa_regiones.clear();
		Iterator<RegionData> it = map.iterator();
		while (it.hasNext()) {
			RegionData r = it.next();
			this.change(r);
		}
		
		this.fireTableDataChanged();


	}

	private int getAnimalDietCount(RegionInfo r, Diet d) {
		int count = 0;
		for (AnimalInfo animal : r.getAnimalsInfo()) {
			if (animal.get_diet() == d) {
				count++;
			}
		}
		
		return count;
	}

	private void change(RegionData r) {
		if(r.r().toString()== "Dynamic region") System.out.print("HOLA");
		Map<String, Integer> map = new HashMap<>();
		if (this.mapa_regiones.containsKey(r)) {
			map = mapa_regiones.get(r);
		}
		for (Diet dieta : Diet.values()) {
			map.put(dieta.toString(), getAnimalDietCount(r.get_r(), dieta));
		}
				
		
		
		this.mapa_regiones.put(r, map);
	}

}
