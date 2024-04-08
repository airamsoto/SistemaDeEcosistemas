package simulator.view;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
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

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		// TODO crear los diferentes botones/atributos y añadirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para añadir la línea de separación vertical
		// entre las componentes que lo necesiten.

		// OPEN FILE BUTTON
		this._openButton = new JButton();
		this._openButton.setToolTipText("load");
		this._openButton.setIcon(new ImageIcon("resources/icons/open.png"));
		this._openButton.addActionListener((e) -> {
			this._fc = new JFileChooser();
			_fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources/examples"));
			_fc.showOpenDialog(ViewUtils.getWindow(this));
			//resetar con los parametros correspondientes
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
			MapWindow map = new MapWindow(null, this._ctrl); //REVISAR NULL
			
			
			
			
		});
		this._toolaBar.add(this._viewerButton);
		_toolaBar.addSeparator();

		// REGION BUTTON
		this._regionButton = new JButton();
		this._regionButton.setToolTipText("change region");
		this._regionButton.setIcon(new ImageIcon("resources/icons/regions.png"));
		this._regionButton.addActionListener((e)->
		_changeRegionsDialog.open(ViewUtils.getWindow(this)));
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
			this._stopped = true; // es asi?
			/*
			 * obtener valores de delta y el numero de paso del jspinner
			 */
			int steps = 0, dt = 0;
			this.run_sim(steps, dt);
			});
		this._toolaBar.add(this._runButton);
		_toolaBar.addSeparator();
		
		//STOP BUTTON
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
		this._toolaBar.addSeparator();
		
		
		// Quit Button
		// _toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> ViewUtils.quit(this));
		_toolaBar.add(_quitButton);

		_toolaBar.addSeparator();

		// TODO Inicializar _fc con una instancia de JFileChooser. Para que siempre
		// abre en la carpeta de ejemplos puedes usar:
	//	this._fc = new JFileChooser();
	//	_fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources/examples"));
		// TODO Inicializar _changeRegionsDialog con instancias del diálogo de cambio de
		// regiones
		// this._changeRegionsDialog = new ChangeRegionDialog();
	//this._changeRegionsDialog = new ChangeRegionsDialog(this._ctrl);
	}

	// TODO el resto de métodos van aquí…

	/*
	 * Fíjate que el método run_sim tal y como está definido garantiza que el
	 * interfaz no se quedará bloqueado. Para entender este comportamiento modifica
	 * run_sim para incluir solo for(int i=0;i<n;i++) _ctrl.advance(dt) — ahora, al
	 * comenzar la simulación, no verás los pasos intermedios, únicamente el estado
	 * final, además de que la interfaz estará completamente bloqueada.
	 */
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