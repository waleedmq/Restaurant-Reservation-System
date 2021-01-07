public class ExNotEnoughTables extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExNotEnoughTables(int numberOfPeople) {
		super("Suggestion for "+numberOfPeople+" persons: Not enough tables");
	}

	public ExNotEnoughTables(String message) {
		super(message);
	}
}