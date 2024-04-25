package simulator.view;

import javax.swing.JPanel;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

public class InfoTable extends JPanel {
	String _title;
	TableModel _tableModel;

	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}

	private void initGUI() {

		this.setLayout(new BorderLayout()); 

		Border b = BorderFactory.createLineBorder(Color.BLACK, 2);
		this.setBorder(BorderFactory.createTitledBorder(b, this._title));
		JTable table = new JTable(this._tableModel);
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
	}
}