import java.io.*;
import java.util.Scanner;

public class Day  implements Cloneable{

	private int year;
	private int month;
	private int day;
	private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";

	public void set(String sDay) //Set year,month,day based on a string like 01-Mar-2019
	{       
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
		this.year =Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1])/3+1;
	}

	public Day(String sDay) {set(sDay);} //Constructor, simply call set(sDay)



	@Override
	public String toString() 
	{       
		return day+"-"+ MonthNames.substring((month-1)*3,(month)*3) + "-"+ year; // (month-1)*3,(month)*3
	}


	@Override
	public Day clone() 
	{
		Day copy=null;
		try {
			copy=(Day) super.clone();
		} catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return copy;
	}

	@Override
	public int hashCode() { 
		return year*1000+month*100+day;

	}

	@Override
	public boolean equals(Object aDay) {
		if(aDay==null)
			return false;

		if(this.getClass()!=aDay.getClass())
			return false;

		Day otherDay=(Day) aDay;

		if(day!=otherDay.day)
			return false;

		if(month!=otherDay.month)
			return false;

		if(year!=otherDay.year)
			return false;

		return true;
	}



}