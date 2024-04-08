package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import simulator.control.Controller;
import javax.swing.JTable;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		
		super("[ECOSYSTEM SIMULATOR]");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
	JPanel mainPanel = new JPanel(new BorderLayout());
	setContentPane(mainPanel);
	
	// TODO crear ControlPanel y añadirlo en PAGE_START de mainPanel
	ControlPanel controlPanel = new ControlPanel(_ctrl);
	mainPanel.add(controlPanel, BorderLayout.PAGE_START);

	
	// TODO crear StatusBar y añadirlo en PAGE_END de mainPanel
	
	StatusBar statusBar = new StatusBar (this._ctrl);
	mainPanel.add(statusBar, BorderLayout.PAGE_END);
	
	// Definición del panel de tablas (usa un BoxLayout vertical)
	JPanel contentPanel = new JPanel();
	contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	mainPanel.add(contentPanel, BorderLayout.CENTER);
	
	// TODO crear la tabla de especies y añadirla a contentPanel.
	

	contentPanel.add(new InfoTable("Species", new SpeciesTableModel(_ctrl))).setPreferredSize(new Dimension(500, 250));
	
	
	// crear la tabla de regiones.

	contentPanel.add(new InfoTable("Regions", new RegionsTableModel(_ctrl))).setPreferredSize(new Dimension(500, 250));
	
	// TODO llama a ViewUtils.quit(MainWindow.this) en el método windowClosing
	//addWindowListener();ç
	


	
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	pack();
	setVisible(true);
	}
}
