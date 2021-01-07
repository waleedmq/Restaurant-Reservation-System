public class CmdRequest extends RecordedCommand {
	Reservation r;
	BookingOffice bk=BookingOffice.getInstance();

	public void execute(String[] cmdParts) {
		try {
			if(cmdParts.length<5)
				throw new ExInsufficientCommandArguments();
			if(bk.isAlreadyPresent(cmdParts[1],cmdParts[2],Integer.parseInt(cmdParts[3]),cmdParts[4]))
				throw new ExBookingAlreadyExists();
			if(bk.DateHasPassed(cmdParts[4]))
				throw new ExDateAlreadyPassed();
			r=new Reservation(cmdParts[1],cmdParts[2],Integer.parseInt(cmdParts[3]),cmdParts[4]);
			bk.addReservation(r);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done. Ticket code for "+new Day(cmdParts[4]).toString()+": "+r.getTicketCode());
		}  catch (ExInsufficientCommandArguments e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch(ExBookingAlreadyExists e) {
			System.out.println(e.getMessage());
		} catch(ExDateAlreadyPassed e) {
			System.out.println(e.getMessage());
		}

	}
	@Override
	public void undoMe() {
		BookingTicketController.undoTicket(r.getDateDine());
		bk.removeReservation(r);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		bk.addReservation(r);
		BookingTicketController.takeTicket(r.getDateDine());
		addUndoCommand(this);
	}




}