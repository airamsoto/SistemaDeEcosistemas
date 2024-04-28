package simulator.view;

import simulator.model.AnimalInfo;
import simulator.model.MapInfo;
import simulator.model.State;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class MapViewer extends AbstractMapViewer {

	private int _width;
	private int _height;

	private int _rows;
	private int _cols;

	int _rwidth;
	int _rheight;

	State _currState;

	volatile private Collection<AnimalInfo> _objs;
	volatile private Double _time;

	private static class SpeciesInfo {
		private Integer _count;
		private Color _color;

		SpeciesInfo(Color color) {
			_count = 0;
			_color = color;
		}
	}

	Map<String, SpeciesInfo> _kindsInfo = new HashMap<>();

	private Font _font = new Font("Arial", Font.BOLD, 12);

	private boolean _showHelp;

	public MapViewer() {
		initGUI();
	}

	private void initGUI() {

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'h':
					_showHelp = !_showHelp;

					repaint();
					break;
				case 's':
					changeState();
					repaint();
				default:
				}
			}

		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}
		});

		_currState = null;

		_showHelp = true;
	}

	private void changeState() {
		if (this._currState == null) {

			this._currState = State.values()[0];
		} else {
			int indice = this._currState.ordinal();
			int siguiente = (indice + 1) % (State.values().length);
			if (siguiente > State.values().length)
				this._currState = null;
			else
				this._currState = siguiente == 0 ? null : State.values()[siguiente];

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(_font);
		gr.setBackground(Color.WHITE);
		gr.clearRect(0, 0, _width, _height);

		if (_objs != null)
			drawObjects(gr, _objs, _time);

		if (this._showHelp) {
			g.setColor(Color.RED);
			this.drawStringWithRect(gr, 20, 40, "h: toggle help");
			this.drawStringWithRect(gr, 20, 60, "s: show animals of a specific state");
		}

	}

	private boolean visible(AnimalInfo a) {

		return (this._currState == null || a.get_state() == this._currState);

	}

	private void drawObjects(Graphics2D g, Collection<AnimalInfo> animals, Double time) {

		this._rwidth = this._width / this._cols;
		this._rheight = this._height / this._rows;
		for (int i = 0; i <= this._cols; i++) {
			int x = i * this._rwidth;
			g.drawLine(x, 0, x, this._height);

		}
		for (int j = 0; j <= this._rows; j++) {
			int y = j * this._rheight;
			g.drawLine(0, y, this._width, y);

		}

		for (AnimalInfo a : animals) {

			SpeciesInfo esp_info = _kindsInfo.get(a.get_genetic_code());

			if (!visible(a)) {
				continue;

			}
			Color color = ViewUtils.get_color(a.get_genetic_code());
			if (esp_info == null) {
				esp_info = new SpeciesInfo(color);
				this._kindsInfo.put(a.get_genetic_code(), esp_info);
			}

			esp_info._count++;
			Color colorA = esp_info._color;
			int tamanio = (int) (a.get_age() / 2 + 2);
			int posx = (int) a.get_position().getX();
			int posy = (int) a.get_position().getY();
			g.setColor(colorA);
			g.fillOval(posx, posy, tamanio, tamanio);
		}

		int yPos = this._height - 70;
		if (this._currState != null) {
			int extraPos = 20;
			yPos -= extraPos;

		}

		for (Entry<String, SpeciesInfo> e : _kindsInfo.entrySet()) {
			String speciesName = e.getKey();
			SpeciesInfo speciesInfo = e.getValue();
			g.setColor(speciesInfo._color);
			this.drawStringWithRect(g, 10, yPos, (speciesName + ": " + speciesInfo._count));
			yPos += 20;
			speciesInfo._count = 0;
		}
		g.setColor(Color.GREEN);
		this.drawStringWithRect(g, 10, yPos, "Time " + String.format("%.3f", time));

		if (this._currState != null) {
			g.setColor(Color.MAGENTA);
			g.drawString("visible state: " + this._currState.toString(), 10, yPos + 20);
		}

	}

	void drawStringWithRect(Graphics2D g, int x, int y, String s) {
		Rectangle2D rect = g.getFontMetrics().getStringBounds(s, g);
		g.drawString(s, x, y);
		g.drawRect(x - 1, y - (int) rect.getHeight(), (int) rect.getWidth() + 1, (int) rect.getHeight() + 5);
	}

	@Override
	public void update(List<AnimalInfo> objs, Double time) {
		this._objs = objs;
		this._time = time;
		this.repaint();

	}

	@Override
	public void reset(double time, MapInfo map, List<AnimalInfo> animals) {

		this._width = map.get_width();
		this._height = map.get_height();
		this._cols = map.get_cols();
		this._rows = map.get_rows();

		setPreferredSize(new Dimension(map.get_width(), map.get_height()));

		update(animals, time);
	}

}