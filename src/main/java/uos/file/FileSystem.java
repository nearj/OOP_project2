package uos.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

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
	public final static class Ext {
		public final static String CPP = "cpp";
	}
	
	public static final String DEFAULT_PATH = "./";
	public static final String DEFAULT_FILENAME = "QUEUE";
	public static final String DEFAULT_EXT = Ext.CPP;
	// Default setting.
	
	private static PSource pSource = PSource.newInstance();
	
	/**
	 * Read file and pass to Parse Source class
	 * 
	 * @param file File to read.
	 * @return Parse Source Class
	 */
	public static PSource read( File file ) {
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
	
	public static void save( PSource pSource ) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(pSource.getFileName() + "has classes");
		pSource.getClassList();
		
	}

	/**
	 * Close program.
	 */
	public void close() {
		System.exit(0);
	}
}
