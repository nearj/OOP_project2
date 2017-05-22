package uos;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import uos.file.FileSystem;
import uos.gui.GUISystem;
import uos.parse.Classes;
import uos.parse.Methods;
import uos.parse.PSource;

/** -- Done -- */
// < package: uos >
// Accessor_Modifier
// Delim
// Type

// < package: uos.parse >

// < package: file >
// FileSystem - open, read and parse system with GUI

/** -- In progress -- */
// < package: uos.parse >
// Parse
// Classes
// Methods

// PSource
// PClass
// PMethod

// < package: file >
// FileSystem - save system

// < package: gui >
// GUISystem - whole sturcture, tree, menu bar, method-member list
// ^ left side

/** -- Not In Progress -- */
//< package: uos >
// App

// < package: uos.parse >
// Consturctors
// Members
// PMember

//< package: gui >
// GUISystem - class table, method text

/** -- 1st required -- */
// TODO-1st: Interface PMember
// TODO-1st: Class Members
// TODO-1st: GUI - method text area
// TODO-1st: GUI - class table area

/** -- 2nd required -- */
// TODO-2nd: connect Members/PMember class with parse class.
// TODO-2nd: class Members must integrate class Constructors

/** -- 3rd required -- */
// TODO-3rd: improve GUI
// TODO-3rd: improve performance

/** -- jonghun: doing -- */
// Opinion:
// -- Current --

/** -- hayonh: doing -- */
// Opinion: integral Constructors and method. Since consturctor is another type of method, that is constructor is method.
// Opinion: save example file( save.txt ) is uploaded. Please look at least once.
// Opinion: GUI system structure will be set as GridBagLayOut. 
// ^ ref) https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
// -- Current --
// TODO-1st: tree, menubar, whole structure. 
// TODO-2nd: to carry out whole GUI structure.
// TODO-3rd: to carry out save file with GUI

public class App {
	private static PSource pSource = new PSource();

	public static void main(String... args) {
		SwingUtilities.invokeLater( new GUISystem() );
		/*
		JFrame jFrame = new JFrame();
		JPanel jPanel = new JPanel();
		JScrollPane jsp = new JScrollPane();
		jPanel.add(jsp, new BorderLayout() );
		jPanel.setSize( 300, 300 );
		jFrame.setSize( 300, 300 );
		jFrame.add(jPanel);
		jFrame.setVisible(true);
		jsp.getViewport().removeAll();
		jsp.getViewport().add(classTree());
		jPanel.revalidate();
		jPanel.repaint();
		*/

		
	}
	
	private static JTree classTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

		for( Classes classes : pSource.getClassList() ) {
			DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(classes) {
				private static final long serialVersionUID = -1405861207465188691L;
				
				@Override public String toString() {
					return classes.getName();
				}
			};
			
			
			for( Methods methods : classes.getMethodList() ){
				DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(methods) {
					private static final long serialVersionUID = -6298724158229241038L;

					@Override public String toString() {
						StringBuilder strBuilder = new StringBuilder();
						Iterator<String> iterator = methods.getParams().keySet().iterator();
						while( iterator.hasNext() ) {
							strBuilder.append( methods.getParamType( iterator.next() ).getTypeName() );
							if( !iterator.hasNext() ) break;
							strBuilder.append(", ");
						}
						return methods.getName() + "(" + strBuilder.toString() + ")";
					}
				};
				
				classNode.add(methodNode);
			}
			root.add(classNode);
		}
    	return new JTree(root);
	}

}
