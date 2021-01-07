import java.util.ArrayList;

public class Reservation implements Comparable<Reservation>{
	private String guestName;
	private String phoneNumber;
	private int totPersons;
	private Day dateDine;
	private Day dateRequest;
	private RState status;
	private int ticketCode;


	public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine){   
		this.guestName=guestName;
		this.phoneNumber=phoneNumber;
		this.totPersons=totPersons;
		this.dateDine=new Day(sDateDine);
		this.dateRequest=SystemDate.getInstance().clone();
		this.status=new RStatePending();
		this.ticketCode=BookingTicketController.takeTicket(this.dateDine);      
	}

	@Override
	public String toString() {
		return String.format("%-13s%-11s%-14s%-25s%4d       %s",guestName, phoneNumber, dateRequest, dateDine+String.format(" (Ticket %d)", ticketCode), totPersons, status.genState());
	}

	public static String getListingHeader() {
		return String.format("%-13s%-11s%-14s%-25s%-11s%s", "Guest Name", "Phone", "Request Date", "Dining Date and Ticket", "#Persons", "Status");
	}

	@Override
	public int compareTo(Reservation another) 
	{
		if (this.guestName.equals(another.guestName)) {
			if(this.phoneNumber.compareTo(another.phoneNumber)>0)
				return 1;
			else if(this.phoneNumber.compareTo(another.phoneNumber)<0)
				return -1;
			else if(this.phoneNumber.compareTo(another.phoneNumber)==0) {
				if(dateDine.hashCode()>another.dateDine.hashCode())
					return 1;
				else if(dateDine.hashCode()<another.dateDine.hashCode())
					return -1;
			}
		} 
		else if (this.guestName.compareTo(another.guestName)>0) return 1;
		else return -1;
		return 0;
	}

	public int getTicketCode() {
		return ticketCode;
	}

	public String getName() {
		return guestName;
	}

	public String getNumber() {
		return phoneNumber;
	}

	public int getPersons() {
		return totPersons;
	}

	public Day getDateDine() {
		return dateDine;
	}


	public void setStatus(RState newStatus) {
		status=newStatus;
	}

	public boolean TableNotAssigned() {
		return status instanceof RStatePending;
	}

	public void assigningTables(String[] cmdParts) throws ExNotEnoughSeats, ExTableNotFound, ExTableAlreadyAssignedForThisBooking {
		setStatus(new RStateTableAllocated(cmdParts));
	}

	public ArrayList <Table> undoTableAssign() {
		ArrayList <Table> tablesAllocated= new ArrayList<>();
		tablesAllocated=((RStateTableAllocated)status).getTablesAllocated();
		setStatus(new RStatePending());
		return tablesAllocated;
	}

}





