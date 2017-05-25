package uos;

/**
 * Type of member or method.
 * 
 * @author 216920054_JUHAYONG
 */
public enum Type {
	VOID( "void" ), BOOL( "bool" ), INT( "int" ), NULL( "" );

	private String type;

	private Type( String type ) {
		this.type = type;
	}

	public String getTypeName() {
		return type;
	}

	/**
	 * With String parameter, return according type.
	 * 
	 * @param type
	 * @return Type which according the string parameter.
	 */
	public static Type set( String type ) {
		if ( type.equals( VOID.type ) )
			return Type.VOID;
		else if ( type.equals( BOOL.type ) )
			return Type.BOOL;
		else if ( type.equals( INT.type ) )
			return Type.INT;
		else
			return null;
	}
}