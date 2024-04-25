package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.control.Controller;
import simulator.launcher.Main;
import simulator.misc.Utils;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private ChangeRegionsDialog _changeRegionsDialog;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; 
	private JButton _quitButton;

	private JButton _openButton;
	private JButton _viewerButton;
	private JButton _regionButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JTextField _dt;
	private JSpinner _stepsSpinner;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		this._changeRegionsDialog = new ChangeRegionsDialog(this._ctrl); 

	}

	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);

		// OPEN FILE BUTTON
		this._openButton = new JButton();
		this._openButton.setToolTipText("load");
		this._openButton.setIcon(new ImageIcon("resources/icons/open.png"));
		this._openButton.addActionListener((e) -> {
			this._fc = new JFileChooser();
			_fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources/examples"));
			try {
				this.openActions();
			} catch (FileNotFoundException e1) {
			
				e1.printStackTrace();
			}

		});
		this._toolaBar.add(this._openButton);
		_toolaBar.addSeparator();

		// MAP BUTTON
		this._viewerButton = new JButton();
		this._viewerButton.setToolTipText("map");
		this._viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		this._viewerButton.addActionListener((e) -> {
			MapWindow map = new MapWindow(null, this._ctrl); 

		});
		this._toolaBar.add(this._viewerButton);

		// REGION BUTTON
		this._regionButton = new JButton();
		this._regionButton.setToolTipText("change region");
		this._regionButton.setIcon(new ImageIcon("resources/icons/regions.png"));
		this._regionButton.addActionListener((e) -> _changeRegionsDialog.open(ViewUtils.getWindow(this)));
		this._toolaBar.add(this._regionButton);
		_toolaBar.addSeparator();

		// RUN BUTTON
		this._runButton = new JButton();
		this._runButton.setToolTipText("run");
		this._runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		this._runButton.addActionListener((e) -> {
			this._openButton.setEnabled(false);
			this._viewerButton.setEnabled(false);
			this._regionButton.setEnabled(false);
			this._runButton.setEnabled(false);
			this._quitButton.setEnabled(false);
			this._stopped = false;

			int steps = (int) this._stepsSpinner.getValue();

			double dt = Double.valueOf(this._dt.getText());

			this.run_sim(steps, dt);

		});
		this._toolaBar.add(this._runButton);

		// STOP BUTTON
		this._stopButton = new JButton();
		this._stopButton.setToolTipText("Stop");
		this._stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		this._stopButton.addActionListener((e) -> {
			this._openButton.setEnabled(true);
			this._viewerButton.setEnabled(true);
			this._regionButton.setEnabled(true);
			this._runButton.setEnabled(true);
			this._quitButton.setEnabled(true);
			this._stopped = true;
		});
		this._toolaBar.add(this._stopButton);

		_toolaBar.add(new JLabel(" Steps: "));
		_stepsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 10));
		_stepsSpinner.setToolTipText("Simulation steps to run: 1-10000");
		_stepsSpinner.setMaximumSize(new Dimension(80, 40));
		_stepsSpinner.setMinimumSize(new Dimension(80, 40));
		_stepsSpinner.setPreferredSize(new Dimension(80, 40));
		_toolaBar.add(_stepsSpinner);

		_toolaBar.add(new JLabel(" DELTA-TIME: "));
		if (Main._delta != null) {
			this._dt = new JTextField(String.valueOf(Main._delta));
		} else
			this._dt = new JTextField("0.03");

		this._dt.setMaximumSize(new Dimension(80, 40));
		this._dt.setMinimumSize(new Dimension(80, 40));
		this._dt.setPreferredSize(new Dimension(80, 40));
		this._toolaBar.add(this._dt);

		this._toolaBar.addSeparator();
		// Quit Button
		_toolaBar.add(Box.createGlue());
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> ViewUtils.quit(this));
		_toolaBar.add(_quitButton);

	}

	private void run_sim(double n, double dt) {
		if (n > 0 && !_stopped) {
			try {
				System.out.println(n);
				_ctrl.advance(dt);
				Thread.sleep((long) (dt*500));
				SwingUtilities.invokeLater(() -> run_sim(n - 1, dt));
			} catch (Exception e) {

				ViewUtils.showErrorMsg(e.getMessage());
				this.enableButtons();
			}
		} else {
			this.enableButtons();
		}
	}

	private void openActions() throws FileNotFoundException {
		this._fc.showOpenDialog(ViewUtils.getWindow(this));

		File file = this._fc.getSelectedFile();
		if (file != null) {
			FileInputStream fis = new FileInputStream(file);
			JSONObject json = new JSONObject(new JSONTokener(fis));

			int cols = json.getInt("cols");
			int rows = json.getInt("rows");
			int width = json.getInt("width");
			int height = json.getInt("height");

			this._ctrl.reset(cols, rows, width, height);

			this._ctrl.load_data(json);
		}

	}

	private void enableButtons() {
		_stopped = true;
		_openButton.setEnabled(true);
		_viewerButton.setEnabled(true);
		_regionButton.setEnabled(true);
		_runButton.setEnabled(true);
		_stopButton.setEnabled(true);

	}
}