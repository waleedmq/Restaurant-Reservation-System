public class ExTableAlreadyAssignedForThisBooking extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExTableAlreadyAssignedForThisBooking() {
		super("Table(s) already assigned for this booking!");
	}

	public ExTableAlreadyAssignedForThisBooking(String message) {
		super(message);
	}
}