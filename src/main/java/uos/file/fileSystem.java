package uos.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.file.Paths;
import java.util.Scanner;

import uos.Accessor_Modifier;
import uos.Delim;
import uos.Type;
import uos.parse.PClass;
import uos.parse.PMethod;

/**
 * This class implements to open files and to parse data.
 * 
 * Abbreviation: 
 * @author 2016920054_JUHAYONG
 *
 */
public class fileSystem {

	final static class Ext {
		final static String CPP = "cpp"; 
	}
	
	private static int numberOfClasses = 0;    // To tracking the number of classes.
	private static final InputStreamReader inStrmReader =
			new InputStreamReader( System.in );
	
	private static final String DEFAULT_PATH = "./";
	private static final String DEFAULT_FILENAME = "QUEUE";
	private static final String DEFAULT_EXT = Ext.CPP;
	// Default setting.
	
	private String fileName = DEFAULT_FILENAME;
	private String fullName = fileName + "." + DEFAULT_EXT;
	private String fullPath = DEFAULT_PATH + fullName; 
	// Path setting. 
	{
	
	}
	
	/**
	 * Read file with user prompted file and parse each contents.
	 * 
	 * Information: info
	 * Scanner: scn 
	 * 
	 * @param File to read and parse.
	 */
	public void reader( File file ) {
		
		try( BufferedReader br = 
				new BufferedReader( new FileReader( file ) )
				) {
			
			Scanner scn = new Scanner(br);
			scn.useDelimiter(Delim.CLASS);
			
			
			while( scn.hasNext() ) {
				String totalContent = scn.next();
				if( !totalContent.contains("};") ) break;
				// If scanned lines do not have content, break.
				
				numberOfClasses++;
				Delim.setClassName( 
						totalContent.substring( 
								totalContent.lastIndexOf(Delim.CLASS) + 1,
								totalContent.indexOf(Delim.INITIAL_OPEN) - 1 ).trim() );
				// Use name of class as Delimiter.
				// TODO: matching number of class and current name.
				
				String initContent = 
						totalContent.substring( totalContent.indexOf(Delim.INITIAL_OPEN) + 1,
						totalContent.indexOf(Delim.INITIAL_CLOSE) - 1 ).
						trim().replace(System.lineSeparator(), "");
				initParser(initContent);
				// Parsing class.
				
				String methodContents =
						totalContent.substring( totalContent.indexOf(Delim.INITIAL_CLOSE) + 1 ).
						trim().replace(System.lineSeparator(), "");
				classParser(methodContents);
				//Parsing method.
			}
			scn.close();
			
		} catch ( FileNotFoundException e ) {
			System.out.println( "Error: There is no such file \"" +
					fullPath.toString() + "\"" );
		} catch ( IOException e ) {
			System.out.println( "Error: " + e );
		}
	}
	
	private void initParser( String initContent ) {
		PClass pClass = PClass.newInstance();
		pClass.setName(Delim.getClassName( numberOfClasses ) );
		// Setting class and name of class
		
		Scanner initScn = new Scanner(initContent);
		initScn.useDelimiter(":");
		Accessor_Modifier acModifier = null;
		// Initializtion.
		
		while( initScn.hasNext() ) {
			String typeOfContents = initScn.next();
			if( acModifier == null ) {
				if( typeOfContents.equals(Accessor_Modifier.PUBLIC.toString()) )
					acModifier = Accessor_Modifier.PUBLIC;
				else if( typeOfContents.equals(Accessor_Modifier.PRIVATE.toString()) )
					acModifier = Accessor_Modifier.PRIVATE;
				break;
			}
			
			Scanner lineScn = new Scanner(typeOfContents);
			lineScn.useDelimiter(Delim.LINE_END);
			String content = lineScn.next().trim();
			switch( acModifier ) {
				case PUBLIC: {
					while( lineScn.hasNext() ) {
						PMethod pMethod = PMethod.newInstance();
						
						String methodInfo = content.substring(
								0, content.indexOf(Delim.PARAMETER_OPEN) - 1 );
						if( methodInfo.equals( pClass.getName() ) ) {
							// TODO: Constructor stub.
						} else if( methodInfo.equals( "~" + pClass.getName() ) ) {
							// TODO: DeConstructor stub.
						} else {
						
							pMethod.setReturnType(
									Type.set(
											methodInfo.substring( 0, methodInfo.indexOf(" ") - 1 ) ) );
							pMethod.setName(
									methodInfo.substring(
											methodInfo.indexOf(" ") ) );
						}
						pClass.setMethod(pMethod);
						
					}
				} break;
				
				case PRIVATE: {
					while( lineScn.hasNext() ) {
						// TODO: Parsed Member stub.
					}
					
				}
			}
			lineScn.close();
		}
		initScn.close();
	}
	
	enum MethodType {
		Constructor, Method 
	}
	
	private void classParser( String classContents ) {
		Scanner classScn = new Scanner(classContents);
		classScn.useDelimiter( "[" + Delim.CLAUSE_OPEN + Delim.CLUASE_CLOSE + "]+" );
		while( classScn.hasNext() ) {
			String infoContent = classScn.next();
			PMethod pMethod = null;
			MethodType methodType = null;
			
			// < Setting method or constructor return type >
			Scanner infoScn = new Scanner(infoContent);
			infoScn.useDelimiter( Delim.getClassName(numberOfClasses) + Delim.ACCESSOR );
			if( infoScn.hasNext() ) {
				String infoContent_Prev = infoScn.next().trim();
				if( infoContent_Prev.equals("") ) {
					methodType = MethodType.Constructor;
					// TODO: Constructor stub.
					;
				}
				else {
					methodType = MethodType.Method;
					pMethod = PMethod.newInstance();
					pMethod.setReturnType(Type.set(infoContent_Prev));
				}
			}
			// < /Setting method or constructor return type >
			
			// < Setting name and parameter >
			if( infoScn.hasNext() ) {
				String infoContent_Post = infoScn.next();
				String paramContent = infoContent_Post.
						substring( infoContent_Post.indexOf(Delim.PARAMETER_OPEN) + 1,
								infoContent_Post.indexOf(Delim.PARAMETER_CLOSE) - 1 ).trim();
				switch( methodType ) {
					case Constructor:
						// TODO: Constructor stub.
						break;
					case Method:
						String name = infoContent_Post.
						substring( 0, infoContent_Post.indexOf(Delim.PARAMETER_OPEN) - 1 ).trim();
						pMethod.setName(name);
						Scanner paramScn = new Scanner(paramContent);
						String paramElem = paramScn.next();
						if( paramContent.contains( Delim.SEMI_COLON ) ) {
							paramScn.useDelimiter( Delim.SEMI_COLON );
							while( paramScn.hasNext() ) {
								String paramName = 
										paramElem.substring( 0, paramElem.indexOf(" ") - 1 );
								Type paramType = Type.set( paramElem.substring(
										paramElem.indexOf(" ") + 1).trim() );
								pMethod.setParam(paramType, paramName);
							}
							paramScn.close();
						} else if( !paramElem.trim().equals("") ) {
							String paramName = 
									paramElem.substring( 0, paramElem.indexOf(" ") - 1 );
							Type paramType = Type.set( paramElem.substring(
									paramElem.indexOf(" ") + 1).trim() );
							pMethod.setParam(paramType, paramName);
						} else {
							pMethod.setParam(null, null);
						}
				}
			}
			// < /Setting name and parameter >
			infoScn.close();
			
			// < Content >
			String contents = null;
			if( classScn.hasNext() ) {
				contents = classScn.next();
				switch( methodType ) {
					case Constructor:
						// TODO: Constructor stub
						break;
					case Method: {
						pMethod.setContents(contents);
						// TODO: Member stub
					}
						
				}

			}
			else break;
			// < Content >

		}
		
		classScn.close();
	}
	
	/**
	 * Prompt file to read.
	 * 
	 * @return File which is selected by interface.
	 */
	public File open() {
		System.out.print( "Select File Name: " );
		Scanner scn = new Scanner(inStrmReader);
		fileName = scn.nextLine();
		scn.close();
		fullPath = DEFAULT_PATH + fileName + "." + Ext.CPP;
		return new File(fullPath);
	}
	
	/**
	 * Close program.
	 */
	public void close() {
		System.exit(0);
	}
}
