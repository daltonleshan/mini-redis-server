package run;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * The Main class of our project. This is where execution begins.
 *
 * @author daltonleshan
 */
public final class Main {

	/**
	 * Default server for Spark
	 */
	private static final int DEFAULT_PORT = 4567;

	/**
	 * Arguments passed to main
	 */
	private String[] args;

	/**
	 * The initial method called when execution begins.
	 *
	 * @param args
	 *            An array of command line arguments
	 */
	public static void main(String[] args) {
		new Main(args).run();
	}

	/**
	 * Protected main class
	 * 
	 * @param args
	 *            arguments for main
	 */
	private Main(String[] args) {
		this.args = args;
	}

	/**
	 * Runs the program
	 */
	private void run() {
		// Parse command line arguments
		OptionParser parser = new OptionParser();
		parser.accepts("gui");
		parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
		OptionSet options = parser.parse(args);

		if (options.has("gui")) {
			new Repl((int) options.valueOf("port"));
			System.out.println("done");
		}
	}

}
