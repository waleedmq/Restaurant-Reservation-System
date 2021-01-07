public class ExTableAlreadyAssignedForAnotherBooking extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExTableAlreadyAssignedForAnotherBooking(Table t) {
		super(String.format("Table %s is already reserved by another booking!",t.getTableCode()));
	}

	public ExTableAlreadyAssignedForAnotherBooking(String message) {
		super(message);
	}
}