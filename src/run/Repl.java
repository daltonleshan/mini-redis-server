package run;

import Gui.Gui;

public class Repl {

	private int port;

	public Repl(int port) {
		this.port = port;
		repl();
	}

	private void repl() {
		Gui g = new Gui();
		g.runSparkServer(port);
	}
}
