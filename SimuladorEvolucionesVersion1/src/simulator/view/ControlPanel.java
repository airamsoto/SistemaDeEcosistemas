package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

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

import simulator.control.Controller;
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
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;

// TODO añade más atributos aquí …
	// botones
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
		this._changeRegionsDialog = new ChangeRegionsDialog(this._ctrl); // SE INICIA AQUI?
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
			_fc.showOpenDialog(ViewUtils.getWindow(this));
			// resetar con los parametros correspondientes
			this._ctrl.reset(ALLBITS, ABORT, WIDTH, HEIGHT);
			this._ctrl.load_data(null);

		});
		this._toolaBar.add(this._openButton);
		_toolaBar.addSeparator();

		// MAP BUTTON
		this._viewerButton = new JButton();
		this._viewerButton.setToolTipText("map");
		this._viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		this._viewerButton.addActionListener((e) -> {
			MapWindow map = new MapWindow(null, this._ctrl); // REVISAR NULL

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

		/*	int steps = (int) this._stepsSpinner.getValue();
			double dt = Double.parseDouble(this._dt.getText());
		*/
			int steps = 10;
			double dt = 0.03;
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

		_stepsSpinner = new JSpinner(new SpinnerNumberModel(5000, 1, 10000, 100));
		_stepsSpinner.setToolTipText("Simulation steps to run: 1-10000");
		_stepsSpinner.setMaximumSize(new Dimension(80, 40));
		_stepsSpinner.setMinimumSize(new Dimension(80, 40));
		_stepsSpinner.setPreferredSize(new Dimension(80, 40));
		_toolaBar.add(_stepsSpinner);

		_toolaBar.add(new JLabel(" DELTA-TIME: "));
		this._dt = new JTextField();
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
//CAMBIO EN EL TIPO DE N
	private void run_sim(int n, double dt) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.advance(dt);
				SwingUtilities.invokeLater(() -> run_sim(n - 1, dt));
			} catch (Exception e) {
				// llamar a eso con el mensaje que corresponda
				ViewUtils.showErrorMsg(e.getMessage());

				// TODO activar todos los botones
				_stopped = true;
				_openButton.setEnabled(true);
				_viewerButton.setEnabled(true);
				_regionButton.setEnabled(true);
				_runButton.setEnabled(true);
				_stopButton.setEnabled(true);
			}
		} else {
			// TODO activar todos los botones
			_stopped = true;
			_openButton.setEnabled(true);
			_viewerButton.setEnabled(true);
			_regionButton.setEnabled(true);
			_runButton.setEnabled(true);
			_stopButton.setEnabled(true);
		}
	}

}