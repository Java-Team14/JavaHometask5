package Home_Assignment4;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

class InvalidUserInputException extends Exception {
    public InvalidUserInputException(String message) {
        super(message);
    }
}

class ChargingStations {
    String name_of_Charging_Station;
    int count_Charging_locations;
    int number_of_Free_stations_available;
    List<String> Available_Source_of_Energy;
    Logger stationLogger;

    public ChargingStations(String name, int no_of_charging_location, String... type) {
        this.name_of_Charging_Station = name;
        this.count_Charging_locations = no_of_charging_location;
        this.Available_Source_of_Energy=new ArrayList<>();
        
        for(String t: type)
        {
             this.Available_Source_of_Energy.add(t);
        }
        
        this.number_of_Free_stations_available = no_of_charging_location - 1;
		
        // Initializing logger for the charging stations and energy source
        stationLogger = Logger.getLogger(name);
        try {
            Handler fileHandler = new FileHandler("logs" + File.separator + name + "_log.txt",1024 * 1024 * 10, 1, true); 
            fileHandler.setFormatter(new SimpleFormatter());
            stationLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
		 
    }
}

public class Capstone {
    private static final Logger systemLogger = Logger.getLogger("System");
    private static final Logger dieselLogger = Logger.getLogger("Diesel");
    private static final Logger petrolLogger = Logger.getLogger("Petrol");
    private static final Logger cngLogger = Logger.getLogger("CNG");

    private static final Scanner scanner = new Scanner(System.in);

    static {
        // Initializing system logger
        try {
            Handler fileHandler = new FileHandler("logs" + File.separator + "system_log.txt", 1024 * 1024 * 10, 1, true);
            fileHandler.setFormatter(new SimpleFormatter());
            systemLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChargingStations Ionity = new ChargingStations("Ionity", 4, "Diesel", "Petrol");
        ChargingStations FastEnd = new ChargingStations("FastEnd", 6, "Diesel", "CNG");
        ChargingStations Ladestation = new ChargingStations("Ladestation", 7, "CNG", "Petrol");
        ChargingStations Vattenfall = new ChargingStations("Vattenfall", 8, "Diesel", "Petrol", "CNG");

        ChargingStations csarray[] = {Ionity, FastEnd, Ladestation, Vattenfall};

        System.out.println("Welcome to the Car Charging Simulator. Select a charging station:\n");
        int selected_station = 0;
        ChargingStationFinder(csarray);
        System.out.print("\nEnter your choice of station: ");
        try {
            int userChoice = scanner.nextInt();
            if (userChoice < 1 || userChoice > csarray.length) {
                throw new InvalidUserInputException("Invalid choice. Defaulting to option 1.");
            }
            selected_station = userChoice - 1;
        } catch (InvalidUserInputException | java.util.InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
            systemLogger.log(Level.SEVERE, "Invalid user input", e);
        }

        ChargingStations selectedStation = csarray[selected_station];
        systemLogger.log(Level.INFO, "Selected charging station: " + selectedStation.name_of_Charging_Station);

        // Initializing logger for the charging station
        ChargingStations station = csarray[selected_station];
        station.stationLogger = Logger.getLogger(station.name_of_Charging_Station);
        try {
            Handler fileHandler = new FileHandler(station.name_of_Charging_Station + "_log.txt");
            fileHandler.setFormatter(new SimpleFormatter());
            station.stationLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.print("Enter the energy resource (Diesel, Petrol, CNG): ");
        String selected_E_source = selectEnergySource(selectedStation);
        systemLogger.log(Level.INFO, "Selected energy source: " + selected_E_source);

        System.out.println("You have " + selectedStation.number_of_Free_stations_available +
                " free charging slots available for charging with " + selected_E_source);

        try {
            ChargeCar(selectedStation, selected_E_source);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            systemLogger.log(Level.SEVERE, "Charging error", e);
        }
        
        try {
            System.out.println("Enter the station name to open its log file or Enter 'system' to open the system log file:");
            String stationName = scanner.next();
            
            if (stationName.equalsIgnoreCase("system")) {
				openSystemLogFile();
            } else {
                openLogFile(stationName);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // Closing scanner to prevent resource leak
        scanner.close();        
    }

    static void ChargingStationFinder(ChargingStations Chargingstationsarray[]) {
        DisplayMenu();
    }

    public static void DisplayMenu() {
        System.out.println("Please enter between 1-4 based on available charging stations:\n"
                + "1. Ionity\n"
                + "2. FastEnd\n"
                + "3. Ladestation\n"
                + "4. Vattenfall");
    }

    static String selectEnergySource(ChargingStations chargingStation) {
        System.out.println("Available energy sources for " + chargingStation.name_of_Charging_Station + ": "
                + chargingStation.Available_Source_of_Energy);
        System.out.print("Enter your choice: ");
        String userInput = scanner.next();
        if (!chargingStation.Available_Source_of_Energy.contains(userInput)) {
            systemLogger.log(Level.WARNING, "Invalid energy source selected: " + userInput);
        }
        return userInput;
    }

    static void ChargeCar(ChargingStations chargingStation, String energySource) throws InterruptedException {
        System.out.println("Charging started at " + new Date() + " for station " +
                chargingStation.name_of_Charging_Station + " with energy source " + energySource);

        // Simulating charging time
        Thread.sleep(3000);

        System.out.println("Charging completed. Thank you!");

        String logMessage = "Charging completed at " + new Date() +
                " for station " + chargingStation.name_of_Charging_Station +
        			" with energy source " + energySource;
        chargingStation.stationLogger.log(Level.INFO, logMessage);

        // Information on energy source logs
        if ("Diesel".equals(energySource)) {
            dieselLogger.log(Level.INFO, logMessage);
        } else if ("Petrol".equals(energySource)) {
            petrolLogger.log(Level.INFO, logMessage);
        } else if ("CNG".equals(energySource)) {
            cngLogger.log(Level.INFO, logMessage);
        }
    }
    
    public static void openLogFile(String stationName) {
        try {
        	String logFileName = stationName + "_log.txt";
            String logFilePath = "logs" + File.separator + logFileName;
            Desktop.getDesktop().open(new File(logFilePath));
        } catch (IOException e) {
            systemLogger.log(Level.SEVERE, "Error opening log file: " + e.getMessage());
        }
    }

    public static void openSystemLogFile() {
        try {
        	String systemLogFilePath = "logs" + File.separator + "system_log.txt";
        	Desktop.getDesktop().open(new File(systemLogFilePath));
        } catch (IOException e) {
            log(systemLogger, Level.SEVERE, "Error opening log file: " + e.getMessage());
        }
    }
    
    private static void log(Logger logger, Level level, String message) {
        logger.log(level, message);
    }
    
}