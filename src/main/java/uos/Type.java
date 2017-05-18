package uos;

public enum Type {
	VOID("void"), BOOL("bool"), INT("int");
	
	private String type;
	
	private Type( String type ) {
		this.type = type;
	}
	
	public String get() {
		return type;
	}
	
	public static Type set( String type ) {
		if( type.equals(VOID) )
			return Type.VOID;
		else if( type.equals(BOOL) )
			return Type.BOOL;
		else if( type.equals(INT) )
			return Type.INT;
		else return null;
	}
}