public class CmdListReservations {

	public void execute(String[] cmdParts) {
		BookingOffice.getInstance().listReservations();
	}


}