package uos.gui;

import java.io.File;

import java.util.List;
import java.util.Iterator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;


import uos.file.FileSystem;
import uos.parse.Classes;
import uos.parse.Members;
import uos.parse.Methods;
import uos.parse.PSource;


public class GUISystem implements Runnable {

	private static final int DEFAULT_SIZE_X = 959;
	private static final int DEFAULT_SIZE_Y = 757;
	private int size_x = DEFAULT_SIZE_X;
	private int size_y = DEFAULT_SIZE_Y;
	private static PSource pSource;
	
	private static JFrame mainFrame = new JFrame();
	private static JPanel mainPanel = new JPanel( new GridBagLayout() );
	private static JMenuBar jmb = new JMenuBar();
	
	private static final JScrollPane DEFAULT_TREE =
			new JScrollPane(); 
	private static final JScrollPane DEFAULT_RIGHT =
			new JScrollPane(); 
	private static final JScrollPane DEFAULT_INFO = 
			new JScrollPane();
	private static GridBagConstraints gridBagCstr = new GridBagConstraints();
	@Override
	public void run() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBackground(Color.BLACK);
		mainPanel.setBackground(Color.WHITE);
		mainFrame.add(mainPanel);
		mainFrame.setTitle("UOS OOP class 2nd project - parsing program");
		mainFrame.setMinimumSize( new Dimension(800, 600) );
		init();
		mainFrame.setVisible(true);
		
	}
	
	static void init() {
		GridBagConstraints gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.HORIZONTAL;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 0;
		gridBagCstr.gridwidth = 2;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 1.0d;
		gridBagCstr.anchor = GridBagConstraints.CENTER;
		mainPanel.setSize( DEFAULT_SIZE_X, DEFAULT_SIZE_Y);
		mainPanel.add( jmb, gridBagCstr );
		
		gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 1;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 0.4d;
		gridBagCstr.weighty = 0.6d;

		DEFAULT_TREE.getViewport().setBackground(Color.WHITE);
		JLabel treeLabel = new JLabel("Hello there? I am in the tree part");
		treeLabel.setHorizontalAlignment(JLabel.CENTER);
		DEFAULT_TREE.getViewport().add(treeLabel);
		mainPanel.add( DEFAULT_TREE, gridBagCstr );
		
		gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 2;
		gridBagCstr.gridwidth =	1;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 0.4d;
		gridBagCstr.weighty = 0.3d;

		gridBagCstr.anchor = GridBagConstraints.CENTER;
		DEFAULT_INFO.getViewport().setBackground(Color.WHITE);
		JLabel infoLable = new JLabel("Hello there? I am in the info part");
		infoLable.setHorizontalAlignment(JLabel.CENTER);
		DEFAULT_INFO.getViewport().add(infoLable);
		mainPanel.add( DEFAULT_INFO, gridBagCstr);
		
		gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 1;
		gridBagCstr.gridy = 1;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 2;
		gridBagCstr.weightx = 0.6d;
		gridBagCstr.weighty = 0.9d;
		gridBagCstr.anchor = GridBagConstraints.CENTER;
		JLabel rightLable = new JLabel("Hello there? I am in the right part");
		rightLable.setHorizontalAlignment(JLabel.CENTER);
		DEFAULT_RIGHT.getViewport().add(rightLable);
		DEFAULT_RIGHT.getViewport().setBackground(Color.WHITE);
		mainPanel.add( DEFAULT_RIGHT, gridBagCstr);
		mainPanel.revalidate();
		mainPanel.repaint();
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	
	private static void classTree() {
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
		JTree classTree = new JTree(root);
		classTree.addTreeSelectionListener( new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode nodeObj = 
						(DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				if( nodeObj.getUserObject() instanceof Classes ) {
					// TODO: table stub.
					System.out.println( ((Classes) nodeObj.getUserObject()).getName() );
				} else if ( nodeObj.getUserObject() instanceof Methods ) {
					memberList(((Methods) nodeObj.getUserObject()).getMemberList());
				} else if ( nodeObj.getUserObject() instanceof Members ) {
					// TODO: member stub.
					System.out.println("members");
				}
				
			}
		});

		DEFAULT_TREE.getViewport().remove(0);
		DEFAULT_TREE.getViewport().revalidate();
		
		GridBagConstraints gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 1;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 0.4d;
		gridBagCstr.weighty = 0.6d;
		gridBagCstr.anchor = GridBagConstraints.CENTER;
		Font font = classTree.getFont();
		classTree.setFont(new Font(font.getName(), Font.PLAIN, 18) );
		classTree.setVisibleRowCount(5);
		classTree.setRootVisible(false);
		classTree.setMinimumSize(new Dimension((int) (DEFAULT_SIZE_X * 0.4),(int) (DEFAULT_SIZE_Y * 0.6)));
		for( int i = 0 ; i < classTree.getRowCount(); ) {
			classTree.expandRow(i++);
		}
		
		DEFAULT_TREE.getViewport().add(classTree, gridBagCstr);
		DEFAULT_TREE.getViewport().revalidate();
    	mainPanel.revalidate();
    	mainFrame.pack();
    	mainPanel.repaint();
	}
	
	
	static {
		JMenu jmFile = new JMenu("File");
		jmFile.setMnemonic(KeyEvent.VK_F);

		JMenuItem jmiOPEN = new JMenuItem("Open File");
		jmiOPEN.setMnemonic(KeyEvent.VK_O);
		jmiOPEN.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem jmiSAVE = new JMenuItem("SAVE");
		jmiSAVE.setMnemonic(KeyEvent.VK_S);
		jmiSAVE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		JMenuItem jmiCLOSE = new JMenuItem("CLOSE");
		jmiCLOSE.setMnemonic(KeyEvent.VK_C);
		jmiCLOSE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
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
		    	jFileChooser.setSelectedFile(new File(FileSystem.DEFAULT_DIR));
		    	jFileChooser.setCurrentDirectory(new File(FileSystem.DEFAULT_PATH));
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
		    		classTree();
		    	} else if( retVal == JFileChooser.CANCEL_OPTION );
			}
		});
		
		jmiSAVE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
		    	jFileChooser.setDialogTitle("Save File");
		    	jFileChooser.setCurrentDirectory(new File(FileSystem.DEFAULT_PATH));
		    	jFileChooser.setSelectedFile(new File(pSource.getFileName()));
		    	jFileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
		    	jFileChooser.setFileFilter( new FileFilter() {
					@Override
					public String getDescription() {
						return "text files";
					}
					
					@Override
					public boolean accept(File f) {
						if( f.isDirectory() ) return true;
						
						if( f.getName().substring(f.getName().lastIndexOf('.') + 1 ).equals("txt") )
							return true;
						else return false;
					}
				});
		    	
		    	int retVal = jFileChooser.showSaveDialog(null);
		    	if( retVal == JFileChooser.APPROVE_OPTION ) {
		    		FileSystem.save( jFileChooser.getSelectedFile(), pSource );
		    	}
			}
		});
		
		jmiCLOSE.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint();
				init();
				pSource.clearClassList();
				pSource = null;
			}
		});
		
		jmiEXIT.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileSystem.exit();
			}
		});
		jmb.setMinimumSize( new Dimension(300, 21));
		jmb.add(jmFile);
	}
	
	private static void memberList( List<Members> memberList ) {
		DefaultListModel<String> dlm = new DefaultListModel<>();
		dlm.addElement("Use:");
		for( Members members : memberList ) {
			dlm.addElement(members.getName());
		}

		DEFAULT_INFO.getViewport().remove(0);
		DEFAULT_INFO.getViewport().revalidate();
		
		GridBagConstraints gridBagCstr = new GridBagConstraints();
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 2;
		gridBagCstr.gridwidth =	1;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 0.4d;
		gridBagCstr.weighty = 0.3d;
		gridBagCstr.anchor = GridBagConstraints.CENTER;
		JList<String> jList = new JList<>(dlm);
		Font font = jList.getFont();
		jList.setFont(new Font(font.getName(), font.getStyle(), 20) );
		jList.setMinimumSize(new Dimension((int) (DEFAULT_SIZE_X * 0.4),(int) (DEFAULT_SIZE_Y * 0.3)));
		DEFAULT_INFO.getViewport().add(jList, gridBagCstr);
    	mainPanel.revalidate();
    	mainFrame.pack();
    	mainPanel.repaint();
	}

}
