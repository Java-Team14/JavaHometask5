package Home_Assignment2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
	
	@Test
    void testLogFileDoesNotExist() {
        assertTrue(LogManager.logFileExists("unknown_log.txt"));
    }

    @Test
    void testLogFileCreation() {
        LogManager.createSystemFuncLog("Test log message");
        assertTrue(LogManager.logFileExists("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\system_log.txt"));
    }

    @Test
    void testLogFileMove() {
        LogManager.moveLog("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\system_log.txt", "C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\moved_logs\\system_log_moved.txt");
        assertTrue(LogManager.logFileExists("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\moved_logs\\system_log_moved.txt"));
    }
    
    @Test
    void testLogFileArchive() {
        LogManager.archiveLog("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\system_log.txt");
        assertTrue(LogManager.logFileExists("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\archived_logs\\archive_20231120_234220_energy_management_log_moved.txt"));
    }

    @Test
    void testLogFileDeletion() {
        LogManager.deleteLog("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\charging_station_log.txt");
        assertFalse(LogManager.logFileExists("C:\\Users\\MADHU\\eclipse-workspace\\Java_Hometasks\\logs\\created_logs\\charging_station_log.txt"));
    }
}
