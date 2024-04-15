package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import simulator.control.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

public class StatusBar extends JPanel implements EcoSysObserver {
	private JLabel _time;
	private JLabel _animals;
	private JLabel _dimension;

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {

		SwingUtilities.invokeLater(() -> {
			
			this._time.setText("Time: " + String.format("%.3f", time));
			this._animals.setText("Total Animals: " + animals.size());
			this._dimension.setText("Dimension: " + map.get_width() + "x" + map.get_height() + " " + map.get_cols()
					+ "x" + map.get_rows());
		});
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		this._time.setText("Time: " + String.format("%.3f", 0.0));
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		// TODO Auto-generated method stub
		this._animals.setText("Total Animals: " + animals.size());
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		this._time.setText("Time: " + String.format("%.3f", time));
		this._animals.setText("Total Animals: " + animals.size());

	}

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		// TODO poner los valores para time animal y dimensiones
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		// TODO ver pq time se queda en 0 y poner el time con mas decimales
		this._time = new JLabel("Time: ");
		
		this.add(this._time);

		JSeparator s1 = new JSeparator(JSeparator.VERTICAL);
		s1.setPreferredSize(new Dimension(10, 20));
		this.add(s1);

		this._animals = new JLabel("Total Animals: ");
		this.add(_animals);

		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		this.add(s2);

		
		this._dimension = new JLabel("Dimension: ");
		this.add(this._dimension);

	}

}
