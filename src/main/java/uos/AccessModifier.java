package uos;

/**
 * Accssor modifier
 * 
 * @author imp
 */
public enum AccessModifier {
	PUBLIC("public"), PRIVATE("private"), PROTECTED("protected");
	
	private String am;
	
	private AccessModifier( String am ) {
		this.am = am;
	}
	
	public String toString() {
		return am;
	}
	
	public static AccessModifier set( String am ) {
		if( am.equals(PUBLIC.am) )
			return AccessModifier.PUBLIC;
		else if( am.equals(PRIVATE.am) )
			return AccessModifier.PRIVATE;
		else return null;
	}
}