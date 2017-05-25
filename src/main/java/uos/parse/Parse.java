package uos.parse;

/**
 * This interface stand for parsed class which has contents to seperate information from contents.
 * 
 * @author 2016920054_주하용
 */
public interface Parse {

	// --------------------------------- Name -----------------------------------
	/**
	 * Set contents of parse data.
	 * 
	 * @param contents
	 *            contents of data.
	 */
	public void setContents( String contents );

	/**
	 * Return contents of parse data.
	 * 
	 * @return contents of data.
	 */
	public String getContents();

	// --------------------------------- Name -----------------------------------
}