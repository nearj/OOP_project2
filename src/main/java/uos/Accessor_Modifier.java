package uos;

public enum Accessor_Modifier {
	PUBLIC("public"), PRIVATE("private");
	
	private String am;
	
	private Accessor_Modifier( String am ) {
		this.am = am;
	}
	
	public String toString() {
		return am;
	}
	
	public static Accessor_Modifier set( String am ) {
		if( am.equals(PUBLIC) )
			return Accessor_Modifier.PUBLIC;
		else if( am.equals(PRIVATE) )
			return Accessor_Modifier.PRIVATE;
		else return null;
	}
}