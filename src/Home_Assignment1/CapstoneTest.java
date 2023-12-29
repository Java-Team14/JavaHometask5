package Home_Assignment1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow; 

public class CapstoneTest {

    @Test
    void testSystemLoggerInitialization() {
        assertFalse(Files.exists(Paths.get("logs" + File.separator + "system_log.txt")));
    }

    @Test
    void testChargingStationLoggerInitialization() {
        assertTrue(Files.exists(Paths.get("logs" + File.separator + "Ionity_log.txt")));
    }

    @Test
    void testSelectEnergySourceThrowsIOException() {
        provideInput("CNG\n");
        assertThrows(IOException.class, () -> Capstone.selectEnergySource(new ChargingStations("Ionity", 4, "CNG", "Petrol")));
    }

    @Test
    void testChargeCarThrowsException() {
        provideInput("1\n");
        assertThrows(Exception.class, Capstone::ChargeCar);
    }

    @Test
    void testCompletePaymentWithValidInput() {
        provideInput("1\n");
        assertDoesNotThrow(() -> Capstone.CompletePayment());
    }

    @Test
    void testCompletePaymentWithInvalidUPI() {
        provideInput("2\nwrongUPI123\n");
        IOException exception = assertThrows(IOException.class, () -> Capstone.CompletePayment());
        assertEquals("Wrong UPI ID is entered.", exception.getMessage());
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }
}

