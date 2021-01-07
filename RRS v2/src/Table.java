
public class Table {
	private String tableCode;
	private String ticketCode="null";
	private int size;
	private boolean available;

	public Table(String code,int size) {
		tableCode=code;
		this.size=size;
		available=true;
	}

	public String getTableCode() {
		return tableCode;
	}

	public int getSize() {
		return size;
	}

	public void assignTicketCode(String tCode) {
		ticketCode=tCode;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void changeAvailability() {
		if(available==true)
			available=false;
		else
			available=true;
	}

	public boolean isAvailable() {
		return available;
	}
}
