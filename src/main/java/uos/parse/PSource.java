package uos.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uos.AccessModifier;
import uos.Delim;
import uos.Type;
import uos.parse.Methods.MethodType;

/**
 * Parsing with requested source file(flankly speeking, just text contents)
 * class. Implements interface {@link Parse} and has class-list and file name.
 * 
 * @author 2016920054_JUHAYONG
 *
 */
public final class PSource implements Parse {

	//========================================= < member > =========================================

	private String fileName;
	private List<Classes> classList = new ArrayList<>(); // List of classes with
															// in source file

	private PSource() {
	} // To prevent instantiation.

	//========================================= < method > =========================================

	// Return instance of PSource with static factory method.
	public static PSource newInstance() {
		return new PSource();
	}

	// < File name >

	/**
	 * Setting name of file(format: ###(name).###(extension))
	 * 
	 * @param fileName
	 *            Name of file(format: ###(name).###(extenstion))
	 */
	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}

	/**
	 * Get name of current source file(format: ###(name).###(extenstion))
	 * 
	 * @return Name of file(format: ###(name).###(extenstion))
	 */
	public String getFileName() {
		return fileName;
	}

	// < /File name >
	// < Class list >

	/**
	 * Append {@link Classes} to class-list in the source.
	 * 
	 * @param classes
	 *            class to insert in class-list of source file.
	 */
	public void setClass( Classes classes ) {
		classList.add( classes );
	}

	/**
	 * Get {@link Classes} specified with index in class-list
	 * 
	 * @param index
	 *            index of class-list to get class
	 * @return specified class in class-list
	 */
	public Classes getKlass( int index ) {
		return classList.get( index );
	}

	/**
	 * Get class-list in source.
	 * 
	 * @return class-list in source.
	 */
	public List<Classes> getClassList() {
		return classList;
	}

	/**
	 * Clear class-list in source.
	 */
	public void clearClassList() {
		classList.clear();
	}

	@Override
	public String getContents() {
		StringBuilder strBuilder = new StringBuilder();

		// < Class specification >
		for( Classes classes : classList ) {
			PClass pClass = (PClass) classes;
			strBuilder.append( pClass.getField() + Delim.INITIAL_OPEN + Delim.LINE_FEED
					+ pClass.getContents() + Delim.LINE_FEED + Delim.INITIAL_CLOSE + Delim.LINE_FEED
					+ Delim.LINE_FEED );
			for( Methods methods : classes.getMethodList() ) {
				PMethod pMethod = (PMethod) methods;
				strBuilder.append( pMethod.getField() + Delim.CLAUSE_OPEN + Delim.LINE_FEED
						+ pMethod.getContents() + Delim.LINE_FEED + Delim.CLUASE_CLOSE );
			}
			if ( classList.indexOf( classes ) != classList.size() - 1 ) {
				strBuilder.append( Delim.LINE_FEED + Delim.LINE_FEED );
			}
		}
		// < Class specification >
		return strBuilder.toString();
	}

	// < /Class List >
	// < Contents >

	/**
	 * {@inheritDoc}
	 * <p>
	 * <p>
	 * Accept the complete source level contents which has multiple class in the
	 * source.<br>
	 * If format of contents is imcomplete, there could be unpredicted error.
	 */
	@Override
	public void setContents( String contents ) {

		Scanner scn = new Scanner( contents );
		scn.useDelimiter( Delim.CLASS );
		// Use scanner setting delimiter as "class" string with case sensitive.

		while( scn.hasNext() ) {
			String totalContent = scn.next();
			if ( !totalContent.contains( Delim.INITIAL_CLOSE ) )
				break;
			// If scanned lines do not have content, break.

			String className = totalContent.substring( 0,
					totalContent.indexOf( Delim.INITIAL_OPEN ) - 1 );
			// class name will be first first index of token, and before first
			// index of
			// Initialization.

			PClass pClass = PClass.newInstance();
			pClass.setPropField( "class " + className );
			pClass.setName( className.trim() );
			classList.add( pClass );
			// Set name of class and use the name as Delimiter.

			String initContent = totalContent.substring( totalContent.indexOf( Delim.INITIAL_OPEN ),
					totalContent.indexOf( Delim.INITIAL_CLOSE ) + 1 ).trim();
			pClass.setContents( toCleanClauses( initContent ) );
			initParser( toCleanClauses( initContent ).replace( Delim.LINE_FEED, Delim.BLANK ) );
			// Parsing initialization.

			String methodContents = totalContent
					.substring( totalContent.indexOf( Delim.INITIAL_CLOSE ) + 2 ).trim();
			methodParser( methodContents );
			// Parsing method, since initialization close is };
		}
		scn.close();
	}

	private void initParser( String initContent ) {

		// < Init: set scanner and accessor modifier >
		Scanner initScn = new Scanner( initContent );
		initScn.useDelimiter( Delim.COLON );
		AccessModifier acModifier = null;
		if ( initScn.hasNext() ) {
			String initType = initScn.next().replace( Delim.LINE_FEED, Delim.BLANK ).trim();
			acModifier = AccessModifier.set( initType );
		}
		// < /Init >

		// < Scan initialization >
		while( initScn.hasNext() ) {

			// < Main-init: set scanner >
			String contents = initScn.next().replace( Delim.LINE_FEED, Delim.BLANK )
					.replace( Delim.LINE_END, Delim.LINE_END + Delim.SPACE );
			// To make non empty line and interpreter like contents.
			Scanner lineScn = new Scanner( contents );
			lineScn.useDelimiter( Delim.LINE_END );
			// < /Initialization >

			// < Scan each line >
			while( lineScn.hasNext() ) {

				String lineContents = lineScn.next();
				if ( !lineScn.hasNext() ) {
					if ( !lineContents.trim().equals( Delim.BLANK ) )
						acModifier = AccessModifier.set( lineContents );
					break;
				}
				// If there is no more line at next scan, there will access modifier or
				// nothing. Therefore if there is line set access modifier, if not just break;

				boolean isMember;
				if ( lineContents.contains( Delim.PARAMETER_OPEN ) )
					isMember = false;
				else
					isMember = true;
				// verify line content is method or member.

				Classes classes = classList.get( classList.size() - 1 );
				if ( isMember ) { // If line content is member.
					PMember pMember = PMember.newInstance();
					pMember.setRefClass( classes );
					lineContents = lineContents.trim();

					Type retrunType = Type.set( lineContents
							.substring( 0, lineContents.indexOf( Delim.SPACE ) ).trim() );
					String methodName;
					if ( lineContents.contains( Delim.ARRAY_OPEN ) ) {
						methodName = lineContents.substring( lineContents.indexOf( Delim.SPACE ),
								lineContents.indexOf( Delim.ARRAY_OPEN ) ).trim();
						pMember.setMemberType( Members.MemberType.ARRAY );
					} else {
						methodName = lineContents.substring( lineContents.indexOf( Delim.SPACE ) )
								.trim();
						pMember.setMemberType( Members.MemberType.DEFAULT );
					}
					pMember.setReturnType( retrunType );
					pMember.setName( methodName );
					classes.setMember( pMember );

				} else { // If line content is method.
					PMethod pMethod = PMethod.newInstance();
					pMethod.setRefClass( classes );

					String methodInfo = lineContents
							.substring( 0, lineContents.indexOf( Delim.PARAMETER_OPEN ) ).trim();

					if ( methodInfo.equals( classes.getName() ) ) {
						pMethod.setMethodType( MethodType.Constructor );
					} else if ( methodInfo.equals( "~" + classes.getName() ) ) {
						pMethod.setMethodType( MethodType.Deconstructor );
					} else {
						pMethod.setMethodType( MethodType.Default );
					}

					pMethod.setAccessModifier( acModifier );
					switch( pMethod.getMethodType() ) {
						case Constructor:
						case Deconstructor:
						pMethod.setName( methodInfo );
							break;
						case Default: {
							pMethod.setName(
									methodInfo.substring( methodInfo.indexOf( " " ) ).trim() );
							pMethod.setReturnType( Type.set(
									methodInfo.substring( 0, methodInfo.indexOf( " " ) ).trim() ) );
						}
							break;
					}
					classes.setMethod( pMethod );
				}
			}
			lineScn.close();
		}
		initScn.close();
	}

	private void methodParser( String methodContents ) {
		// < Init: set scanner with '{', '}' >

		Scanner methodScn = new Scanner(
				methodContents.replace( Delim.CLAUSE_OPEN + Delim.CLUASE_CLOSE,
						Delim.CLAUSE_OPEN + Delim.SPACE + Delim.CLUASE_CLOSE ) );
		// To make non empty clauses. {} -> { }
		methodScn.useDelimiter( "\\" + Delim.CLAUSE_OPEN + "?+" + "\\" + Delim.CLUASE_CLOSE );
		/*
		 * regex: {?}, java syntax: (?<=\\{)\\} '\\{' : Firstly, use clause open
		 * capturing group '?+' : Matches between zero and one times, as many
		 * times as possible, without giving back '\\}' : with '}' character.
		 * 
		 * Honestly, just '}' has almost same effect, except performance for
		 * large contents.
		 */

		// < /Init >
		// < Contents parse >

		String contents = methodScn.next() + Delim.CLUASE_CLOSE;
		// To restore '}'

		while( methodScn.hasNext() ) {
			String tempContents = methodScn.next() + Delim.CLUASE_CLOSE;

			if ( trimLeading( tempContents ).indexOf( Delim.CLAUSE_OPEN ) <= 0 ) {
				contents = contents + tempContents;
				continue;
			} else {
				methodParseHelper( contents );
				contents = tempContents;
			}
		}

		if ( !contents.trim().isEmpty() )
			methodParseHelper( contents );

		methodScn.close();

		// < /Contents pars >
	}

	private void methodParseHelper( String contents ) {

		Classes classes = classList.get( classList.size() - 1 );
		// get current class.
		PMethod pMethod = null;
		String infoContent = contents.substring( 0, contents.indexOf( Delim.CLAUSE_OPEN ) ).trim();
		String infoContent_Prev = infoContent
				.substring( 0, infoContent.indexOf( classes.getName() + Delim.ACCESSOR ) ).trim();
		String infoContent_Post = infoContent
				.substring( infoContent.indexOf( classes.getName() + Delim.ACCESSOR )
						+ ( classes.getName() + Delim.ACCESSOR ).length() );

		// < Setting name and parameter >
		String methodName = infoContent_Post
				.substring( 0, infoContent_Post.indexOf( Delim.PARAMETER_OPEN ) ).trim();
		String paramContent = infoContent_Post
				.substring( infoContent_Post.indexOf( Delim.PARAMETER_OPEN ) )
				.replaceAll( "[" + Delim.PARAMETER_OPEN + Delim.PARAMETER_CLOSE + "]", Delim.BLANK )
				.trim();
		// Repalce (...) to ()

		pMethod = (PMethod) classes.getMethod( methodName );
		pMethod.setPropField( contents.substring( 0, contents.indexOf( Delim.CLAUSE_OPEN ) - 1 ) );

		if ( paramContent.contains( Delim.COMMA ) ) {
			Scanner paramScn = new Scanner( paramContent );
			paramScn.useDelimiter( Delim.COMMA );

			while( paramScn.hasNext() ) {
				String paramElem = paramScn.next().trim();

				String paramName = paramElem.substring( 0, paramElem.indexOf( Delim.SPACE ) - 1 );
				Type paramType = Type
						.set( paramElem.substring( paramElem.indexOf( Delim.SPACE ) + 1 ).trim() );
				pMethod.setParam( paramType, paramName );
			}
			paramScn.close();

		} else if ( paramContent.contains( Delim.SPACE ) ) {

			String paramName = paramContent.substring( paramContent.indexOf( " " ) + 1 );

			Type paramType = Type
					.set( paramContent.substring( 0, paramContent.indexOf( " " ) ).trim() );
			pMethod.setParam( paramType, paramName );
		} else if ( !paramContent.equals( Delim.BLANK ) ) {
			Type paramType = Type.set( paramContent.trim() );
			pMethod.setParam( paramType, Delim.BLANK );
		} else {
			pMethod.setParam( Type.NULL, Delim.BLANK );
		}

		pMethod.setName( methodName );
		pMethod.setReturnType( Type.set( infoContent_Prev ) );

		// < Set method name, return type, parameter information >

		// < set method contents >
		String methodClauses = contents.substring( contents.indexOf( Delim.CLAUSE_OPEN ),
				contents.lastIndexOf( Delim.CLUASE_CLOSE ) + 1 );

		pMethod.setContents( toCleanClauses( methodClauses ) );
		// < set method contents >
	}

	// This method use method open source JDK 1.8 as a reference to trim only
	// leading string.
	// Reference: java.lang.String.trim()
	private String trimLeading( String string ) {
		int len = string.length();
		int st = 0;
		char[] val = string.toCharArray(); /* avoid getfield opcode */

		while( ( st < len ) && ( val[st] <= ' ' ) ) {
			st++;
		}
		return ( ( st > 0 ) || ( len < string.length() ) ) ? string.substring( st, len ) : string;
	}

	// Trim unnecessary contents, as make the line which contains not only
	// escape character to first line
	// and make the line which contains the characters as to the last.
	//
	// Although this method get desired result usally, because this method check lines by '!'(U+0031)
	// it not always assure a correct one.
	private String toCleanClauses( String clauses ) {
		char[] val = clauses.toCharArray();
		int st = 0;
		int ed = val.length - 1;

		outerloop: for( int i = st + 1; i < ed; i++ ) {
			if ( val[i] == '\n' || val[i] == '\r' ) {
				for( int j = i + 1; j < ed; j++ ) {
					if ( val[j] == '\n' || val[j] == '\r' ) {
						i = j - 1;
						break;
					}

					if ( val[j] >= '!' ) {
						st = i;
						break outerloop;
					}
				}
			}
		}

		outerloop: for( int i = ed - 1; i > st; i-- ) {
			if ( val[i] == '\n' || val[i] == '\r' ) {
				for( int j = i - 1; j > st; j-- ) {
					if ( val[j] >= '!' ) {
						ed = i;
						break outerloop;
					}

					if ( val[j] == '\n' || val[j] == '\r' ) {
						i = j + 1;
						break;
					}
				}
			}
		}
		return clauses.substring( st + 1, ed );
	}
	//========================================= < method > =========================================
}
