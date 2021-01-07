import java.util.ArrayList;
import java.util.Collections;

public class BookingOffice {
	
    private ArrayList<Reservation> allReservations;  

    private static BookingOffice instance = new BookingOffice(); 
    SystemDate sd=SystemDate.getInstance();
    
    private BookingOffice() {
    	allReservations=new ArrayList<>();   
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

}