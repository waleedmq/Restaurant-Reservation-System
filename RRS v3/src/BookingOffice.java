import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BookingOffice {

	private ArrayList<Reservation> allReservations;	
	private HashMap<Day, RestaurantTables> rtControllers;


	private static BookingOffice instance = new BookingOffice(); 
	SystemDate sd=SystemDate.getInstance();

	private BookingOffice() {
		allReservations=new ArrayList<>();
		rtControllers=new HashMap<>();
	}

	public static BookingOffice getInstance(){ return instance; }

	public Reservation addReservation(String sName,String sNumber, int sPersons, String sDate) throws ExBookingAlreadyExists, ExDateAlreadyPassed {
		Reservation r = new Reservation(sName,sNumber,sPersons,sDate);
		allReservations.add(r);
		Collections.sort(allReservations);
		return r;
	}


	public void listReservations()
	{
		System.out.println(Reservation.getListingHeader());
		for (Reservation r: allReservations)
			System.out.println(r);
	}

	public void removeReservation(Reservation r) {   
		allReservations.remove(r);
	}

	public void addReservation(Reservation r) {
		allReservations.add(r);
		Collections.sort(allReservations);
	}

	public boolean isAlreadyPresent(String sName,String sNumber, int sPersons, String sDate) {
		for(Reservation res: allReservations)
			if(sName.equals(res.getName()))
				if(sNumber.equals(res.getNumber()))
					if(sDate.equals(res.getDateDine().toString()))
						return true;
		return false;
	}

	public boolean DateHasPassed(String sDate) {
		Day d=new Day(sDate);
		if(d.hashCode()<sd.hashCode())
			return true;
		return false;
	}

	public Reservation searchByDateandTicket(String sDate,int sTicket) throws ExBookingNotFound {
		for(Reservation r: allReservations) {
			if(r.getDateDine().toString().equals(sDate)&&sTicket==r.getTicketCode())
				return r;
		}
		throw new ExBookingNotFound();
	}

	public void assigningTables(String[] cmdParts) throws ExNotEnoughSeats, ExTableNotFound, ExTableAlreadyAssignedForThisBooking, ExBookingNotFound, ExTableAlreadyAssignedForAnotherBooking {
		Reservation r=null;
		r=searchByDateandTicket(cmdParts[1],Integer.parseInt(cmdParts[2]));

		RestaurantTables rt=rtControllers.get(new Day(cmdParts[1]));
		if(rt == null) {
			rtControllers.put(r.getDateDine(),new RestaurantTables());
			rtControllers.get(r.getDateDine()).tableAllocated(cmdParts);
			r.assigningTables(cmdParts);
		}
		else {
			rt.tableAllocated(cmdParts);
			r.assigningTables(cmdParts);
		}

	}

	public void undoTableAssign(Reservation r) {
		RestaurantTables rt=rtControllers.get(r.getDateDine());
		ArrayList <Table> allTablesAllocated;
		allTablesAllocated=r.getTablesAlocated();
		rt.unAssignTables(allTablesAllocated);
		r.setStatus(new RStatePending());
	}

	public void printTableAllocations(String[] cmdParts) {
		int totalPendingRequests=0;
		int totalPeople=0;
		Day d=new Day(cmdParts[1]);
		RestaurantTables rt=rtControllers.get(d);
		if(rt == null) {
			rtControllers.put(d,new RestaurantTables());
			rt = rtControllers.get(d);
		}
		for(Reservation r: allReservations) {
			if(r.getDateDine().toString().equals(cmdParts[1])) {
				if(r.TableNotAssigned()) {
					totalPendingRequests++;
					totalPeople+=r.getPersons();
				}
			}
		}
		rt.printTableAllocations(totalPendingRequests,totalPeople);
	}

	public void cancelReservation(Reservation r) {
		Day d=r.getDateDine();
		RestaurantTables rt=rtControllers.get(r.getDateDine());

		if(r.TableNotAssigned()) {
			allReservations.remove(r);
			Collections.sort(allReservations);
		}else {
			ArrayList <Table> allTablesAllocated= new ArrayList<>();
			allTablesAllocated=r.getTablesAlocated();
			rt.unAssignTables(allTablesAllocated);
			allReservations.remove(r);
			Collections.sort(allReservations);
		}
	}

	public void undoCancelBooking(Reservation r) {
		RestaurantTables rt=rtControllers.get(r.getDateDine());
		if(r.TableNotAssigned()) {
			allReservations.add(r);
			Collections.sort(allReservations);
		}else {
			ArrayList <Table> allTablesPreviouslyAllocated= new ArrayList<>();
			allTablesPreviouslyAllocated=r.getTablesAlocated();
			rt.undoCancellation(allTablesPreviouslyAllocated, String.valueOf(r.getTicketCode()));
			allReservations.add(r);
			Collections.sort(allReservations);
		}
	}

	public void suggestTables(Reservation r) throws ExTableAlreadyAssignedForThisBooking, ExNotEnoughTables {
		ArrayList <Table> suggestedTables;
		RestaurantTables rt=rtControllers.get(r.getDateDine());
		if(rt == null) {
			rtControllers.put(r.getDateDine(),new RestaurantTables());
			suggestedTables=rtControllers.get(r.getDateDine()).getSuggestedTables(r);
		}
		else {
			suggestedTables=rt.getSuggestedTables(r);
		}

		if(r.getPersons()>rtControllers.get(r.getDateDine()).getTotalPersonAccomadationLeft()) {
			throw new ExNotEnoughTables(r.getPersons());
		} else {
			System.out.print("Suggestion for "+r.getPersons()+" persons:");
			for(Table t: suggestedTables)
				System.out.print(" "+t.getTableCode());
			System.out.println("\n");
		}
	}
}




