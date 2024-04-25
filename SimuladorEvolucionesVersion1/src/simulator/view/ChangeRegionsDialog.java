package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import simulator.control.*;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.launcher.Main;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class ChangeRegionsDialog extends JDialog implements EcoSysObserver {
	private static final long serialVersionUID = 6417401071679027376L;
	private DefaultComboBoxModel<String> _regionsModel;
	private DefaultComboBoxModel<String> _fromRowModel;
	private DefaultComboBoxModel<String> _toRowModel;
	private DefaultComboBoxModel<String> _fromColModel;
	private DefaultComboBoxModel<String> _toColModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _regionsInfo;
	private String[] _headers = { "Key", "Value", "Description" };

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
		JLabel helpText = new JLabel(
				"<html><p>Select a region type, the rows/cols interval, and provide values for the parameters in the <b>Value column</b> (default values are used for parameters with no value).</p></html>");
		JPanel helpTextPanel = new JPanel();
		helpText.setPreferredSize(new Dimension(675, 50));
		helpTextPanel.add(helpText);
		JPanel tablePanel = new JPanel();
		JPanel comboBoxPanel = new JPanel();

		JPanel buttonPanel = new JPanel();
		mainPanel.add(helpTextPanel);
		mainPanel.add(tablePanel);
		mainPanel.add(comboBoxPanel);
		mainPanel.add(buttonPanel);

		_regionsInfo = Main._regionFactory.get_info();

		_dataTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
				
			}
		};

		_dataTableModel.setColumnIdentifiers(_headers);
		tablePanel.setLayout(new BorderLayout());
		JTable jt = new JTable(this._dataTableModel);
		jt.getColumnModel().getColumn(2).setPreferredWidth(-1);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tablePanel.add(new JScrollPane(jt));

		_regionsModel = new DefaultComboBoxModel<>();

		for (int i = 0; i < this._regionsInfo.size(); i++) {
			this._regionsModel.addElement(this._regionsInfo.get(i).getString("type"));

		}
		Font letra = new Font("Arial", Font.PLAIN, 12);
		JComboBox region = new JComboBox(this._regionsModel);
		JLabel labelRegion = new JLabel("Region type:");
		labelRegion.setFont(letra);
		comboBoxPanel.add(labelRegion);

		comboBoxPanel.add(region);
		region.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int indice = region.getSelectedIndex();
				if (indice >= 0 && indice < _regionsInfo.size()) {
					JSONObject data = _regionsInfo.get(indice).optJSONObject("data");
					_dataTableModel.setRowCount(0);

					if (data != null) {
						for (String key : data.keySet()) {
							_dataTableModel.addRow(new Object[] { key, "", data.get(key) });
						}
					}
				}

			}

		});

		this._fromRowModel = new DefaultComboBoxModel<>();
		this._toRowModel = new DefaultComboBoxModel<>();
		this._fromColModel = new DefaultComboBoxModel<>();
		this._toColModel = new DefaultComboBoxModel<>();

		JComboBox combo_fromRowModel = new JComboBox(_fromRowModel);
		JComboBox combo_toRowModel = new JComboBox(_toRowModel);
		JComboBox combo_fromColModel = new JComboBox(_fromColModel);
		JComboBox combo_toColModel = new JComboBox(_toColModel);

		JLabel rowLabel = new JLabel("Row from/to:");
		rowLabel.setFont(letra);
		comboBoxPanel.add(rowLabel);
		comboBoxPanel.add(combo_fromRowModel);
		comboBoxPanel.add(combo_toRowModel);
		JLabel colLabel = new JLabel("Column from/to:");
		colLabel.setFont(letra);
		comboBoxPanel.add(colLabel);
		comboBoxPanel.add(combo_fromColModel);
		comboBoxPanel.add(combo_toColModel);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JSONArray jsonRegiones = new JSONArray();
				int fromCol = Integer.parseInt(_fromColModel.getSelectedItem().toString());
				int toCol = Integer.parseInt(_toColModel.getSelectedItem().toString());
				int fromRow = Integer.parseInt(_fromRowModel.getSelectedItem().toString());
				int toRow = Integer.parseInt(_toRowModel.getSelectedItem().toString());
				JSONObject spec = new JSONObject();
		        spec.put("type", _regionsInfo.get(region.getSelectedIndex()).getString("type"));
		        JSONObject region_data = new JSONObject();

		        for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
		            String key = (String) _dataTableModel.getValueAt(i, 0);
		            String value = (String) _dataTableModel.getValueAt(i, 1);
		           
		            if (!value.isEmpty()) {
		                region_data.put(key, value);
		            }
		        }

		        spec.put("data", region_data);

		       
		        JSONObject regionObject = new JSONObject();
		        JSONArray rowArray = new JSONArray();
		        JSONArray colArray = new JSONArray();

		        rowArray.put(fromRow);
		        rowArray.put(toRow);
		        colArray.put(fromCol);
		        colArray.put(toCol);

		        regionObject.put("row", rowArray);
		        regionObject.put("col", colArray);
		        regionObject.put("spec", spec);

		        jsonRegiones.put(regionObject);
		   
		        try {
		            _ctrl.set_regions(jsonRegiones);
		            setVisible(false); 
		        } catch (Exception ex) {
		            ViewUtils.showErrorMsg(ex.getMessage());
		        }
		    

			}

		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		setPreferredSize(new Dimension(700, 400)); //	TODO  puedes usar otro tamaño
		pack();
		setResizable(false);
		setVisible(false);
	}

	public void open(Frame parent) {
		setLocation(
				parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2, //
				parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		this._fromColModel.removeAllElements();
		this._fromRowModel.removeAllElements();
		this._toColModel.removeAllElements();
		this._toRowModel.removeAllElements();

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
		this.onRegister(time, map, animals);

	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		

	}
}
