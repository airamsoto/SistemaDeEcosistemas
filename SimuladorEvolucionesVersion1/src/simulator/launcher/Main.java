package simulator.launcher;

import java.io.File;

import java.io.FileInputStream;
import java.io.InputStream;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.misc.Utils;
import simulator.model.Simulator;
import simulator.view.MainWindow;
import simulator.model.*;
import simulator.factories.*;
import simulator.factories.SelectClosestBuilder;
import simulator.factories.SelectFirstBuilder;
import simulator.control.*;

import java.util.*;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

	private enum ExecMode {
		BATCH("batch", "Batch mode"), GUI("gui", "Graphical User Interface mode");

		private String _tag;
		private String _desc;

		private ExecMode(String modeTag, String modeDesc) {
			_tag = modeTag;
			_desc = modeDesc;
		}

		public String get_tag() {
			return _tag;
		}

		public String get_desc() {
			return _desc;
		}
	}

	// default values for some parameters
	//
	private final static Double _default_time = 10.0; // in seconds
	public final static Double _default_delta = 0.03;
	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _time = null;
	public static Double _delta = null;
	private static String _in_file = null;
	private static String _out_file = null;
	private static boolean _sv = false;
	private static ExecMode _mode = ExecMode.GUI;
	public static Factory<Animal> _animalFactory;
	public static Factory<Region> _regionFactory;

	private static void parse_args(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = build_options();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parse_help_option(line, cmdLineOptions);
			parse_m_option(line);
			parse_in_file_option(line);
			parse_time_option(line);
			parse_delta_option(line);
			parse_output_option(line);
			parse_sv_option(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options build_options() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("A configuration file.").build());

		// steps
		cmdLineOptions.addOption(Option.builder("t").longOpt("time").hasArg()
				.desc("An real number representing the total simulation time in seconds. Default value: "
						+ _default_time + ".")
				.build());
		// dt
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time <arg>").hasArg().desc(
				"A double representing actual time, in seconds, per simulation step. Default value:" + _default_delta)
				.build());

		// 0
		cmdLineOptions.addOption(Option.builder("o").longOpt("output <arg>").hasArg()
				.desc("Output file, where output is written.\n").build());
		// sv
		cmdLineOptions.addOption(
				Option.builder("sv").longOpt("simple-viewer").desc("Show the viewer window in console mode.").build());

		// m
		cmdLineOptions
				.addOption(Option.builder("m").longOpt("-m,--mode <arg>").hasArg()
						.desc("Execution Mode. Possible values: 'batch' (Batch\r\n"
								+ "mode), 'gui' (Graphical User Interface mode).\r\n" + "Default value: 'gui'")
						.build());
		return cmdLineOptions;
	}

	private static void parse_help_option(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parse_delta_option(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _default_delta.toString());
		try {
			_delta = Double.parseDouble(dt);
			assert (_delta >= 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time: " + dt);
		}
	}

	private static void parse_output_option(CommandLine line) throws ParseException {
		_out_file = line.getOptionValue("o");
		if (_mode == ExecMode.BATCH && _out_file == null) {
			throw new ParseException("In batch mode an output configuration file is required");
		}

	}

	private static void parse_sv_option(CommandLine line) throws ParseException {
		if (line.hasOption("sv"))
			_sv = true;

	}

	private static void parse_time_option(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", _default_time.toString());
		try {
			_time = Double.parseDouble(t);
			assert (_time >= 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time: " + t);
		}
	}

	private static void parse_in_file_option(CommandLine line) throws ParseException {
		_in_file = line.getOptionValue("i");
		if (_mode == ExecMode.BATCH && _in_file == null) {
			throw new ParseException("In batch mode an input configuration file is required");
		}
	}

	private static void parse_m_option(CommandLine line) throws ParseException {
		if (line.hasOption("m") && line.getOptionValue("m").equals("batch")) {
			_mode = ExecMode.BATCH;
		} else {
			if (line.hasOption("m") && line.getOptionValue("m").equals("gui")) {

				_mode = ExecMode.GUI;
			}
		}

	}

	private static void init_factories() {
		List<Builder<SelectionStrategy>> selection_strategy_builders = new ArrayList<>();
		selection_strategy_builders.add(new SelectFirstBuilder());
		selection_strategy_builders.add(new SelectClosestBuilder());
		selection_strategy_builders.add(new SelectYoungestBuilder());
		Factory<SelectionStrategy> selection_strategy_factory = new BuilderBasedFactory<SelectionStrategy>(
				selection_strategy_builders);
		List<Builder<Animal>> animal_builders = new ArrayList<>();
		animal_builders.add(new SheepBuilder(selection_strategy_factory));
		animal_builders.add(new WolfBuilder(selection_strategy_factory));
		_animalFactory = new BuilderBasedFactory<Animal>(animal_builders);
		List<Builder<Region>> region_builders = new ArrayList<>();
		region_builders.add(new DefaultRegionBuilder());
		region_builders.add(new DinamicSupplyRegionBuilder());
		_regionFactory = new BuilderBasedFactory<Region>(region_builders);

	}

	private static JSONObject load_JSON_file(InputStream in) {
		return new JSONObject(new JSONTokener(in));
	}

	private static void start_batch_mode() throws Exception {
		InputStream is = new FileInputStream(new File(_in_file));
		OutputStream outFile = new FileOutputStream(new File(_out_file));
		JSONObject json = load_JSON_file(is);
		int width = json.getInt("width");
		int height = json.getInt("height");
		int rows = json.getInt("rows");
		int cols = json.getInt("cols");
		Simulator simer = new Simulator(rows, cols, width, height, _animalFactory, _regionFactory);
		Controller cont = new Controller(simer);
		cont.load_data(json);
		cont.run(_time, _delta, _sv, outFile);
		outFile.close();

	}

	private static void start_GUI_mode() throws Exception {
		int width = 800;
		int height = 600;
		int rows = 15;
		int cols = 20;
		if (_in_file != null) {
			InputStream is = new FileInputStream(new File(_in_file));
			JSONObject json = load_JSON_file(is);
			width = json.getInt("width");
			height = json.getInt("height");
			rows = json.getInt("rows");
			cols = json.getInt("cols");

			Simulator simer = new Simulator(cols, rows, width, height, _animalFactory, _regionFactory);
			Controller cont = new Controller(simer);
			cont.load_data(json);
			SwingUtilities.invokeAndWait(() -> new MainWindow(cont));

		} else {
			Simulator simer = new Simulator(cols, rows, width, height, _animalFactory, _regionFactory);
			Controller cont = new Controller(simer);
			SwingUtilities.invokeAndWait(() -> new MainWindow(cont));
		}

	}

	private static void start(String[] args) throws Exception {
		init_factories();
		parse_args(args);
		switch (_mode) {
		case BATCH:
			start_batch_mode();
			break;
		case GUI:
			start_GUI_mode();
			break;
		}
	}

	public static void main(String[] args) {
		Utils._rand.setSeed(2147483647l);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
