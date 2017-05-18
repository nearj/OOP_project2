package uos.parse;

/**
 * This interface stand for parsed class which has contents to seperate 
 * information from contents.
 * 
 * @author imp
 */
public interface Parse {
	
	// --------------------------------- Name -----------------------------------
	/**
	 * Set contents of parsed data. 
	 * @param contents contents of data.
	 */
	public void setContents( String contents );
	
	/**
	 * Return contents of parsed data. 
	 * @return contents of data.
	 */
	public String getContents();
	
	// --------------------------------- Name -----------------------------------	
}