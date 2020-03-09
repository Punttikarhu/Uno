package main;

import ui.Tekstikayttoliittyma;

public class Uno {

	public static void main(String[] args) {
		Uno.pelaa();
	}	

	public static void pelaa() {
		Tekstikayttoliittyma tk = new Tekstikayttoliittyma();
		tk.start();
	}

}
