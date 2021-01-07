public class CmdCancel  extends RecordedCommand {
	Reservation r;
	BookingOffice bk=BookingOffice.getInstance();
	String[] commands;

	public void execute(String[] cmdParts) {
		try {
			if(cmdParts.length<3)
				throw new ExInsufficientCommandArguments();
			if(bk.DateHasPassed(cmdParts[1]))
				throw new ExDateAlreadyPassed();
			commands=cmdParts;
			r=bk.searchByDateandTicket(cmdParts[1], Integer.parseInt(cmdParts[2]));
			bk.cancelReservation(r);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		}  catch (ExInsufficientCommandArguments e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExDateAlreadyPassed e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void undoMe() {
		bk.undoCancelBooking(r);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		try {
			r=bk.searchByDateandTicket(commands[1], Integer.parseInt(commands[2]));
			bk.cancelReservation(r);
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		}
		addUndoCommand(this);
	}
}
