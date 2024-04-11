package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import simulator.control.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

public class StatusBar extends JPanel implements EcoSysObserver {
	private double time;
	private int animals;
	private int width;
	private int height;
	private int row;
	private int col;

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		this.time = time;
		//this.dimension = new Dimension( map.get_region_height(), map.get_region_width());
		this.animals = animals.size();
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
	
	
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Auto-generated method stub

	}

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	

	private void initGUI() {
		//TODO poner los valores para time animal y dimensiones
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		//TODO ver pq se quedan en 0 y poner el time con mas decimales
		JLabel time = new JLabel ("Time: " + this.time);
		this.add(time);
			
		JSeparator s1 = new JSeparator(JSeparator.VERTICAL);
		s1.setPreferredSize(new Dimension(10, 20));
		this.add(s1);
		
		JLabel animals = new JLabel ("Total Animals: " + this.animals);
		this.add(animals);
		
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		this.add(s2);
		
		//TODO la dimension es nula
		
		JLabel dimension = new JLabel ("Dimension: " + this.row + "x" + this.col);
		this.add(dimension);
		
	}
	

}
