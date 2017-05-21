package uos;

import java.util.List;
import java.util.ArrayList;

/**
 * Set of Delimiter to use.
 * 
 * @author imp
 */
public class Delim {
	
	public final static String INITIAL_OPEN = "{";
	public final static String INITIAL_CLOSE = "};";
	public final static String CLASS = "class";
	
	public final static String CONSTRUCTOR_OPEN = "{";
	public final static String CONSTRUCTOR_CLOSE = "}";
	
	
	public final static String CLAUSE_OPEN = "{";
	public final static String CLUASE_CLOSE = "}";
	
	
	public final static String PARAMETER_OPEN = "(";
	public final static String PARAMETER_CLOSE = ")";
	
	
	public final static String PAREN_OPEN = "(";
	public final static String PAREN_CLOSE = ")";
	
	public final static String ARRAY_OPEN = "[";
	public final static String ARRAY_CLOSE= "]";
	
	public final static String TAG_OPEN = "<";
	public final static String TAG_CLOSE = ">";
	
	public final static String BLANK = "";
	public final static String SPACE = " ";
	public final static String COLON = ":";
	public final static String COMMA = ",";
	public final static String ACCESSOR = "::";
	public final static String LINE_END = ";";
	public final static String LINE_FEED = System.lineSeparator();
	public final static String TAB = "\t";
	
	
	private static List<String> classList = new ArrayList<>();

	/**
	 * Setting class name as delimiter
	 * 
	 * @param name of class
	 */
	public static void setClassName( String className ) {
		classList.add(className);
	}
	
	/**
	 * Get list of class to use delimiter
	 * 
	 * @return List of class name
	 */
	public static List<String> getClassList() { 
		return classList;
	}
	
	/**
	 * Get class name to use delimiter
	 * 
	 * @param index of class name list
	 * @return class name in the list according index
	 */
	public static String getClassName( int index ) {
		return classList.get(index);
	}
}
