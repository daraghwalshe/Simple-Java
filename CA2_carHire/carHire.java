// -------------------------------------------------------------------------+
// Fundamentals of Programming 2                    Assignment 2            |
// -------------------------------------------------------------------------+
// File: carHire.java                                                   	|
// -------------------------------------------------------------------------+
// Author:  Daragh Walshe                           Group: Group 6          |
// Student# B00064428                               Date:  April-May 2013   |
// -------------------------------------------------------------------------+
// DESCRIPTION:                                                             |
// A Java class to define an abstraction of a car hire transaction   		|
// -------------------------------------------------------------------------+

class carHire
{
	private String type;
	private int year;
	private String reg;
	private int days;
	private double cost;


	//========================================================
	// set method(default-constructor)
	carHire()
	{
	}


	//========================================================
	// set method(user-defined-constructor)
	carHire(String typeIn, int yearIn, String regIn, int daysIn, double costIn)
	{
		type = typeIn;
		year = yearIn;
		reg = regIn;
		days = daysIn;
		cost = costIn;
	}


	//========================================================
	// set methods

	public void setType(String typeIn)
	{
		type = typeIn;
	}

	public void setYear(int yearIn)
	{
		year = yearIn;
	}

	public void setReg(String regIn)
	{
		reg = regIn;
	}

	public void setDays(int daysIn)
	{
		days = daysIn;
	}

	public void setCost(double costIn)
	{
		cost = costIn;
	}
	//========================================================


	//========================================================
	// get methods

	public String getType()
	{
		return type;
	}

	public int getYear()
	{
		return year;
	}

	public String getReg()
	{
		return reg;
	}

	public int getDays()
	{
		return days;
	}

	public double getCost()
	{
		return cost;
	}


	//========================================================
	// display methods

	public void showLong()
	{
		System.out.println("\n\tType\t\t" + ": " + type);
		System.out.println("\tYear\t\t" + ": " + year);
		System.out.println("\tRegistration\t" + ": " + reg);
		System.out.println("\tNo. days hire\t" + ": " + days);
		System.out.println("\tCost\t\t" + ": " + cost + "\n");
	}
	//========================================================

	public void showBrief()
	{
		System.out.println("\n  "+ type +"\t : "+ year +"\t : "+ reg +"\t : "+ days +"\t : "+ cost);
	}
	//========================================================

}// end of class carHire

