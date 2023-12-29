package Home_Assignment4;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class CapstoneTest {
	
	@Test
    void testSystemLoggerInitialization() {
        assertTrue(Files.exists(Paths.get("logs" + File.separator + "system_log.txt")));
    }

    @Test
    void testChargingStationLoggerInitialization() {
        assertTrue(Files.exists(Paths.get("logs" + File.separator + "Ionity_log.txt")));
    }
    
    @Test
    void testChargingStationLoggerInitialization1() {
        assertTrue(Files.exists(Paths.get("logs" + File.separator + "FastEnd_log.txt")));
    }

    @Test
    void testChargingStationLoggerInitialization2() {
        assertTrue(Files.exists(Paths.get("logs" + File.separator + "Ladestation_log.txt")));
    }
    
    @Test
    void testInvalidChargingStationLoggerInitialization() {
        assertTrue(Files.exists(Paths.get("logs" + File.separator + "Dortmund_log.txt")));
    }
    
    @Test
    void testInvalidEnergySourceSelection() {
        provideInput("1\nE10\n");
        executeMainAndAssertOutputContains("Charging completed. Thank you!", "Selected charging station: Ionity");
    }    

    @Test
    void testOpeningStationLogFile() {
        provideInput("1\nIonity\n");
        ByteArrayOutputStream fileOutput = executeLogFileMethodAndCaptureOutput("Ionity");

        String fileContent = fileOutput.toString();
        assertTrue(fileContent.contains("Charging started at"));
        assertTrue(fileContent.contains("Charging completed. Thank you!"));
    }

	
	@Test 
	void testOpeningSystemLogFile() { 
		provideInput("system\n");
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
	  
		ByteArrayOutputStream fileOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(fileOutput));
	  
		Capstone.openSystemLogFile();
	  
		String fileContent = fileOutput.toString();
		assertTrue(fileContent.contains("Welcome to the Car Charging Simulator."));
		assertTrue(fileContent.contains("Selected charging station: Ionity")); 
		
	}

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }
    
    private void executeMainAndAssertOutputContains(String... expectedSubstrings) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Capstone.main(null);

        String output = outputStream.toString();
        for (String expectedSubstring : expectedSubstrings) {
            assertTrue(output.contains(expectedSubstring));
        }
    }
    
    private ByteArrayOutputStream executeLogFileMethodAndCaptureOutput(String stationName) {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));

        ByteArrayOutputStream fileOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fileOutput));

        Capstone.openLogFile(stationName);

        return fileOutput;
    }
}
