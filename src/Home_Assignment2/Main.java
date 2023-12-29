package Home_Assignment2;
import java.io.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

class ChargingStationSimulation {
    private Map<User, Boolean> userQueue;

    public ChargingStationSimulation() {
        this.userQueue = new HashMap<>();
    }

    public void addUserToQueue(User user) {
        userQueue.put(user, true);
        System.out.println("User " + user.getUsername() + " added to the queue.");
    }

    public void removeUserFromQueue(User user) {
        userQueue.remove(user);
        System.out.println("User " + user.getUsername() + " removed from the queue.");
    }

    public void viewUserQueue() {
        System.out.println("\nCurrent User Queue:");
        // List to store users that need to be removed
        List<User> usersToRemove = new ArrayList<>();

        // Iterate through the userQueue entries
        for (Map.Entry<User, Boolean> entry : userQueue.entrySet()) {
            User user = entry.getKey();
            boolean isInQueue = entry.getValue();

            // Check if the user is not in the queue for removal
            if (!isInQueue) {
                usersToRemove.add(user);
            } else {
                System.out.println(user.getUsername());
            }
        }

        // Remove users that are not in queue
        for (User userToRemove : usersToRemove) {
            userQueue.remove(userToRemove);
        }

        if (userQueue.isEmpty()) {
            System.out.println("No users in the queue.\n");
        }
        System.out.println("\n");
    }
    
    public void clearUserFromQueue(User user) {
        if (userQueue.containsKey(user)) {
            userQueue.put(user, false);
            System.out.println("User " + user.getUsername() + " removed from the queue and value set to false.");
        } else {
            System.out.println("User " + user.getUsername() + " not found in the queue.");
        }
    }

    public void checkChargingStationStatus() {
    	System.out.println("\nCharging Station Status:");
    	for (Map.Entry<User, Boolean> entry : userQueue.entrySet()) {
            User user = entry.getKey();
            boolean isInQueue = entry.getValue();

            System.out.println("User: " + user.getUsername() + ", In Queue: " + isInQueue);
        }

        if (userQueue.isEmpty()) {
            System.out.println("No users in the queue.");
        }
    }
}

class ExternalUser extends User {
    public ExternalUser(String username) {
        super(username);
    }

    public void bookTimeSlot(ChargingStationSimulation chargingStationSimulation) {
    	chargingStationSimulation.addUserToQueue(this);
        System.out.println("External user " + getUsername() + " booked a time slot.");
    }
}

class Administrator extends User {
    public Administrator(String username) {
        super(username);
    }

    public void manageAdminAccess(ChargingStationSimulation chargingStationSimulation) {
    	chargingStationSimulation.addUserToQueue(this);
    	chargingStationSimulation.checkChargingStationStatus();
    	chargingStationSimulation.viewUserQueue();
    }
}


public class Main {
	
	//--------Byte and Character Stream--------
	private static final String BYTE_STREAM = "C:\\Users\\MADHU\\Documents\\input_byte.txt";
    private static final String CHAR_STREAM = "C:\\Users\\MADHU\\Documents\\read_char.txt";
    
    private static void writeByteStream() {
        try (PrintWriter writer = new PrintWriter(BYTE_STREAM)) {
            User user1 = new User("User 1");
            User user2 = new User("User 2");

            writer.println(user1.getUsername() + " " + user2.getUsername());

            System.out.println("Byte Stream written successfully.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCharStream() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CHAR_STREAM))) {
            writer.write("Line 2 : Character streams for simulation of the data exchange");
            writer.newLine();
            writer.write("Line 3 : in the system.");

            System.out.println("Character Stream written successfully.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void readByteStream() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BYTE_STREAM))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("\nRead from Byte Stream: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readCharStream() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CHAR_STREAM))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read from Character Stream: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	ChargingStationSimulation chargingStationSimulation = new ChargingStationSimulation();
        ExternalUser externalUser1 = new ExternalUser("ExternalUser1");
        ExternalUser externalUser2 = new ExternalUser("ExternalUser2");
        ExternalUser externalUser3 = new ExternalUser("ExternalUser3");
        Administrator administrator1 = new Administrator("Admin1");
        ExternalUser externalUser4 = new ExternalUser("ExternalUser4");
        Administrator administrator2 = new Administrator("Admin2");

        externalUser1.bookTimeSlot(chargingStationSimulation);
        externalUser2.bookTimeSlot(chargingStationSimulation);
        administrator1.manageAdminAccess(chargingStationSimulation);
        chargingStationSimulation.clearUserFromQueue(externalUser1);
        chargingStationSimulation.clearUserFromQueue(externalUser2);
        externalUser3.bookTimeSlot(chargingStationSimulation);
        externalUser4.bookTimeSlot(chargingStationSimulation);
        administrator2.manageAdminAccess(chargingStationSimulation);
        
        
        //--------Byte and Character Stream--------
        // Simulation using byte stream (ObjectOutputStream)
        readByteStream();
        writeByteStream();

        // Simulation using character stream (BufferedWriter)
        readCharStream();
        writeCharStream();
        
        
        
        //--------Metadata of the project--------
        Path file = Paths.get("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks");
        try {
            // Read file attributes
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

            // Print file attributes
            System.out.println("\n");
            System.out.println("------Metadata of the project------");
            System.out.println("creationTime: " + attr.creationTime());
            System.out.println("lastAccessTime: " + attr.lastAccessTime());
            System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
            System.out.println("isDirectory: " + attr.isDirectory());
            System.out.println("isOther: " + attr.isOther());
            System.out.println("isRegularFile: " + attr.isRegularFile());
            System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
            System.out.println("size: " + attr.size());
            System.out.println("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        // --------Managing the log files--------
        // Create
        LogManager.createSystemFuncLog("Details of the system logs : ");
        LogManager.createChargingStationFuncLog("Details of the charging station logs : ");
        LogManager.createEnergyManagementFuncLog("Details of energy management logs : ");

        // Move
        LogManager.moveLog("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\system_log.txt", "C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\moved_logs\\system_log_moved.txt");

        // Delete
        LogManager.deleteLog("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\charging_station_log.txt");

        // Archive
        LogManager.archiveLog("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\energy_management_log.txt");       
        
    }
}
