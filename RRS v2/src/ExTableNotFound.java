public class ExTableNotFound extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExTableNotFound(Table t) {
		super("Table code "+t.getTableCode()+" not found!");
	}

	public ExTableNotFound(String message) {
		super(message);
	}
}