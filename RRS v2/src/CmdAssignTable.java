public class CmdAssignTable extends RecordedCommand {
	Reservation r;
	BookingOffice bk=BookingOffice.getInstance();
	String[] commands;

	public void execute(String[] cmdParts) {
		try {
			if(cmdParts.length<4)
				throw new ExInsufficientCommandArguments();
			if(bk.DateHasPassed(cmdParts[1]))
				throw new ExDateAlreadyPassed();
			commands=cmdParts;
			bk.assigningTables(cmdParts);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		}  catch (ExInsufficientCommandArguments e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch(ExDateAlreadyPassed e) {
			System.out.println(e.getMessage());
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExNotEnoughSeats e) {
			System.out.println(e.getMessage());
		} catch (ExTableNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssignedForThisBooking e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssignedForAnotherBooking e) {
			System.out.println(e.getMessage());
		}

	}
	
	@Override
	public void undoMe() {
		try {
			r=bk.searchByDateandTicket(commands[1], Integer.parseInt(commands[2]));
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		}
		bk.undoTableAssign(r);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		try {
			bk.assigningTables(commands);
		} catch (ExNotEnoughSeats e) {
			System.out.println(e.getMessage());
		} catch (ExTableNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssignedForThisBooking e) {
			System.out.println(e.getMessage());
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExTableAlreadyAssignedForAnotherBooking e) {
			System.out.println(e.getMessage());
		}
		addUndoCommand(this);
	}




}