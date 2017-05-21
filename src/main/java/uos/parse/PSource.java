package uos.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import uos.Accessor_Modifier;
import uos.Delim;
import uos.Type;

public final class PSource implements Parse {

	private String fileName;
	private List<Classes> classList = new ArrayList<>();
	private Object infoContent_Prev;

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
	
	// < Class list >
	
	public void setClass( Classes classes ) {
		classList.add(classes);
	}

	public Classes getKlass(int index) {
		return classList.get(index);
	}

	public void clearClassList() {
		classList.clear();
	}
	
	public List<Classes> getClassList() {
		return classList;
	}

    // < /Class List >
		
	@Override
	public void setContents(String contents) {
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
			classList.add(pClass);
			Delim.setClassName(className);
			// Set name of class and use the name as Delimiter.
			
			String initContent = 
					totalContent.substring( totalContent.indexOf(Delim.INITIAL_OPEN) + 1,
					totalContent.indexOf(Delim.INITIAL_CLOSE) - 1 ).
					trim();
			pClass.setContents(initContent);
			initParser(initContent.replace(Delim.LINE_FEED, ""));
			// Parsing class.
			
			String methodContents =
					totalContent.substring( totalContent.indexOf(Delim.INITIAL_CLOSE) + 2 ).
					trim();
			methodParser(methodContents);
			// Parsing method.
		}
		scn.close();
	}

	@Override
	public String getContents() {
		StringBuilder strBuilder = new StringBuilder();
		
		// < File name >
		strBuilder.append("File Name: " + fileName + 
				Delim.LINE_FEED + Delim.LINE_FEED);
		// < /File name >
		
		// < Class-list >
		strBuilder.append( "Classes: " + Delim.LINE_FEED );
		for( Classes classes : classList ) {
			strBuilder.append( Delim.TAB + classes.getName() );
		}
		strBuilder.append( Delim.LINE_FEED + Delim.LINE_FEED );
		// < /Class-list >
		
		// < Class specification >
		for( Classes classes : classList ) {
			strBuilder.append( Delim.TAG_OPEN + "class " + classes.getName() + 
					Delim.TAG_CLOSE + Delim.LINE_FEED );
			
			// < Method-list >
			strBuilder.append("methods:" + Delim.LINE_FEED );
			for( Methods methods : classes.getMethodList() ) {
				strBuilder.append( Delim.TAB + methods.getReturnType().name() + Delim.SPACE +
						methods.getName() + Delim.PARAMETER_OPEN );
				Iterator<String> iterator = methods.getParams().keySet().iterator();
				while( iterator.hasNext() ) {
					String paramName = iterator.next();
					
					strBuilder.append( Delim.TAB + 
							methods.getParamType( paramName ).getTypeName() +
							Delim.SPACE + paramName ) ;
					
					if( !iterator.hasNext() ) break;
					strBuilder.append( "," );
				}
				strBuilder.append( Delim.PARAMETER_CLOSE + Delim.LINE_FEED );
			}
			strBuilder.append( Delim.LINE_FEED );
			// < Method-list >
			
			// < Member-list >
			for( Members members : classes.getMemberList() ) {
				strBuilder.append( members.getType().getTypeName() + 
						Delim.SPACE + members.getName() );
				if( members.isArray() )
					strBuilder.append(Delim.ARRAY_OPEN + Delim.ARRAY_CLOSE);
				strBuilder.append( Delim.LINE_FEED );
			}
			// < /Member-list >
			
			// < Method specification >
			for( Methods methods : classes.getMethodList() ) {
				strBuilder.append( Delim.TAB + Delim.TAG_OPEN + 
						"method " + methods.getName() + Delim.TAG_CLOSE +
						Delim.LINE_FEED ); 
				strBuilder.append( Delim.TAB + "field:" + Delim.LINE_FEED );
				for( Members members : methods.getMemberList() ) {
					strBuilder.append( Delim.TAB + Delim.TAB + members.getName() +
							Delim.LINE_FEED );
				}
				strBuilder.append( Delim.TAB + Delim.TAG_OPEN +
						"/method " + methods.getName() + Delim.TAG_CLOSE +
						Delim.LINE_FEED );
			}
			// < /Method specification >
			strBuilder.append( Delim.TAG_OPEN + "/class " + Delim.TAG_CLOSE +
					Delim.LINE_FEED );
		}
		// < Class specification >
		return strBuilder.toString();
	}

	private void initParser( String initContent ) {
		
		// < Init: set scanner and accessor modifier >
		Scanner initScn = new Scanner(initContent);
		initScn.useDelimiter( Delim.COLON );
		Accessor_Modifier acModifier = null;
		if( initScn.hasNext() ) {
			String initType = 
					initScn.next().replace( Delim.LINE_FEED, Delim.BLANK ).trim();
			acModifier = Accessor_Modifier.set(initType);
		}
		// < /Init >

		// < Scan initialization >
		while( initScn.hasNext() ) {

			// < Main-init: set scanner  >
			String contents = 
					initScn.next().replace( Delim.LINE_FEED, Delim.BLANK );
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
									classList.get( classList.size() - 1 );
							PMethod pMethod = PMethod.newInstance();
							pMethod.setRefClass(classes);
							
							String methodInfo = lineContents.substring(
									0, lineContents.indexOf(Delim.PARAMETER_OPEN) );

							if( methodInfo.equals( classes.getName() ) ||
									methodInfo.equals( "~" + classes.getName() ) ) {
								
								// TODO: Constructor stub.
							} else {
								pMethod.setReturnType(
										Type.set(
												methodInfo.substring(
														0, methodInfo.indexOf(" ") ).trim() ) );
								pMethod.setName(
										methodInfo.substring(
												methodInfo.indexOf(" ") ).trim() );
							}
							classes.setMethod(pMethod);

						}
					} break;

					case PRIVATE: {
						// TODO: Private stub.
						while( lineScn.hasNext() ) {
							// TODO: Parsed Member stub.
							break;
						}

					}
				}
			}
			lineScn.close();
		}
		initScn.close();
	}

	private String trimLeading( String string ) {
        int len = string.length();
        int st = 0;
        char[] val = string.toCharArray();    /* avoid getfield opcode */

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        return ((st > 0) || (len < string.length())) ? string.substring(st, len) 
        		: string;
	}
	
	private void methodParser( String methodContents ) {
		// < Init: set scanner with '{', '}' >

		
		Scanner methodScn = new Scanner(methodContents.
				replace( Delim.CLAUSE_OPEN, Delim.CLAUSE_OPEN + Delim.SPACE));
		methodScn.useDelimiter( 
				"\\" + Delim.CLAUSE_OPEN + "?+" + "\\" + Delim.CLUASE_CLOSE ); 
		// regex: \\{?\\}
		// / '\\{' /g : Firstly, use clause open delimiter( curly braket )
		// / '?' /g : Find '{' greedly once and not at all
		// / '\\}' /g : following clause close delimiter.
		// < /Init >
		
		// < Contents parse >
		String contents = methodScn.next() + "}";
		
		while( methodScn.hasNext() ) {
			String tempContents = methodScn.next() + "}";
			
			if( trimLeading(tempContents).indexOf("{") <= 0 ) {
				contents = contents + tempContents;
				continue;
			} else {
				methodParseHelper(contents);
				contents = tempContents;
			}
		}
		
		if( !contents.isEmpty() ) methodParseHelper(contents);
		
		methodScn.close();
	}
	
	private void methodParseHelper( String contents ) {

		Classes classes = classList.get( classList.size() - 1 );
		PMethod pMethod = null;
		MethodType methodType = null;
		String infoContent = contents.substring( 0, contents.indexOf( Delim.CLAUSE_OPEN ) ).trim();
		String infoContent_Prev = infoContent.substring(
				0, infoContent.indexOf( classes.getName() + Delim.ACCESSOR ) ).trim();
		String infoContent_Post =
				infoContent.substring( infoContent.indexOf( classes.getName() + Delim.ACCESSOR )
						+ ( classes.getName() + Delim.ACCESSOR).length() );
		
		// < Setting method or constructor return type >				
		if( infoContent_Prev.equals("") ) methodType = MethodType.Constructor;
		else methodType = MethodType.Method;
		// < /Setting method or constructor return type >
		// < Setting name and parameter >
		String methodName = infoContent_Post.
				substring( 0, infoContent_Post.indexOf(Delim.PARAMETER_OPEN) ).trim();
		String paramContent = infoContent_Post.
				substring( infoContent_Post.indexOf(Delim.PARAMETER_OPEN) + 1,
						infoContent_Post.indexOf(Delim.PARAMETER_CLOSE) ).trim();

		pMethod = (PMethod) classes.getMethod(methodName);

		switch( methodType ) {
			case Constructor:
				// TODO: Constructor stub.
				break;
			case Method: {
				if( paramContent.equals("") ) {
					pMethod.setParam(Type.NULL, "");
					break;
				}


				if( paramContent.contains( Delim.COMMA ) ) {
					Scanner paramScn = new Scanner(paramContent);
					paramScn.useDelimiter( Delim.COMMA );
					String paramElem = paramScn.next().trim();

					while( paramScn.hasNext() ) {
						String paramName = 
								paramElem.substring( 0, paramElem.indexOf(Delim.SPACE) - 1 );


						Type paramType = Type.set( paramElem.substring(
								paramElem.indexOf(Delim.SPACE) + 1).trim() );
						pMethod.setParam(paramType, paramName);
					}
					paramScn.close();

				} else if( !paramContent.trim().equals("") ) {

					String paramName = paramContent.substring(
							paramContent.indexOf(" ") + 1 );

					Type paramType = Type.set( paramContent.substring( 0, paramContent.indexOf(" ") ).trim() );
					pMethod.setParam(paramType, paramName);
				}

				pMethod.setName(methodName);
				pMethod.setReturnType( Type.set( infoContent_Prev ) );
			} break;
		}
		// < Set method name, return type, parameter information >
		
		// < set method contents >
		String methodClauses = contents.substring( contents.indexOf( Delim.CLAUSE_OPEN ) + 1, 
				contents.lastIndexOf( Delim.CLUASE_CLOSE ) - 1).trim();
		switch( methodType ) {
			case Constructor:
				// TODO: Constructor stub
				break;
			case Method: {
				pMethod.setContents( "  " + methodClauses );
			}
		}
		// < set method contents >
	}
}
