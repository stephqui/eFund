package fr.isika.cda14.efund.exception;

public class UserAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6030109082223467064L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
