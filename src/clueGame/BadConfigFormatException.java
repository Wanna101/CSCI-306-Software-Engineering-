package clueGame;

import java.util.*;
import java.io.*;
import java.time.Instant;

@SuppressWarnings({ "unused", "serial" })
public class BadConfigFormatException extends Exception {

	/*
	 * Appends a meaningful message to the user on the console and also to a separate .txt file
	 * in the src/ directory.
	 * 
	 * Also records time the error message was printed in GMT.
	 * 
	 * File is written to the tests package.
	 */
	
	private void appendMessage(String string) {
		try {
			File log = new File("src\\tests\\ExceptionMessages.txt");
			if (log.exists() == false) {
				System.out.println("Creating new file for error messages...");
				log.createNewFile();
				System.out.println("File created successfully");
			}
			PrintWriter printWriter = new PrintWriter(new FileWriter(log, true));
			printWriter.append(Instant.now().toString() + " GMT: " + string + "\n");
			
			printWriter.close();
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException(): Could not find file");
		} catch (IOException e) {
			System.err.println("IOException(): Could not append message to file");
		}
		System.err.println(string);
	}
	
	public BadConfigFormatException() {
		appendMessage("Bad config file format in file");
	}
	
	public BadConfigFormatException(String string) {
		appendMessage("BadConfigFormatException(): " + string);
	}
}
