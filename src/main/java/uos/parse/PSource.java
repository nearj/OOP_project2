package uos.parse;

import java.util.Scanner;

import uos.Accessor_Modifier;
import uos.Delim;
import uos.Type;

public final class PSource implements Parse {
	
	private String contents;
	private String fileName;
	
	public static PSource newInstance() {
		return new PSource();
	}
	
	enum MethodType {
		Constructor, Method 
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public void setContents(String contents) {
		this.contents = contents;
		
		Scanner scn = new Scanner(contents);
		scn.useDelimiter(Delim.CLASS);
		
		
		while( scn.hasNext() ) {
			String totalContent = scn.next();
			if( !totalContent.contains(Delim.INITIAL_CLOSE) ) break;
			// If scanned lines do not have content, break.
			
			String className = totalContent.substring( 
					totalContent.lastIndexOf(Delim.CLASS) + 1,
					totalContent.indexOf(Delim.INITIAL_OPEN) - 1 ).trim();
			PClass pClass = PClass.newInstance();
			pClass.setName(className);
			Delim.setClassName(className);
			// Set name of class and use the name as Delimiter.
			
			String initContent = 
					totalContent.substring( totalContent.indexOf(Delim.INITIAL_OPEN) + 1,
					totalContent.indexOf(Delim.INITIAL_CLOSE) - 1 ).
					trim();
			pClass.setContents(initContent);
			initParser(initContent.replace(System.lineSeparator(), ""));
			// Parsing class.
			
			String methodContents =
					totalContent.substring( totalContent.indexOf(Delim.INITIAL_CLOSE) + 1 ).
					trim();
			methodParser(methodContents);
			// Parsing method.
		}
		scn.close();
	}

	@Override
	public String getContents() {
		return contents;
	}

	private void initParser( String initContent ) {
		
		// < Init: set scanner and accessor modifier >
		Scanner initScn = new Scanner(initContent);
		initScn.useDelimiter( Delim.COLON );
		Accessor_Modifier acModifier = null;
		if( initScn.hasNext() ) {
			String initType = 
					initScn.next().replace( System.lineSeparator(), Delim.BLANK ).trim();
			acModifier = Accessor_Modifier.set(initType);
		}
		// < /Init >

		// < Scan initialization >
		while( initScn.hasNext() ) {

			// < Main-init: set scanner  >
			String contents = 
					initScn.next().replace( System.lineSeparator(), Delim.BLANK );
			Scanner lineScn = new Scanner( contents );
			lineScn.useDelimiter(Delim.LINE_END);
			// < /Initialization >
			
			// < Scan each line >
			while( lineScn.hasNext() ) {
				String lineContents = lineScn.next().trim();
				if( !lineScn.hasNext() ) {
					if( !lineContents.equals(Delim.BLANK) )
						acModifier = Accessor_Modifier.set(lineContents);
					break;
				}
				// If there is no more line at next scan, there will access modifier or
				// nothing. Therefore if there is line set access modifier, if not just
				// break;
					
				switch( acModifier ) {
					
					// < Set method or constructor name and return type >
					case PUBLIC: {
						if( lineScn.hasNext() ) {
							
							Classes classes = 
									Classes.classList.get( Classes.classList.size() - 1 );
							PMethod pMethod = PMethod.newInstance();
							pMethod.setRefClass(classes);
							
							String methodInfo = lineContents.substring(
									0, lineContents.indexOf(Delim.PARAMETER_OPEN) - 1 );
							
							if( methodInfo.equals( classes.getName() ) ||
									methodInfo.equals( "~" + classes.getName() ) ) {
								// TODO: Constructor stub.
							} else {
								pMethod.setReturnType(
										Type.set(
												methodInfo.substring(
														0, methodInfo.indexOf(" ") - 1 ) ) );
								pMethod.setName(
										methodInfo.substring(
												methodInfo.indexOf(" ") ) );
							}
							classes.setMethod(pMethod);

						}
					} break;

					case PRIVATE: {
						while( lineScn.hasNext() ) {
							// TODO: Parsed Member stub.
						}

					}
				}
			}
			lineScn.close();
		}
		initScn.close();
	}

	private void methodParser( String methodContents ) {
		// < Init: set scanner with '{', '}' >
		Scanner methodScn = new Scanner(methodContents);
		methodScn.useDelimiter( "[" + Delim.CLAUSE_OPEN + Delim.CLUASE_CLOSE + "]+" );
		// < /Init >
		
		while( methodScn.hasNext() ) {
			String infoContent = methodScn.next();
			Classes classes = Classes.classList.get( Classes.classList.size() - 1 );
			PMethod pMethod = null;
			MethodType methodType = null;
			
			// < Setting method or constructor return type >
			Scanner infoScn = new Scanner(infoContent);
			infoScn.useDelimiter( classes.getName() + Delim.ACCESSOR );
			String infoContent_Prev = null;
			
			if( infoScn.hasNext() ) {
				infoContent_Prev = infoScn.next().trim();
				if( infoContent_Prev.equals("") ) {
					methodType = MethodType.Constructor;
					// TODO: Constructor stub.
					;
				}
				else {
					methodType = MethodType.Method;
				}
			}
			// < /Setting method or constructor return type >
			
			// < Setting name and parameter >
			if( infoScn.hasNext() ) {
				String infoContent_Post = infoScn.next();
				String paramContent = infoContent_Post.
						substring( infoContent_Post.indexOf(Delim.PARAMETER_OPEN) + 1,
								infoContent_Post.indexOf(Delim.PARAMETER_CLOSE) - 1 ).trim();
				String name = infoContent_Post.
						substring( 0, infoContent_Post.indexOf(Delim.PARAMETER_OPEN) - 1 ).trim();
				pMethod = (PMethod) classes.getMethod(name);

				switch( methodType ) {
					case Constructor:
						// TODO: Constructor stub.
						break;
					case Method: {
						Scanner paramScn = new Scanner(paramContent);
						
						String paramElem = paramScn.next();
						if( paramContent.contains( Delim.COMMA ) ) {
							paramScn.useDelimiter( Delim.COMMA );
							while( paramScn.hasNext() ) {
								String paramName = 
										paramElem.substring( 0, paramElem.indexOf(Delim.SPACE) - 1 );
								Type paramType = Type.set( paramElem.substring(
										paramElem.indexOf(Delim.SPACE) + 1).trim() );
								pMethod.setParam(paramType, paramName);
							}
							paramScn.close();
							
						} else if( !paramElem.trim().equals("") ) {
							String paramName = 
									paramElem.substring( 0, paramElem.indexOf(" ") - 1 );
							Type paramType = Type.set( paramElem.substring(
									paramElem.indexOf(" ") + 1).trim() );
							pMethod.setParam(paramType, paramName);
						}

						pMethod.setName(name);
						pMethod.setReturnType( Type.set( infoContent_Prev ) );
					} break;
				}
			}
			// < /Setting name and parameter >
			infoScn.close();
			
			// < Content >
			
			if( methodScn.hasNext() ) {
				String contents = methodScn.next();
				switch( methodType ) {
					case Constructor:
						// TODO: Constructor stub
						break;
					case Method: {
						pMethod.setContents(contents);
					}
				}
			}
			else break;
			// < Content >
		}
		methodScn.close();
	}
}
