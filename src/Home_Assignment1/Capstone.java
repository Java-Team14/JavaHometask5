package Home_Assignment1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class InvalidUserInputException extends Exception{							
    public InvalidUserInputException(String message)
    {
        super(message);
        
    }
}

class ChargingStations 
{
	String name_of_Charging_Station;
	int count_Charging_locations;
	int number_of_Free_stations_available;
	List<String> Available_Source_of_Energy;
	
	 public ChargingStations(String name, int no_of_charging_location, String...type)
	    {
	        this.name_of_Charging_Station=name;
	        this.count_Charging_locations=no_of_charging_location;
	        this.Available_Source_of_Energy=new ArrayList<>();

	        for(String t: type)
	        {
	             this.Available_Source_of_Energy.add(t);
	        }
	        number_of_Free_stations_available = no_of_charging_location-1;
	    }
}


public class Capstone {
    public static void main(String[] args)
    {
    	ChargingStations Ionity=new ChargingStations("Ionity",4,"Diesel","Petrol");
    	ChargingStations FastEnd=new ChargingStations("FastEnd",6,"Diesel","CNG");
    	ChargingStations Ladestation=new ChargingStations("Ladestation",7,"CNG","Petrol");
    	ChargingStations Vattenfall=new ChargingStations("Vattenfall",8,"Diesel","Petrol");
    	
    	
    	ChargingStations csarray[]={Ionity,FastEnd,Ladestation,Vattenfall};
    	
        System.out.println("Welcome to the Charging App. Menu will be displayed for you to select options: \n");
        int selected_station=0;
        ChargingStationFinder(csarray);
        System.out.print("\n Please enter your choice of station. \n ");
        Scanner sc=new Scanner(System.in);
        
        try{   										 //Handling Multiple Exceptions											
            int User_Choice=sc.nextInt();
            if(User_Choice<1 || User_Choice>csarray.length)
            {
                throw new InvalidUserInputException("Apologies. You have not selected available options. The system is selecting option 1 by default.");
            }
            selected_station=User_Choice-1;
        } catch (InvalidUserInputException e) {        
            System.out.println("Number selected must be between 1 and 4. "+ e);
        }
        catch (InputMismatchException e) {
            System.out.println("Input should be a number and not a character. "+e);
        } 
        
        
        System.out.println("Please Enter the Energy resource : ");
        String selected_E_sourse=null;
        
        
       try {								 //rethrowing exception
            selected_E_sourse=selectEnergySource(csarray[selected_station]);
        } catch (InputMismatchException e) {              
            System.out.println("Exception Occured: "+e.getMessage());
        }
             
        System.out.println("You have "+csarray[selected_station].number_of_Free_stations_available+
        " free charging slots available for charging with your selected energy source "+selected_E_sourse);
       
        
        try {								//chaining exception
        	ChargeCar(); 
        } catch (Exception e) {     				
            System.out.println("Exception occured: "+e.getMessage());
        }
        
       
            try (Scanner scanner = new Scanner(System.in)) {					//resource management
                System.out.print("Enter your name to print the receipt of the charging done: ");
                String name = scanner.nextLine();
                System.out.println("Hello " + name + " !");

                System.out.println("We are printing your receipt. Thank you for visiting us. See you soon again!");
                
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
    }
    

    static void ChargingStationFinder(ChargingStations Chargingstationsarray[])
	{
         
         DisplayMenu();	
         
    }
	
	public static void DisplayMenu() {									//Displays the main Menu option 
		System.out.println("Please enter between 1-4 based on available charging stations:\n"
				+ "option 1: Select charging station name: IONITY \n"
				+ "option 2: Select charging station name: FASTEND \n"
				+ "option 3: Select charging station name: LADESTATION \n"
				+ "option 4: select charging station name: VATTENFALL \n");
	}
	
	
	 static String selectEnergySource(ChargingStations Array_of_ChargingStations)	
	    {
	        System.out.println("The sources of energy available for charging are : " + Array_of_ChargingStations.Available_Source_of_Energy);
	        
	        String UserInput="Diesel";
	        try  {
	            Scanner sc = new Scanner(System.in);
	            UserInput = sc.next();
	            
	            if (!(Array_of_ChargingStations.Available_Source_of_Energy.contains(UserInput))) {
	                throw new IOException("Apologies. You have not selected the available energy resource.  We are proceeding with Diesel based on your vehicle data");
	            }
	        } catch (IOException e) {
	        	UserInput="Diesel";
	        	System.out.println(e.getMessage());
	        	throw new InputMismatchException(" This is an Exception due to invalid user Input.");
	        }
	        return UserInput;
	    }
	  
	 
	 static void CompletePayment() throws IOException					
	    {
	        System.out.println("Hello Customer. How would you like to make payment. Please select option."+ "\n"+ "1. Cash" + "\n" + "2. Apple/Google Pay" + "\n" + "3. Card");

	        Scanner sc=new Scanner(System.in);
	        int Mode_of_Payment= sc.nextInt();
	        
	        switch (Mode_of_Payment) {
	        	case 1:
	        		System.out.println("Kindly pay the amount and take the receipt.");
	                break;
	                
	        	 case 2:
		                System.out.println("Kindly enter your UPI ID and press proceed to continue.");
		                sc=new Scanner(System.in);
		                String UPI_ID=sc.next();
		                if(UPI_ID.length()>13)
	                    {
	                        throw new IOException("Wrong UPI ID is entered.");
	                    }
		                break;
		                
	        	case 3:
	                System.out.println("Kindly insert your card and complete the transaction.");
	                break;
	                
	           
	           default:
	                System.out.println("Selected payment method is invalid");
	        }
	        
}
	 
	    static void ChargeCar() throws Exception							
	    {
	        
	        System.out.println("\nDear Customer! Your car is getting filled with your chosen fuel. Please wait...........\n");
	        try {
	        	CompletePayment();
	        } catch (IOException e) {
	            throw new Exception("Exception occured in proceedforpayment(): "+e.getMessage());
	        }
	    }
    
}