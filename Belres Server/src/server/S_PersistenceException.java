package server;

public class S_PersistenceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public S_PersistenceException(String msg) {
		super(msg);
	}
	
	public S_PersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}