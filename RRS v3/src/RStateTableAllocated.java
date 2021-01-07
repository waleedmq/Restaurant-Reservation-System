import java.util.ArrayList;

public class RStateTableAllocated implements RState {

	private ArrayList<Table> tablesAllocated;

	RestaurantTables rt=RestaurantTables.getInstance();



	public RStateTableAllocated(String[] cmdParts) throws ExTableNotFound, ExTableAlreadyAssignedForThisBooking {
		tablesAllocated=new ArrayList<>();
		int numberOfTables=cmdParts.length-3;
		for(int i=3;i<numberOfTables+3;i++) {
			Table t=rt.findTable(cmdParts[i]);
			tablesAllocated.add(t);
		}

	}

	public String genState() {
		String output="Table assigned:";
		for(Table t: tablesAllocated) {
			output+=" ";
			output+=t.getTableCode();
		}
		return output;
	}

	public ArrayList<Table> getTablesAllocated() {
		return tablesAllocated;
	}

}
