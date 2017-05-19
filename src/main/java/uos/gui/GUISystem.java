package uos.gui;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import uos.file.FileSystem;
import uos.parse.Classes;
import uos.parse.Members;
import uos.parse.Methods;
import uos.parse.PSource;


public class GUISystem {
	
	private static final int DEFAULT_SIZE_X = 540;
	private static final int DEFAULT_SIZE_Y = 360;
	private int size_x = DEFAULT_SIZE_X;
	private int size_y = DEFAULT_SIZE_Y;
	private static PSource pSource;
	
	private static JFrame mainFrame = new JFrame();
	
	static {
		JTree classTree = new JTree();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		classTree.setRootVisible(false);
		
		List<DefaultMutableTreeNode> classNodeList = new ArrayList<>();
		
		for( Classes classes : pSource.getClassList() ) {
			root.add( new DefaultMutableTreeNode(classes){

				private static final long serialVersionUID = -1405861207465188691L;

				@Override public String toString() {
					return classes.getName();
				}
			});
		}
		
		for( DefaultMutableTreeNode classNode : classNodeList ) {
			Classes classes = (Classes) classNode.getUserObject();
			for( Methods methods : classes.getMethodList() ) {
				classNode.add( new DefaultMutableTreeNode(methods) {

					private static final long serialVersionUID = -6298724158229241038L;

					@Override public String toString() {
						StringBuilder strBuilder = new StringBuilder();
						Iterator<String> iterator = methods.getParams().keySet().iterator();
						while( iterator.hasNext() ) {
							strBuilder.append( methods.getParamType( iterator.next() ) );
							if( !iterator.hasNext() ) break;
							strBuilder.append(", ");
						}
						return methods.getName() + "(" + strBuilder.toString() + ")";
					}
				});
			}
			for( Members members : classes.getMemberList() ) {
				classNode.add( new DefaultMutableTreeNode(members) {

					private static final long serialVersionUID = 1533853619145227062L;
					
					@Override public String toString() {
						// TODO: Member stub.
						return null;
					}
				});
			}
		}
		mainFrame.setLayout( new GridBagLayout() );
		
		JPanel treePanel = new JPanel();
		treePanel.setLayout( new BorderLayout() );
		JScrollPane treePane = new JScrollPane(classTree);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 0.3;
		treePanel.add( treePane,  gbc1 );
	}
	
	static {
		JMenuBar jmb = new JMenuBar();
		JMenu jmFile = new JMenu("File");
		JMenuItem jmiOPEN = new JMenuItem("Open File");
		JMenuItem jmiSAVE = new JMenuItem("SAVE");
		JMenuItem jmiCLOSE = new JMenuItem("CLOSE");
		JMenuItem jmiEXIT = new JMenuItem("EXIT");
		jmFile.add(jmiOPEN);
		jmFile.add(jmiSAVE);
		jmFile.add(jmiCLOSE);
		jmFile.add(jmiEXIT);
		
		jmiOPEN.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
		    	jFileChooser.setDialogTitle("Open File");
		    	jFileChooser.setCurrentDirectory(new File("."));
		    	jFileChooser.setDialogType( JFileChooser.OPEN_DIALOG );
		    	jFileChooser.setFileFilter( new FileFilter() {
					@Override
					public String getDescription() {
						return "cpp files";
					}
					
					@Override
					public boolean accept(File f) {
						if( f.isDirectory() ) return true;
						
						if( f.getName().substring(f.getName().lastIndexOf('.') + 1 ).equals("cpp") )
							return true;
						else return false;
					}
				});
		    	
		    	int retVal = jFileChooser.showOpenDialog(null);
		    	
		    	if( retVal == JFileChooser.APPROVE_OPTION ) {
		    		pSource = FileSystem.read( jFileChooser.getSelectedFile() );
		    	}	
			}
		});
		
		jmiSAVE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		jmiEXIT.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	private static JList memberList( List<Members> memberList ) {
		JList<String> jList = new JList<>();
		jList.setName("Member");
		for( Members members : memberList ) {
			jList.add( new JLabel(members.getName()) );
		}
		return jList;
	}
}


