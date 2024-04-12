package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import simulator.control.*;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.launcher.Main;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

class ChangeRegionsDialog extends JDialog implements EcoSysObserver {
	private DefaultComboBoxModel<String> _regionsModel;
	private DefaultComboBoxModel<String> _fromRowModel;
	private DefaultComboBoxModel<String> _toRowModel;
	private DefaultComboBoxModel<String> _fromColModel;
	private DefaultComboBoxModel<String> _toColModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _regionsInfo;
	private String[] _headers = { "Key", "Value", "Description" };

// TODO en caso de ser necesario, añadir los atributos aquí…
	ChangeRegionsDialog(Controller ctrl) {
		super((Frame) null, true);
		_ctrl = ctrl;
		initGUI();
		this._ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Change Regions");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		//revisar el prefered size del texto
		JLabel helpText = new JLabel("<html><p>Select a region type, the rows/cols interval, and provide values for the parameters in the <b>Value column</b> (default values are used for parameters with no value).</p></html>");
		JPanel helpTextPanel = new JPanel();
		helpText.setPreferredSize(new Dimension (675, 50));
		helpTextPanel.add(helpText);
		JPanel tablePanel = new JPanel ();
		JPanel comboBoxPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		mainPanel.add(helpTextPanel);
		mainPanel.add(tablePanel);
		mainPanel.add(comboBoxPanel);
		mainPanel.add(buttonPanel);
		
		
		
		
		
		
		
		
		
// TODO crear el texto de ayuda que aparece en la parte superior del diálogo y
// añadirlo al panel correspondiente diálogo (Ver el apartado Figuras)
// _regionsInfo se usará para establecer la información en la tabla
		_regionsInfo = Main._regionFactory.get_info();

// _dataTableModel es un modelo de tabla que incluye todos los parámetros de
// la region
		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
				//return rootPaneCheckingEnabled;
// TODO hacer editable solo la columna 1
			}
		};
		// TODO crear un JTable que use _dataTableModel, y añadirlo al diálogo
		_dataTableModel.setColumnIdentifiers(_headers);

		JTable jt =  new JTable (this._dataTableModel);
		
		tablePanel.add(new JScrollPane (jt));
		
		
				
// _regionsModel es un modelo de combobox que incluye los tipos de regiones
		_regionsModel = new DefaultComboBoxModel<>();
// TODO añadir la descripción de todas las regiones a _regionsModel, para eso
// usa la clave “desc” o “type” de los JSONObject en _regionsInfo,
		//funciona pero revisar si es asi
		for (int i = 0; i < this._regionsInfo.size(); i++) {
			this._regionsModel.addElement(this._regionsInfo.get(i).getString("type"));
	
	
		}
		
		
		
// ya que estos nos dan información sobre lo que puede crear la factoría.
// TODO crear un combobox que use _regionsModel y añadirlo al diálogo.
		
		JComboBox region = new JComboBox (this._regionsModel);
		comboBoxPanel.add(new JLabel ("Region type:"));
		
		comboBoxPanel.add(region);
		
// TODO crear 4 modelos de combobox para _fromRowModel, _toRowModel,
// _fromColModel y _toColModel.
		this._fromRowModel = new DefaultComboBoxModel<>();
		this._toRowModel = new DefaultComboBoxModel<>();
		this._fromColModel = new DefaultComboBoxModel<>();
		this._toColModel = new DefaultComboBoxModel<>();
		
// TODO crear 4 combobox que usen estos modelos y añadirlos al diálogo.
		JComboBox combo_fromRowModel = new JComboBox (_fromRowModel);
		JComboBox combo_toRowModel= new JComboBox (_toRowModel);
		JComboBox combo_fromColModel= new JComboBox (_fromColModel);
		JComboBox combo_toColModel= new JComboBox (_toColModel);
		
		//TODO BUSCA LA MANERA DE PONER LAS FILAS Y LAS COLUMNAS BIEN
		
		comboBoxPanel.add(new JLabel ("Row from/to:"));
		comboBoxPanel.add(combo_fromRowModel);
		comboBoxPanel.add(combo_toRowModel);
		comboBoxPanel.add(new JLabel ("Column from/to:"));
		comboBoxPanel.add(combo_fromColModel);
		comboBoxPanel.add(combo_toColModel);
		
	
	
		
// TODO crear los botones OK y Cancel y añadirlos al diálogo.
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		setPreferredSize(new Dimension(700, 400)); // puedes usar otro tamaño
		pack();
		setResizable(false);
		setVisible(false);
	}

	public void open(Frame parent) {
		setLocation(//
				parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2, //
				parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
	}
// TODO el resto de métodos van aquí…


	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method s
		for (int i = 0; i < map.get_rows(); i++) {
			_fromRowModel.addElement(String.valueOf(i));
			_toRowModel.addElement(String.valueOf(i));
		}

		for (int i = 0; i < map.get_cols(); i++) {
			_fromColModel.addElement(String.valueOf(i));
			_toColModel.addElement(String.valueOf(i));
		}
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
