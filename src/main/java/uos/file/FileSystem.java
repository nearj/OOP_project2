package uos.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

import uos.parse.PSource;

/**
 * This class implements to open files and to parse data.
 * @author 2016920054_JUHAYONG
 *
 */
public class FileSystem {

	/**
	 * File extenstion class
	 * @author 2016920054_JUHAYONG
	 *
	 */
	final static class Ext {
		final static String CPP = "cpp";
		final static String WILDCARD = "*";
	}
	
	private static final InputStreamReader inStrmReader =
			new InputStreamReader( System.in );
	
	private static final String DEFAULT_PATH = "./";
	private static final String DEFAULT_FILENAME = "QUEUE";
	private static final String DEFAULT_EXT = Ext.CPP;
	// Default setting.
	
	private String fileName = DEFAULT_FILENAME;
	private String fullName = fileName + "." + DEFAULT_EXT;
	private String fullPath = DEFAULT_PATH + fullName; 
	// Path setting. 
	
	/**
	 * Read file with user prompted file.
	 * 
	 * @param File to read and parse.
	 */
	public PSource reader( File file ) {
		PSource pSource = PSource.newInstance();
		
		try {
			pSource.setFileName( file.getName() );
			pSource.setContents( 
					new String( Files.readAllBytes( file.toPath() ) ,StandardCharsets.UTF_8 ) );
		} catch ( InvalidPathException e) {
			System.out.println( "Error: " + e );
		} catch ( IOException e ) {
			System.out.println( "Error: " + e );
		}
		
		return pSource;
	}
	
	/**
	 * Prompt file to read.
	 * 
	 * @return File which is selected by interface.
	 */
	public File open() {
		System.out.print( "Select File Name: " );
		Scanner scn = new Scanner(inStrmReader);
		fileName = scn.nextLine();
		scn.close();
		fullPath = DEFAULT_PATH + fileName + "." + Ext.CPP;
		return new File(fullPath);
	}
	
	/**
	 * Close program.
	 */
	public void close() {
		System.exit(0);
	}
}
