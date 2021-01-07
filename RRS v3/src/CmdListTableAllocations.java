public class CmdListTableAllocations {

	BookingOffice bk=BookingOffice.getInstance();

	public void execute(String[] cmdParts) {
		try {
			if(cmdParts.length<2)
				throw new ExInsufficientCommandArguments();
			bk.printTableAllocations(cmdParts);
		} catch(ExInsufficientCommandArguments e) {
			e.getMessage();
		}
	}
}
