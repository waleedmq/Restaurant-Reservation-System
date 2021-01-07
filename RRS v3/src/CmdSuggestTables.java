public class CmdSuggestTables {
	Reservation r;
	BookingOffice bk=BookingOffice.getInstance();

	public void execute(String[] cmdParts) {
		try {
			if(cmdParts.length<3)
				throw new ExInsufficientCommandArguments();
			r=bk.searchByDateandTicket(cmdParts[1], Integer.parseInt(cmdParts[2]));
			bk.suggestTables(r);
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientCommandArguments e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssignedForThisBooking e) {
			System.out.println(e.getMessage());
		} catch (ExNotEnoughTables e) {
			System.out.println(e.getMessage());
		}

	}
}