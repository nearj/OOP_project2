package uos;

import javax.swing.SwingUtilities;
import uos.gui.GUISystem;

public class App {
	public static void main( String... args ) {
		SwingUtilities.invokeLater( new GUISystem() );
	}
}
