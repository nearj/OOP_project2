package uos;

import java.util.List;
import java.util.ArrayList;

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
	
	
	public final static String cBRACKET_OPEN = "(";
	public final static String cBRACKET_CLOSE = ")";
	
	
	public final static String ACCESSOR = "::";
	public final static String LINE_END = ";";
	public final static String SEMI_COLON = ",";
	
	private static List<String> classList = new ArrayList<>();
	
	public static void setClassName( String className ) {
		classList.add(className);
	}
	
	public static List<String> getClassList() { 
		return classList;
	}
	
	public static String getClassName( int index ) {
		return classList.get(index);
	}
}
