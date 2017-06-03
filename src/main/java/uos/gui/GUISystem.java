package uos.gui;

import java.io.File;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Iterator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

import uos.Delim;
import uos.Type;
import uos.file.FileSystem;
import uos.gui.GUISystem.MyFrame;
import uos.parse.Classes;
import uos.parse.Members;
import uos.parse.Methods;
import uos.parse.PClass;
import uos.parse.PMethod;
import uos.parse.PSource;
import uos.parse.Parse;

public class GUISystem implements Runnable {

	private static final int DEFAULT_SIZE_X = 959;
	private static final int DEFAULT_SIZE_Y = 757;
	private static PSource pSource;

	private static JFrame mainFrame = new JFrame();
	private static JPanel mainPanel = new JPanel( new GridBagLayout() );
	private static JMenuBar jmb = new JMenuBar();
	private static final JScrollPane DEFAULT_TREE = new JScrollPane();
	private static final JScrollPane DEFAULT_INFO = new JScrollPane();
	public JTextArea methodContentText;

	@Override
	public void run() {

		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		mainFrame.setTitle( "UOS OOP class 2nd project - parsing program" );
		mainFrame.setMinimumSize( new Dimension( 800, 600 ) );
		mainPanel.setSize( DEFAULT_SIZE_X, DEFAULT_SIZE_Y );
		mainFrame.add( mainPanel );
		init();
		mainFrame.setVisible( true );

	}

	// ======================================== Init ============================================ //
	static void init() {
		GridBagConstraints gridBagCstr;

		// < Tree init >
		gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 1;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 0.4d;
		gridBagCstr.weighty = 0.6d;
		DEFAULT_TREE.getViewport().setBackground( Color.WHITE );
		DEFAULT_TREE.getViewport().add( initLabel() );
		mainPanel.add( DEFAULT_TREE, gridBagCstr );
		// < /Tree init >

		// < Info init >
		gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 2;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 0.4d;
		gridBagCstr.weighty = 0.3d;
		DEFAULT_INFO.getViewport().setBackground( Color.WHITE );
		DEFAULT_INFO.getViewport().add( initLabel() );
		mainPanel.add( DEFAULT_INFO, gridBagCstr );
		// < /Info init >

		// < Right init >
		gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.BOTH;
		gridBagCstr.gridx = 1;
		gridBagCstr.gridy = 1;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 2;
		gridBagCstr.weightx = 0.6d;
		gridBagCstr.weighty = 0.9d;
		mainPanel.add( initLabel(), gridBagCstr );
		// < Right init >

		mainPanel.setBackground( Color.WHITE );
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private static JLabel initLabel() {
		JLabel initLabel = new JLabel( "No data available" );
		initLabel.setHorizontalAlignment( JLabel.CENTER );
		return initLabel;
	}

	// ======================================== Menu ============================================ //
	static {
		JMenu jmFile = new JMenu( "File" );
		jmFile.setMnemonic( KeyEvent.VK_F );

		JMenuItem jmiOPEN = new JMenuItem( "Open File" );
		jmiOPEN.setMnemonic( KeyEvent.VK_O );
		jmiOPEN.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK ) );
		JMenuItem jmiSAVE = new JMenuItem( "SAVE" );
		jmiSAVE.setMnemonic( KeyEvent.VK_S );
		jmiSAVE.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK ) );
		JMenuItem jmiCLOSE = new JMenuItem( "CLOSE" );
		jmiCLOSE.setMnemonic( KeyEvent.VK_C );
		jmiCLOSE.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK ) );
		JMenuItem jmiEXIT = new JMenuItem( "EXIT" );
		jmFile.add( jmiOPEN );
		jmFile.add( jmiSAVE );
		jmFile.add( jmiCLOSE );
		jmFile.add( jmiEXIT );

		jmiOPEN.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle( "Open File" );
				jFileChooser.setSelectedFile( new File( FileSystem.DEFAULT_DIR ) );
				jFileChooser.setCurrentDirectory( new File( FileSystem.DEFAULT_PATH ) );
				jFileChooser.setDialogType( JFileChooser.OPEN_DIALOG );
				jFileChooser.setFileFilter( new FileFilter() {
					@Override
					public String getDescription() {
						return "cpp files";
					}

					@Override
					public boolean accept( File f ) {
						if ( f.isDirectory() )
							return true;

						if ( f.getName().substring( f.getName().lastIndexOf( '.' ) + 1 )
								.equals( "cpp" ) )
							return true;
						else
							return false;
					}
				} );

				int retVal = jFileChooser.showOpenDialog( null );
				if ( retVal == JFileChooser.APPROVE_OPTION ) {
					if ( pSource != null )
						pSource.clearClassList();
					pSource = FileSystem.read( jFileChooser.getSelectedFile() );
					createClassTree();
				} else if ( retVal == JFileChooser.CANCEL_OPTION )
					;
			}
		} );

		jmiSAVE.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle( "Save File" );
				jFileChooser.setCurrentDirectory( new File( FileSystem.DEFAULT_PATH ) );
				jFileChooser.setSelectedFile( new File( pSource.getFileName() ) );
				jFileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
				jFileChooser.setFileFilter( new FileFilter() {
					@Override
					public String getDescription() {
						return "text files";
					}

					@Override
					public boolean accept( File f ) {
						if ( f.isDirectory() )
							return true;

						if ( f.getName().substring( f.getName().lastIndexOf( '.' ) + 1 )
								.equals( "txt" ) )
							return true;
						else
							return false;
					}
				} );

				int retVal = jFileChooser.showSaveDialog( null );
				if ( retVal == JFileChooser.APPROVE_OPTION ) {
					FileSystem.save( jFileChooser.getSelectedFile(), pSource );
				}
			}
		} );

		jmiCLOSE.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				treePartController( null );
				infoPartControllor( null );
				rightPartControllor( null );
				pSource.clearClassList();
				pSource = null;
			}
		} );

		jmiEXIT.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				FileSystem.exit();
			}
		} );
		jmb.setMinimumSize( new Dimension( 300, 21 ) );
		jmb.add( jmFile );

		GridBagConstraints gridBagCstr = new GridBagConstraints();
		gridBagCstr.fill = GridBagConstraints.HORIZONTAL;
		gridBagCstr.gridx = 0;
		gridBagCstr.gridy = 0;
		gridBagCstr.gridwidth = 2;
		gridBagCstr.gridheight = 1;
		gridBagCstr.weightx = 1.0d;
		mainPanel.add( jmb, gridBagCstr );
	}

	// ========================================= Tree ============================================//
	private static void createClassTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode( "root" );
		for( Classes classes : pSource.getClassList() ) {
			DefaultMutableTreeNode classNode = new DefaultMutableTreeNode( classes ) {
				private static final long serialVersionUID = -1405861207465188691L;

				@Override
				public String toString() {
					return classes.getName();
				}
			};

			for( Methods methods : classes.getMethodList() ) {
				DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode( methods ) {
					private static final long serialVersionUID = -6298724158229241038L;

					@Override
					public String toString() {
						StringBuilder strBuilder = new StringBuilder();
						Iterator<String> iterator = methods.getParams().keySet().iterator();
						while( iterator.hasNext() ) {

							strBuilder.append(
									methods.getParamType( iterator.next() ).getTypeName() );
							if ( !iterator.hasNext() )
								break;
							strBuilder.append( ", " );
						}
						return methods.getName() + "(" + strBuilder.toString() + ")";
					}
				};
				classNode.add( methodNode );
			}

			for( Members members : classes.getMemberList() ) {
				DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode( members ) {
					private static final long serialVersionUID = -6298724158229241038L;

					@Override
					public String toString() {
						StringBuilder strBuilder = new StringBuilder();
						strBuilder.append(
								members.getName() + ": " + members.getReturnType().getTypeName() );
						if ( members.isArray() )
							strBuilder.append( Delim.ARRAY_OPEN + Delim.ARRAY_CLOSE );
						return strBuilder.toString();
					}
				};
				classNode.add( methodNode );
			}

			root.add( classNode );
		}
		JTree classTree = new JTree( root );
		classTree.addTreeSelectionListener( new TreeSelectionListener() {

			@Override
			public void valueChanged( TreeSelectionEvent e ) {
				DefaultMutableTreeNode nodeObj = (DefaultMutableTreeNode) e.getPath()
						.getLastPathComponent();
				if ( nodeObj.getUserObject() instanceof Classes ) {
					infoPartControllor( null );

					// TODO: table stub.
					System.out.println( ( (PClass) nodeObj.getUserObject() ).getContents() );

				} else if ( nodeObj.getUserObject() instanceof Methods ) {

					infoPartControllor(
							createUsedMemberList( ( (Methods) nodeObj.getUserObject() ) ) );
					rightPartControllor(
							createMethodContentText( ( (Methods) nodeObj.getUserObject() ) ) );

				} else if ( nodeObj.getUserObject() instanceof Members ) {

					infoPartControllor( null );

					rightPartControllor(
							createMemberRefTable( (Members) nodeObj.getUserObject() ) );

				}
			}
		} );
		Font font = classTree.getFont();
		classTree.setFont( new Font( font.getName(), Font.PLAIN, 18 ) );
		classTree.setVisibleRowCount( 5 );
		classTree.setRootVisible( false );
		classTree.setMinimumSize(
				new Dimension( (int) ( DEFAULT_SIZE_X * 0.4 ), (int) ( DEFAULT_SIZE_Y * 0.6 ) ) );
		for( int i = 0; i < classTree.getRowCount(); ) {
			classTree.expandRow( i++ );
		}

		DEFAULT_TREE.getViewport().remove( 0 );
		DEFAULT_TREE.getViewport().revalidate();
		DEFAULT_TREE.getViewport().add( classTree );
		DEFAULT_TREE.getViewport().revalidate();
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	// ======================================== List ============================================ //
	private static JList<String> createUsedMemberList( Methods methods ) {
		List<Members> memberList = methods.getMemberList();
		DefaultListModel<String> dlm = new DefaultListModel<>();
		if ( !memberList.isEmpty() )
			dlm.addElement( "Use: " );
		else
			dlm.addElement( "Not use any member." );

		for( Members members : memberList ) {
			dlm.addElement( members.getName() );
		}
		JList<String> jList = new JList<>( dlm );
		Font font = jList.getFont();
		jList.setFont( new Font( font.getName(), font.getStyle(), 15 ) );
		jList.setMinimumSize(
				new Dimension( (int) ( DEFAULT_SIZE_X * 0.4 ), (int) ( DEFAULT_SIZE_Y * 0.3 ) ) );
		return jList;
	}

	// ======================================== Table =========================================== //
	private static JScrollPane createMemberRefTable( Members members ) {
		StringBuilder strBuilder = new StringBuilder();
		ListIterator<Methods> listIterator = members.getRefMethodList().listIterator();

		while( listIterator.hasNext() ) {
			Methods methods = listIterator.next();
			strBuilder.append( methods.getName() + Delim.PARAMETER_OPEN );
			Map<String, Type> map = methods.getParams();
			int i = 0;
			for( String str : map.keySet() ) {
				strBuilder.append( map.get( str ).getTypeName() );
				if ( ++i == map.size() )
					break;
				strBuilder.append( ", " );
			}
			strBuilder.append( Delim.PARAMETER_CLOSE );
			if ( !listIterator.hasNext() )
				break;
			strBuilder.append( ", " );
		}

		String[] columnNames = { "Name", "methods" };
		String[][] data = { { members.getName(), strBuilder.toString() } };

		DefaultTableModel dm = new DefaultTableModel( data, columnNames );

		JTable memberRefTable = new JTable( dm );
		Font font = memberRefTable.getFont();
		memberRefTable.getColumnModel().getColumn( 0 ).setMaxWidth( 200 );
		memberRefTable.getColumnModel().getColumn( 0 ).setPreferredWidth( 120 );
		memberRefTable.getColumnModel().getColumn( 1 ).setWidth( 120 );
		memberRefTable.getColumnModel().getColumn( 1 ).setPreferredWidth( 120 );
		memberRefTable.setFont( new Font( font.getFontName(), font.getStyle(), 15 ) );
		JScrollPane jsp = new JScrollPane( memberRefTable );
		jsp.getViewport().setBackground( Color.WHITE );
		return jsp;
	}

	// ======================================= Control ========================================== //
	//Method Viewer
	private static JScrollPane createMethodContentText(Methods methods) {
		String methodString= ( (PMethod) methods ).getContents();
		JTextArea methodContentText = new JTextArea(methodString);
		Font font = methodContentText.getFont();
		MyFrame myFrame = (new GUISystem()).new MyFrame();
		
		methodContentText.setFont( new Font(font.getFontName(), font.getStyle(), 15) );
		try {
			System.out.println(
					methodContentText.getDocument().getText(0, methodContentText.getDocument().getLength()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JScrollPane jsp = new JScrollPane(methodContentText);
		jsp.getViewport().setBackground(Color.WHITE);
		return jsp;
		
	}
	// ========================================== ±Ë¡æ»∆ ===============================
	public class MyFrame extends JFrame {
		private JButton jb1;
		private JButton jb2;
		private JButton jb3;
		String methodString;
		void setMethodString(String str) {
			this.methodString = str;
			PMethod.contents = methodString;
		}

		public MyFrame() {
			this.setSize(1200, 800);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("Save File?");
			JPanel panel = new JPanel();
			JPanel panel0 = new JPanel();
			
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			makeLabel(panel, "Do you want to save file?");
			makePanel(panel0);
			add(panel);
			pack();
			setVisible(true);
			
			panel0.setLayout(new FlowLayout(FlowLayout.CENTER));
			createButton1(panel0, jb1);
			createButton2(panel0, jb2);
			createButton3(panel0, jb3);
			panel0.add(new JButton("Don't Save"));
			panel0.add(new JButton("Cancel"));
			
			jb1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setMethodString(methodContentText.getText());
					}
				});
			jb2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					}
				});
			jb3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				
			});
			this.add(panel);
			this.setVisible(true);
		}

		private void createButton3(JPanel panel, String buttonname) {
			panel.add(new JButton("Cancel"));
			// I want to name this button 'jb3'
		}

		private void createButton2(JPanel panel, String buttonname) {
			panel.add(new JButton("Don't Save"));
			// I want to name this button 'jb2'
		}

		private void createButton1(JPanel panel, String buttonname) {
			panel.add(new JButton("Save"));
			// I want to name this button 'jb1'
			
		}

		private void makePanel(JPanel panel) {
			panel.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(panel);
			
		}

		private void makeLabel(JPanel panel, String string) {
			JLabel label = new JLabel(string);
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(label);
			
		}

	}
	
	// ================================ ±Ë¡æ»∆(kim jong hun ) =================================
		
	//Method Viewer
	
	private static void rightPartControllor( Component comp ) {
		mainPanel.remove( 3 );

		GridBagConstraints gridBagCstr = new GridBagConstraints();
		gridBagCstr.gridx = 1;
		gridBagCstr.gridy = 1;
		gridBagCstr.gridwidth = 1;
		gridBagCstr.gridheight = 2;
		gridBagCstr.weightx = 0.6d;
		gridBagCstr.weighty = 0.9d;
		gridBagCstr.fill = GridBagConstraints.BOTH;
		if ( comp == null ) {
			mainPanel.add( initLabel(), gridBagCstr );
		} else {
			mainPanel.add( comp, gridBagCstr );
		}
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public Component jb3(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Component jb2(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void infoPartControllor( Component comp ) {
		DEFAULT_INFO.getViewport().remove( 0 );
		DEFAULT_INFO.getViewport().revalidate();

		if ( comp == null ) {
			DEFAULT_INFO.getViewport().add( initLabel() );
		} else {
			DEFAULT_INFO.getViewport().add( comp );
		}
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private static void treePartController( Component comp ) {
		DEFAULT_TREE.getViewport().remove( 0 );
		DEFAULT_TREE.getViewport().revalidate();

		if ( comp == null ) {
			DEFAULT_TREE.getViewport().add( initLabel() );
		} else {
			DEFAULT_TREE.getViewport().add( comp );
		}
		mainPanel.revalidate();
		mainPanel.repaint();
	}
}