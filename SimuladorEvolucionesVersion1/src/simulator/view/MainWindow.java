package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
	ControlPanel controlPanel = new ControlPanel(_ctrl);
	mainPanel.add(controlPanel, BorderLayout.PAGE_START);
	StatusBar statusBar = new StatusBar (this._ctrl);
	mainPanel.add(statusBar, BorderLayout.PAGE_END);
	JPanel contentPanel = new JPanel();
	contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	mainPanel.add(contentPanel, BorderLayout.CENTER);
	contentPanel.add(new InfoTable("Species", new SpeciesTableModel(_ctrl))).setPreferredSize(new Dimension(500, 250));
	contentPanel.add(new InfoTable("Regions", new RegionsTableModel(_ctrl))).setPreferredSize(new Dimension(500, 250));
	addWindowListener(new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {
			
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			ViewUtils.quit(MainWindow.this);
			
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
		
		}

		@Override
		public void windowIconified(WindowEvent e) {
			
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			
			
		}
		
	});
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pack();
	setVisible(true);
	}
}