package simulator.view;

import simulator.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import simulator.model.*;
import simulator.model.MapInfo.RegionData;

import javax.swing.table.AbstractTableModel;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {

	private Controller _ctrl;
	private Map<String,  Integer> _regions;
	private MapInfo mapa;

	

	RegionsTableModel(Controller ctrl) {
		this._regions = new HashMap<>();
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.mapa.get_cols()*this.mapa.get_rows();		
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
	    if (mapa != null) {
	        Iterator<MapInfo.RegionData> it = mapa.iterator();

	        for (int i = 0; i < rowIndex && it.hasNext(); i++) {
	            it.next();
	        }
	        //guardarlo en una lista.
	        //no buckes , sabiendo el numero de cada cosa columnas y numero de elemento stotales puedo calcular directamemte donde esta esa region

	        if (it.hasNext()) {
	            MapInfo.RegionData regionData = it.next();
	            RegionInfo region = regionData.get_r();

	            if (region != null) {
	                switch (columnIndex) {
	                    case 0:
	                        return regionData.get_row(); 
	                    case 1:
	                        return regionData.get_col(); 
	                    case 2:
	                        return region.toString(); 
	                    case 3:
	                        return getHerbivoreCount(region);
	                    case 4:
	                        return getCarnivoreCount(region);
	                    default:
	                        return null;
	                }
	            } 
	            
	        }
	    }
	    return null;
	}


	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		this.mapa = map;
		fireTableDataChanged();

	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		
		this.mapa = map;
		fireTableDataChanged();
		
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		this.mapa = map;
		fireTableDataChanged();
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		//con esta info si tenemos las regiones en una lista sabemos done esta esa region a paritr de la row col
		//division y modulo
		//no hace falta recorrer todo se que solo ha cambiado una
		this.mapa = map;
		fireTableDataChanged();
		

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Autxo-generated method stub
		this.mapa = map;
		this.fireTableDataChanged();

	}
	
	// Método para calcular el número de herbívoros en la región
	private int getHerbivoreCount(RegionInfo region) {
	    int count = 0;
	    for (AnimalInfo animal : region.getAnimalsInfo()) {
	        if (animal.get_diet() == Diet.HERBIVORE) {
	            count++;
	        }
	    }
	    return count;
	}

	// Método para calcular el número de carnívoros en la región
	private int getCarnivoreCount(RegionInfo region) {
	    int count = 0;
	    for (AnimalInfo animal : region.getAnimalsInfo()) {
	        if (animal.get_diet() == Diet.CARNIVORE) {
	            count++;
	        }
	    }
	    return count;
	}
}
