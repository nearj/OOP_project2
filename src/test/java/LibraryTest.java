/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import uos.gui.GUISystem;


import static org.junit.Assert.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Utilities;

import java.awt.*;
import java.awt.event.*;

public class LibraryTest {
	
    @Test public void testSomeLibraryMethod() {
    	String test = "Queue::Queue(void) {\nsize = 10;\nlast = 0;\nfirst = 0;\n}\nQueue::~Queue(void) {}\nbool Queue::IsEmpty() {\nif( (last) % size == first )\nreturn true;\nelse return false;\n}\nbool Queue::IsFull() {\nif( ( last + 1 ) % == first  )\nreturn true;\nelse return false;\n}\nvoid Queue::EnQueue( int data ) {\n  if( Queue::isFull() ) {\n    arr[last] = data;\n    last = ( last + 1 ) % size;\n{absd}\n{xzxcv}\n{asdf}\n  }\n}\nint Queue::DeQueue() {\nif( Queue::isEmpty() ) {\nreturn arr[first++];\nfirst = ( first + 1 ) % size;\n  }\n}\n";
    	Scanner scn = new Scanner(test);
    	scn.useDelimiter("\\{?+\\}");
    	String saver = "";
    	int i = 0;
    	while(scn.hasNext()) {
			String temp = scn.next() + "}";
			if( !scn.hasNext() )
				break;
    		if( trim(temp.toCharArray()).indexOf("{") <= 0 ) {
    			saver = saver + temp;
    			continue;
    		} else {
    			System.out.println( "----" + i++ + "----\n" + trim(saver.toCharArray()) );
    			saver = temp;
    		}
    	}
    	System.out.println(trim(saver.toCharArray()));
    	scn.close();
    }
    
    public String trim( char[] value ) {
        int len = value.length;
        int st = 0;
        char[] val = value;    /* avoid getfield opcode */

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        return ((st > 0) || (len < value.length)) ? String.copyValueOf(value).substring(st, len) 
        		: String.copyValueOf(value);
    }
}

