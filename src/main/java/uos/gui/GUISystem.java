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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

import uos.Delim;
import uos.Type;
import uos.file.FileSystem;
import uos.parse.Classes;
import uos.parse.Members;
import uos.parse.Methods;
import uos.parse.PMethod;
import uos.parse.PSource;

public class GUISystem implements Runnable {
	
	private static final int DEFAULT_SIZE_X = 600;
	private static final int DEFAULT_SIZE_Y = 800;
	private static final Dimension DEFAULT_PREFERRED_DIMENTION_RIGHT = new Dimension(
			(int) ( DEFAULT_SIZE_X * 0.6 ), (int) ( DEFAULT_SIZE_Y * 0.9 ) );
	private static final Dimension DEFAULT_PREFERRED_DIMENTION_INFO = new Dimension(
			(int) ( DEFAULT_SIZE_X * 0.4 ), (int) ( DEFAULT_SIZE_Y * 0.3 ) );
	private static final Dimension DEFAULT_PREFERRED_DIMENTION_TREE = new Dimension(
			(int) ( DEFAULT_SIZE_X * 0.4 ), (int) ( DEFAULT_SIZE_Y * 0.6 ) );
	private static PSource sourceOrigin;
	private static PSource sourceCopied;
	
	private static JFrame mainFrame = new JFrame();
	private static JPanel mainPanel = new JPanel( new GridBagLayout() );
	private static JMenuBar jmb = new JMenuBar();
	private static final JScrollPane DEFAULT_TREE = new JScrollPane();
	private static final JScrollPane DEFAULT_INFO = new JScrollPane();
	private static boolean isChanged = false;
	
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
		DEFAULT_TREE.setPreferredSize( DEFAULT_PREFERRED_DIMENTION_TREE );
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
		DEFAULT_INFO.setPreferredSize( DEFAULT_PREFERRED_DIMENTION_INFO );
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
					if ( sourceOrigin != null )
						sourceOrigin.clearClassList();
					sourceOrigin = FileSystem.read( jFileChooser.getSelectedFile() );
					createClassTree();
				} else if ( retVal == JFileChooser.CANCEL_OPTION )
					;
			}
		} );
		
		jmiSAVE.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent e ) {
				if ( isChanged ) {
					JFrame saveFrame = new JFrame( "저장" );
					JLabel jLabel = new JLabel( "내용이 변경되었습니다. 변경된 내용으로 저장하시겠습니까?" );
					JButton denied = new JButton( "아니요" );
					JButton accepted = new JButton( "예" );
					
					denied.addActionListener( new ActionListener() {
						
						@Override
						public void actionPerformed( ActionEvent e ) {
							saveFrame.setVisible( false );
							save( sourceCopied );
						}
					} );
					accepted.addActionListener( new ActionListener() {
						
						@Override
						public void actionPerformed( ActionEvent e ) {
							saveFrame.setVisible( false );
							save( sourceOrigin );
						}
					} );
					
					JPanel jPanel = new JPanel( new FlowLayout() );
					JPanel jPanel1 = new JPanel( new FlowLayout() );
					JPanel jPanel2 = new JPanel( new FlowLayout() );
					jPanel1.setVisible( true );
					jPanel2.setVisible( true );
					jPanel2.setVisible( true );
					jPanel1.add( jLabel );
					jPanel2.add( denied );
					jPanel2.add( accepted );
					jPanel.add( jPanel1 );
					jPanel.add( jPanel2 );
					saveFrame.add( jPanel );
					jPanel.setBackground( Color.WHITE );
					jPanel1.setBackground( Color.WHITE );
					jPanel2.setBackground( Color.WHITE );
					saveFrame.setSize( new Dimension( 375, 125 ) );
					saveFrame.setResizable( false );
					saveFrame.setVisible( true );
				} else
					save( sourceOrigin );

				
			}

		} );
		
		jmiCLOSE.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				treePartController( null );
				infoPartControllor( null );
				rightPartControllor( null );
				sourceOrigin.clearClassList();
				sourceOrigin = null;
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
		for ( Classes classes : sourceOrigin.getClassList() ) {
			DefaultMutableTreeNode classNode = new DefaultMutableTreeNode( classes ) {
				private static final long serialVersionUID = -1405861207465188691L;
				
				@Override
				public String toString() {
					return classes.getName();
				}
			};
			
			for ( Methods methods : classes.getMethodList() ) {
				DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode( methods ) {
					private static final long serialVersionUID = -6298724158229241038L;
					
					@Override
					public String toString() {
						StringBuilder strBuilder = new StringBuilder();
						Iterator< String > iterator = methods.getParams().keySet().iterator();
						while ( iterator.hasNext() ) {
							
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
			
			for ( Members members : classes.getMemberList() ) {
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
					rightPartControllor( createClassTable( (Classes) nodeObj.getUserObject() ) );
					
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
		
		for ( int i = 0; i < classTree.getRowCount(); ) {
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
	private static JList< String > createUsedMemberList( Methods methods ) {
		List< Members > memberList = methods.getMemberList();
		DefaultListModel< String > dlm = new DefaultListModel<>();
		if ( !memberList.isEmpty() )
			dlm.addElement( "Use: " );
		else
			dlm.addElement( "Not use any member." );
		
		for ( Members members : memberList ) {
			dlm.addElement( members.getName() );
		}
		JList< String > jList = new JList<>( dlm );
		Font font = jList.getFont();
		jList.setFont( new Font( font.getName(), font.getStyle(), 15 ) );
		jList.setFixedCellWidth( (int) ( mainFrame.getSize().getWidth() * 0.4 ) );
		return jList;
	}
	
	// ======================================== Table =========================================== //
	private static JScrollPane createClassTable( Classes classes ) {
		String[] columnNames = { "Name", "Type", "Access" };
		
		DefaultTableModel dm = new DefaultTableModel( columnNames, 0 );
		
		ListIterator< ? > listIterator = classes.getMethodList().listIterator();
		while ( listIterator.hasNext() ) {
			Methods methods = (Methods) listIterator.next();
			if ( methods == null )
				break;
			String[] data = new String[3];
			StringBuilder strBuilder = new StringBuilder();
			
			strBuilder.append( methods.getName() + Delim.PARAMETER_OPEN );
			Map< String, Type > map = methods.getParams();
			int i = 0;
			for ( String str : map.keySet() ) {
				strBuilder.append( map.get( str ).getTypeName() );
				if ( ++i == map.size() )
					break;
				strBuilder.append( ", " );
			}
			strBuilder.append( Delim.PARAMETER_CLOSE );
			data[0] = strBuilder.toString();
			if ( methods.getReturnType() == null )
				data[1] = "void";
			else
				data[1] = methods.getReturnType().getTypeName();
			data[2] = methods.getAccessModifier().toString();
			dm.addRow( data );
		}
		
		listIterator = classes.getMemberList().listIterator();
		while ( listIterator.hasNext() ) {
			Members members = (Members) listIterator.next();
			if ( members == null )
				break;
			String[] data = new String[3];
			data[0] = members.getName();
			data[1] = members.getReturnType().getTypeName();
			data[2] = members.getAccessModifier().toString();
			dm.addRow( data );
		}
		JTable memberRefTable = new JTable( dm );
		Font font = memberRefTable.getFont();
		memberRefTable.getColumnModel().getColumn( 0 ).setMaxWidth( 200 );
		memberRefTable.getColumnModel().getColumn( 0 ).setPreferredWidth( 120 );
		memberRefTable.getColumnModel().getColumn( 1 ).setWidth( 120 );
		memberRefTable.getColumnModel().getColumn( 1 ).setPreferredWidth( 120 );
		memberRefTable.setFont( new Font( font.getFontName(), font.getStyle(), 15 ) );
		JScrollPane jsp = new JScrollPane( memberRefTable );
		jsp.setPreferredSize( DEFAULT_PREFERRED_DIMENTION_RIGHT );
		jsp.getViewport().setBackground( Color.WHITE );
		return jsp;
		
	}
	
	private static JScrollPane createMemberRefTable( Members members ) {
		StringBuilder strBuilder = new StringBuilder();
		ListIterator< Methods > listIterator = members.getRefMethodList().listIterator();
		
		while ( listIterator.hasNext() ) {
			Methods methods = listIterator.next();
			strBuilder.append( methods.getName() + Delim.PARAMETER_OPEN );
			Map< String, Type > map = methods.getParams();
			int i = 0;
			for ( String str : map.keySet() ) {
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
		jsp.setPreferredSize( DEFAULT_PREFERRED_DIMENTION_RIGHT );
		jsp.getViewport().setBackground( Color.WHITE );
		return jsp;
	}
	
	private static JScrollPane createMethodContentText( Methods methods ) {
		String methodString = ( (PMethod) methods ).getContents();
		JTextArea methodContentText = new JTextArea( methodString );
		Font font = methodContentText.getFont();
		
		methodContentText.setFont( new Font( font.getFontName(), font.getStyle(), 15 ) );
		methodContentText.getDocument().addDocumentListener( new DocumentListener() {
			
			@Override
			public void removeUpdate( DocumentEvent e ) {
				changed( e );
			}
			
			@Override
			public void insertUpdate( DocumentEvent e ) {
				changed( e );
			}
			
			@Override
			public void changedUpdate( DocumentEvent e ) {
			}
			
			private void changed( DocumentEvent e ) {
				isChanged = true;
				sourceCopied = PSource.newInstance( sourceOrigin );
				try {
					( (PMethod) methods ).setContents(
							e.getDocument().getText( 0, e.getDocument().getLength() ) );
					
					for ( Classes classes : sourceOrigin.getClassList() ) {
						for ( Methods method_Dst : classes.getMethodList() ) {
							
							if ( method_Dst.equals( methods ) ) {
								Classes classes_Src = classes;
								classes_Src.replaceMethod( methods, method_Dst );
								sourceOrigin.replaceClass( classes_Src, classes );
							}
						}
					}
				} catch ( BadLocationException e1 ) {
					e1.printStackTrace();
				}
			}
		} );
		
		JScrollPane jsp = new JScrollPane( methodContentText );
		jsp.setPreferredSize( DEFAULT_PREFERRED_DIMENTION_RIGHT );
		jsp.getViewport().setBackground( Color.WHITE );
		return jsp;
		
	}
	
	// ======================================= Control ========================================== //
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
	
	private static void save( PSource pSource ) {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setDialogTitle( "Save File" );
		jFileChooser.setCurrentDirectory( new File( FileSystem.DEFAULT_PATH ) );
		jFileChooser.setSelectedFile( new File( sourceOrigin.getFileName() ) );
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
}