package Home_Assignment2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
	private static final String SYSTEM_FUNCTIONALITY_LOG = "C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\";
    private static final String CHARGING_STATION_FUNCTIONALITY_LOG = "C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\";
    private static final String ENERGY_MANAGEMENT_FUNCTIONALITY_LOG = "C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\";
    private static final String ARCHIVED_LOG = "C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\archived_logs\\";
    
    public static void createSystemFuncLog(String content) {
        createLog(SYSTEM_FUNCTIONALITY_LOG + "system_log.txt", content);
        createLog(SYSTEM_FUNCTIONALITY_LOG + "system_log1.txt", content);
    }

    public static void createChargingStationFuncLog(String content) {
        createLog(CHARGING_STATION_FUNCTIONALITY_LOG + "charging_station_log.txt", content);
    }

    public static void createEnergyManagementFuncLog(String content) {
        createLog(ENERGY_MANAGEMENT_FUNCTIONALITY_LOG + "energy_management_log.txt", content);
    }

    private static void createLog(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println("Log file created successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveLog(String sourceFileName, String destinationFileName) {
        Path sourcePath = Paths.get(sourceFileName);
        Path destinationPath = Paths.get(destinationFileName);

        try {
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Log file moved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to move the log file.");
        }
    }

    public static void deleteLog(String fileName) {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Log file deleted successfully.");
        } else {
            System.out.println("Failed to delete the log file.");
        }
    }

    public static void archiveLog(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                Path sourcePath = Paths.get(fileName);
                Path archivePath = Paths.get(ARCHIVED_LOG + generateArchiveFileName(fileName));

                Files.move(sourcePath, archivePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Log file archived successfully: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to archive the log file.");
            }
        } else {
            System.out.println("Log file not found for archiving: " + fileName);
        }
    }

    private static String generateArchiveFileName(String originalFileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return "archive_" + timestamp + "_" + new File(originalFileName).getName();
    	//return "archive_" + new File(originalFileName).getName();
    }
    
    public static boolean logFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}

