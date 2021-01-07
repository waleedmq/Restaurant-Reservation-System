import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantTables {

	private ArrayList<Table> allTables;
	private ArrayList<Table> allTablesReversedBySize;

	private static RestaurantTables instance = new RestaurantTables(); 

	BookingOffice bk=BookingOffice.getInstance();

	public RestaurantTables() {
		allTables=new ArrayList<>();
		for(int i=1;i<10;i++)
			allTables.add(new Table("T0"+i,2));
		allTables.add(new Table("T10",2));
		for(int i=1;i<=6;i++)
			allTables.add(new Table("F0"+i,4));
		for(int i=1;i<=3;i++)
			allTables.add(new Table("H0"+i,8));

		allTablesReversedBySize=new ArrayList<>(allTables);		

	}


	public static RestaurantTables getInstance(){ return instance; }

	public Table findTable(String tCode) throws ExTableNotFound {
		for(Table t: allTables)
			if(tCode.equals(t.getTableCode()))
				return t;
		throw new ExTableNotFound(new Table(tCode,0));
	}

	public void tableAllocated(String[] cmdParts) throws ExTableNotFound, ExTableAlreadyAssignedForAnotherBooking, NumberFormatException, ExBookingNotFound, ExNotEnoughSeats, ExTableAlreadyAssignedForThisBooking {
		Reservation r=bk.searchByDateandTicket(cmdParts[1],Integer.parseInt(cmdParts[2]));
		int numberOfTables=cmdParts.length-3;
		int reservationSize=r.getPersons();
		int tableSize=0;
		boolean isTableAssignedByAnotherBooking=false;
		for(int i=3;i<numberOfTables+3;i++) {
			Table t=findTable(cmdParts[i]);
			tableSize+=t.getSize();
			if(tableAlreadyAssignedByAnotherBooking(t))
				isTableAssignedByAnotherBooking=true;
		}

		if(reservationSize>tableSize)
			throw new ExNotEnoughSeats();

		if(!r.TableNotAssigned()) 
			throw new ExTableAlreadyAssignedForThisBooking();

		if(isTableAssignedByAnotherBooking) {
			for(int i=3;i<numberOfTables+3;i++) {
				Table t=findTable(cmdParts[i]);
				if(tableAlreadyAssignedByAnotherBooking(t))
					throw new ExTableAlreadyAssignedForAnotherBooking(t);
			}
		}

		for(int i=3;i<numberOfTables+3;i++) {
			Table t=findTable(cmdParts[i]);
			t.changeAvailability();
			t.assignTicketCode(cmdParts[2]);
		}
	}


	public void unAssignTables(ArrayList <Table> allTablesAllocated) {
		for(Table t:allTablesAllocated) {
			for(Table table:allTables) {
				if(table.getTableCode().equals(t.getTableCode())) {
					table.changeAvailability();
					table.assignTicketCode("null");
				}
			}
		}
	}

	public void undoCancellation(ArrayList <Table> allTablesAllocated,String ticket) {
		for(Table t:allTablesAllocated) {
			for(Table table:allTables) {
				if(table.getTableCode().equals(t.getTableCode())) {
					table.changeAvailability();
					table.assignTicketCode(ticket);
				}
			}
		}
	}

	public boolean tableAlreadyAssignedByAnotherBooking(Table t) throws ExTableNotFound {
		if(!t.getTicketCode().equals("null"))
			return true;
		return false;
	}



	public void printTableAllocations(int totalPendingRequests,int totalPeople) {
		//Printing Allocated tables
		System.out.println("Allocated tables: ");
		int numberOfAllocatedTables=0;

		for(Table t: allTables)
			if(!t.isAvailable())
				numberOfAllocatedTables++;

		if(numberOfAllocatedTables==0) {
			System.out.println("[None]");
		} else {
			for(Table t: allTables)
				if(!t.isAvailable())
					System.out.println(t.getTableCode()+" (Ticket "+t.getTicketCode()+")");
		}


		//Printing Available Tables
		System.out.println("\nAvailable tables: ");
		for(Table t: allTables)
			if(t.isAvailable())
				System.out.print(t.getTableCode()+" ");

		//Printing Total number of pending requests
		System.out.println("\n");
		System.out.printf("Total number of pending requests = %d (Total number of persons = %d)\n",totalPendingRequests,totalPeople);
	}

	public int getTotalPersonAccomadationLeft() {
		int totalPersonAccomadationLeft=0;
		for(Table t: allTablesReversedBySize) {
			if(t.isAvailable()) {
				totalPersonAccomadationLeft+=t.getSize();
			}
		}
		return totalPersonAccomadationLeft;
	}

	public ArrayList <Table> getSuggestedTables(Reservation r) throws ExTableAlreadyAssignedForThisBooking {
		ArrayList <Table> suggestedTables=new ArrayList<>();
		Collections.sort(allTablesReversedBySize);

		int reservationSize=r.getPersons();


		if(!r.TableNotAssigned()) 
			throw new ExTableAlreadyAssignedForThisBooking();


		for(Table t: allTablesReversedBySize) {
			if(reservationSize>0) {
				if(reservationSize>=7) {
					if(t.isAvailable()&&t.getSize()==8) {
						suggestedTables.add(t);
						reservationSize-=t.getSize();
					} else if(t.isAvailable()&&t.getSize()==4) {
						suggestedTables.add(t);
						reservationSize-=t.getSize();
					} else if(t.isAvailable()&&t.getSize()==2) {
						suggestedTables.add(t);
						reservationSize-=t.getSize();
					}
				} else if(reservationSize>=3) {
					if(t.isAvailable()&&t.getSize()==4) {
						suggestedTables.add(t);
						reservationSize-=t.getSize();
					} else if(t.isAvailable()&&t.getSize()==2) {
						suggestedTables.add(t);
						reservationSize-=t.getSize();
					}
				} else {
					if(t.isAvailable()&&t.getSize()==2) {
						suggestedTables.add(t);
						reservationSize-=t.getSize();
					}
				} 
			}
		}
		return suggestedTables;
	}

}

