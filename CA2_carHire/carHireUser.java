// -------------------------------------------------------------------------+
// Fundamentals of Programming 2                    Assignment 2            |
// -------------------------------------------------------------------------+
// File: carHireUser.java                                                   |
// -------------------------------------------------------------------------+
// Author:  Daragh Walshe                           Group: Group 6          |
// Student# B00064428                               Date:  April-May 2013   |
// -------------------------------------------------------------------------+
// DESCRIPTION:                                                             |
// A Java program to manage the data of car-hire transactions	   			|
// -------------------------------------------------------------------------+

import java.util.*;
import java.io.*;

class carHireUser
{
	//static variables used in a lot of methods
	static Vector<Object> dataVector = new Vector<Object>();
	static Scanner userIn = new Scanner(System.in);

	//keep track of unsaved work on the system for quit-time
	static boolean needToSave = false, goodNight = false;

	public static void main(String args[])
	{
		//loads up the vector from text file
		getStarted();

		drawLine();drawLine();
		System.out.println("\t    **  Car Hire Records Database  **\tver 1.0");
		drawLine();drawLine();

		//go to the main menu
		showMenu();

	}//end main
	//========================================================


	//========================================================
	//method to load-up vector from text-file
	static void getStarted()
	{
		//load contents of text file

		//our variables
		String model, reg, textLine;//lineOfText,
		int year, days;
		double cost;

		try {
			File fileX = new File("hireDetails.txt");
			FileReader readX = new FileReader(fileX);
			BufferedReader inText = new BufferedReader(readX);

			textLine = inText.readLine();

			while (textLine != null) {

				//create new tokenizer to gather strings between tabs
				StringTokenizer token = new StringTokenizer(textLine,"\t");

				//gets 5 tokens from line of text-file
				model = token.nextToken();
				year = Integer.parseInt(token.nextToken());
				reg = token.nextToken();
				days = Integer.parseInt(token.nextToken());
				cost = Double.parseDouble(token.nextToken());

				//next line from text file
				textLine = inText.readLine();

				//create new carHire object
				carHire carHireTemp = new carHire(model, year, reg, days, cost);

				//place on Vector
				dataVector.addElement((Object)carHireTemp);

			}//end while-loop
			inText.close();
			readX.close();

		} catch (IOException e) {
			drawLine();
			System.out.println("\tERROR :    File does not exist !");
			System.out.println("\tDon't panic it will be created when needed.");
			drawLine();
		}

	}//end getStarted
	//========================================================


	//========================================================
	//method to display the main menu
	static void showMenu()
	{
		System.out.println("\n\n");
		drawLine();
		System.out.println("\n\t\t**  Welcome to the Main Menu  **\n\n");
		drawLine();

		//display options to user
		System.out.println("  Press:\tA....to hire a car.\n");
		System.out.println("\t\tB....to modify a transaction.\n");
		System.out.println("\t\tC....to display all.\n");
		System.out.println("\t\tD....to save.\n");
		System.out.println("\t\tE....to quit.\n\n");
		System.out.print("\t\t> ");

		getChoice();

	}//end showMenu
	//========================================================


	//========================================================
	//method to get users choice for main menu
	static void getChoice()
	{
		//use scanner to get input
		String choice = userIn.nextLine().toLowerCase();

		//loop to keep asking until valid input received
		while(!( choice.equals("a") || choice.equals("b") || choice.equals("c") ||
			choice.equals("d") || choice.equals("e") )){
			System.out.print("\n  Please choose either A,B,C,D or E only !\n\t\t");
			System.out.print("\t\t> ");

			//gets input and changes to lower-case
			choice = userIn.nextLine().toLowerCase();
			}//end while loop

		//deal with the answer
		processChoice(choice);

	}//end getChoice
	//========================================================


	//========================================================
	//method to check what the user wants to do
	//and go off to the relevant method
	static void processChoice(String choiceIn)
	{
		switch(choiceIn){
			case("a"):
				hireACar();
				break;
			case("b"):
				modify();
				break;
			case("c"):
				displayAll();
				break;
			case("d"):
				saveToFile();
				break;
			case("e"):
				quit();
				break;
			}//end switch
	}//end processChoice
	//========================================================


	//========================================================
	//method to get car-hire details
	static void hireACar()
	{
		//get car make
		String type = getCarModel();

		//get year and check against current year
		int year = getCarYear();

		System.out.print("\n\tPlease enter car registration > ");
		String reg = userIn.nextLine().toUpperCase();

		int days;
		do{
			System.out.print("\n\tPlease enter no. of days rental > ");
			days = getUserInteger();
			if(days<0 || days>10){
				System.out.println("\tError: minimum 1 day, maximum 10 days !");
				}//end if
			}while(days<0 || days>10);//end while

		double cost = getCost(days, year);

		//send details to the constructor
		carHire carHireA = new carHire(type, year, reg, days, cost);

		//place on vector as an object
		dataVector.addElement((Object)carHireA);

		//boolean flag for quit method to alert user work is unsaved
		needToSave = true;
		showMenu();

	}//end hireACar
	//========================================================


	//========================================================
	//method to get make of car and add some space at end
	//in order to line-up the output

	static String getCarModel()
	{
		//use scanner to get input
		System.out.print("\n\tPlease enter car model > ");
		String type = userIn.nextLine();
		//just for the look of the output
		if(type.length()<10){
			for(int i=type.length(); i<10; i++){
				type = type + " ";
				}//end for
			}//end if

		return type;

	}//end getCarModel
	//========================================================


	//========================================================
	//method to get year of car from user
	//and check against current year

	static int getCarYear()
	{
		System.out.print("\n\tPlease enter car year > ");
		int thisYear = whatYear();
		int carYear = getUserInteger();
		while(carYear > thisYear){
			System.out.print("\n\tError incorrect date");
			System.out.print("\n\tPlease enter car year : ");
			carYear = getUserInteger();
			}
		return carYear;
	}//end makeLonger
	//========================================================


	//========================================================
	//method to compute the cost of car-hire transaction
	static double getCost(int daysIn, int yearIn)
	{
		//find current year
		int year = whatYear();
		double charge;
		int costInCent, carAge;

		carAge = year-yearIn;//work out car age

		if(carAge <= 5){
			costInCent = daysIn*1500;
			}
		else{
			costInCent = daysIn*1000;
			}

		//4% discount
		if(daysIn > 6){
			costInCent = (costInCent*96)/100;
			}

		charge = costInCent;

		return charge/100;

	}//end getCost
	//========================================================


	//========================================================
	//method to return current year
	static int whatYear()
	{
		//find current year
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);

		return thisYear;

	}//end whatYear
	//========================================================


	//========================================================
	//method to modify car-hire details
	static void modify()
	{
		System.out.print("\n\tEnter registration to search > ");

		//use scanner to get input
		String regToFind = userIn.nextLine().toUpperCase();

		//go to search method
		search(regToFind);

		showMenu();

	}//end modify
	//========================================================


	//========================================================
	//method to search for entries
	static void search(String regSought)
	{
		//this loop finds the ammount of reg. matches for user
		int finds=0;
		for(int i=0; i<dataVector.size(); i++){
			carHire carToSearch = (carHire)dataVector.elementAt(i);
			String thisReg = carToSearch.getReg();
			if(thisReg.equals(regSought)){
				finds++;
				}
		}//end for loop
		drawLine();
		System.out.println("\t" + finds + " Records with registration " + regSought + " found.");
		drawLine();

		//this is the search with user interaction
		for(int j=0; j<dataVector.size(); j++){
			carHire carToSearch = (carHire)dataVector.elementAt(j);
			String thisReg = carToSearch.getReg();

			if(thisReg.equals(regSought)){

				System.out.println("\n\tTransaction No." + (j+1));
				carToSearch.showLong();

				System.out.print("\n\tAre the details correct? Y/N > ");
				String answer = userIn.nextLine().toLowerCase();

				while(!( answer.equals("y") || answer.equals("n") )){
					System.out.print("\n\tPlease enter Y or N > ");
					answer = userIn.nextLine().toLowerCase();
					}//end while

				//go to the change entry menu
				if(answer.equals("n")){
					j = changeEntry(j);
					}

				}//end if
			}//end for-loop

	}//end search
	//========================================================


	//========================================================
	//method to change an entry
	static int changeEntry(int index)
	{
		carHire badCar = (carHire)dataVector.elementAt(index);
		boolean stay = true, deFault = true, noVectorSet = true;

		while(stay){
			//menu
			drawLine();
			System.out.println("\n\tTo Change\tMake\tYear\tReg\tDays\tCost");
			System.out.println("\n\tPress\t\tM\tY\tR\tD\tC");
			System.out.println("\n\tPress Return to continue without making changes");
			System.out.println("\n\tPress X to Delete this entry");
			drawLine();
			System.out.print("\tEnter choice > ");
			String choice = userIn.nextLine().toUpperCase();

			//deal with answer
			switch(choice){
				case("M"):{
					modifyMake(badCar);
					deFault = true;
					};break;
				case("Y"):{
					modifyYear(badCar);
					deFault = true;
					};break;
				case("R"):{
					modifyReg(badCar);
					deFault = true;
					};break;
				case("D"):{
					modifyDays(badCar);
					deFault = true;
					};break;
				case("C"):{
					modifyCost(badCar);
					deFault = true;
					};break;
				case(""):{
					stay = false;
					deFault = true;
					};break;
				case("X"):{
					index = deleteEntry(index);
					noVectorSet = false;
					stay = false;
					};break;
				default:{
					System.out.print("\n\tError, Enter only : M Y R D or C : \n\n");
					deFault = false;
					};
				}//end switch

				//places modified entry back on static vector and display new info
				if(deFault && noVectorSet){
					drawLine();
					System.out.println("\tModified entry :");
					dataVector.set(index,badCar);
					badCar.showLong();
					}

			}//end while
			return index;
	}//end changeEntry
	//========================================================


	//========================================================
	//method to delete an entry
	//removes element from the vector and returns a reduced index
	//which is also the control for our for-loop, used to search the vector for matches
	//as the vector is now shorter !!! and we are going back inside the for-loop
	static int deleteEntry(int indexIn)
	{
		dataVector.removeElementAt(indexIn);
		drawLine();
		System.out.println("\t\t  !  Entry Deleted  !");
		drawLine();
		needToSave = true;
		return indexIn-1;
		}//end modifyMake
	//========================================================


	//========================================================
	//method to change car make/model
	static void modifyMake(carHire badCar)
	{
		String makeNew = getCarModel();
		badCar.setType(makeNew);
		needToSave = true;
		}//end modifyMake
	//========================================================

	//========================================================
	//method to change car year
	static void modifyYear(carHire badCar)
	{
		System.out.print("\tEnter new year > ");
		int yearNew = getCarYear();
		badCar.setYear(yearNew);
		needToSave = true;
		}//end modifyYear
	//========================================================

	//========================================================
	//method to change car registration
	static void modifyReg(carHire badCar)
	{
		System.out.print("\tEnter new reg. > ");
		String regNew = userIn.nextLine().toUpperCase();
		badCar.setReg(regNew);
		needToSave = true;
		}//end modifyReg
	//========================================================

	//========================================================
	//method to change days
	static void modifyDays(carHire badCar){
		System.out.print("\tEnter new ammount of days > ");
		int daysNew = getUserInteger();
		if(daysNew > 10){
			System.out.println("\tWARNING : this figure will exceed the 10 day limit!");
			}
		badCar.setDays(daysNew);
		needToSave = true;
		}//end modifyDays
	//========================================================

	//========================================================
	//method to change car cost
	static void modifyCost(carHire badCar){
		System.out.print("\tEnter new cost > ");
		while(!userIn.hasNextDouble()){
				userIn.nextLine();
				System.out.print("\n\tError: Please enter a numerical value > ");
		}
		double costNew = userIn.nextDouble();
		userIn.nextLine();
		badCar.setCost(costNew);
		needToSave = true;
		}//end modifyCost
	//========================================================


	//+-+==================================================+-+
	//method to get an integer from user input
	static int getUserInteger(){

		int intNew=-1;
		boolean badNum=true;

		//check input is numerical
		while(badNum){
			while(!userIn.hasNextInt()){
				System.out.print("\n\tError, Please enter a numerical value > ");
				userIn.nextLine();
				}//end while
			intNew = userIn.nextInt();
			userIn.nextLine();

			//check input is positive
			if(intNew<0){
				System.out.print("\n\tError, Please enter a positive value > ");
				//userIn.nextLine();
				}else{
					badNum=false;
					}//end if-else

			}//end while badNum

		return intNew;

		}//end getUserInteger
	//+-+==================================================+-+


	//========================================================
	//method to display all car-hire entries
	static void displayAll()
	{
		//clear the screen - somewhat -
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		drawLine();
		System.out.println("\t  **  All Transactions on Record  **");
		drawLine();
		System.out.println("  Make\t\t | Year\t | Reg\t\t | Days\t | Cost");
		drawLine();

        //displaying contents of the Vector
        for(int i=0; i<dataVector.size(); i++){
				carHire carHireX = (carHire)dataVector.elementAt(i);
				carHireX.showBrief();
			}//end for loop

		drawLine();
		System.out.print("\n\t\tPress Return to continue > ");

		//use scanner to get input
		String moveOn = userIn.nextLine();

		//loop to run until return entered
		while(!(moveOn.equals("")) ){
			System.out.print("\n\t\t!! Please enter return only !! \n\t\t> ");
			moveOn = userIn.nextLine();
			}

		showMenu();

	}//end displayAll
	//========================================================


	//========================================================
	//method to write a line to the screen
	static void drawLine()
	{
		System.out.print("  ");
		for(int i=0; i<60; i++){
			System.out.print("-");
			}
		System.out.println();

	}//end drawLine
	//========================================================


	//========================================================
	//method to save all car-hire entries to a text file
	static void saveToFile()
	{
		try{
			FileWriter writeF = new FileWriter("hireDetails.txt");
			PrintWriter printF = new PrintWriter(writeF);

			//puts objects on vector into text file
			for(int i=0; i<dataVector.size(); i++){
				carHire carHireX = (carHire)dataVector.elementAt(i);
				printF.println(
					carHireX.getType() + "\t" +
					carHireX.getYear() + "\t" +
					carHireX.getReg() + "\t" +
					carHireX.getDays() + "\t" +
					carHireX.getCost() + "\t"
					);//end of text line entry to file
				}//end for loop
			printF.close();
			writeF.close();
			}//end try

		catch(IOException e){
			e.printStackTrace();
			}//end catch

		needToSave = false;

		drawLine();
		System.out.println("\t ** Saved to file : hireDetails.txt **");
		drawLine();

		//if not being saved from the quit routine
		//returns user to the main menu
		if(!goodNight){
			showMenu();
			}

	}//end saveToFile
	//========================================================


	//========================================================
	//method to quit program
	static void quit()
	{
		//do a quit routine
		goodNight = true;

		if(needToSave){
			drawLine();
			System.out.println("\t\tWARNING ! You have unsaved entries ! ");
			drawLine();
			System.out.println("\tTo -Exit without saving ..... press: X");
			System.out.print("\n\t   -Save and Exit ........... press: Return  > ");
			String answer = userIn.nextLine().toLowerCase();
			drawLine();


			while(!( answer.equals("x") || answer.equals("") )){
				System.out.print("\n\tPlease either X or Return > ");
				answer = userIn.nextLine().toLowerCase();
				}//end while

			//save last entries
			if(answer.equals("")){
				saveToFile();
				}

			}//end if needToSave

		//close the scanner
		userIn.close();

		drawLine();
		System.out.println("\t ** Shutting Down ** ");
		drawLine();

		}//end quit
	//========================================================


//============================================================
//****************************
}// end of class carHireUser
//****************************
//============================================================



