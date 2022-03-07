package clueGame;

public class BadConfigFormatException extends Exception {

	/*
	 * TODO: need to look up how to "append" (add on) to a file
	 */
	
	public BadConfigFormatException() {
		// TODO: append a default meaningful message
		super("Bad config file format in file");
	}
	
	public BadConfigFormatException(String string) {
		// TODO: append what you pass in
		super("Problem was: " + string);
	}
	
}
