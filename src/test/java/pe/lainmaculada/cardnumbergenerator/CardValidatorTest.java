package pe.lainmaculada.cardnumbergenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class CardValidatorTest {

    @Test
    void testPrivateConstructor() {
        // Test that attempting to instantiate throws an exception
        assertThrows(InvocationTargetException.class, () -> {
            // Use reflection to call private constructor
            java.lang.reflect.Constructor<CreditCardValidator> constructor
                    = CreditCardValidator.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testNullInputValidation() {
        // Test null input handling
        assertThrows(NullPointerException.class, () -> {
            CreditCardValidator.validate(null);
        });

        assertThrows(NullPointerException.class, () -> {
            CreditCardValidator.generateCheckDigit(null);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Valid numbers from various systems
            "9452000000000046",
            "9452000000000038",
            "9452000000000020",
            "9452000000000012"
    })
    void testValidNumbers(String number) {
        assertTrue(CreditCardValidator.validate(number),
                "Number should be valid: " + number);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Invalid numbers
            "9452000000000043",  // One digit off valid number
            "9452000000000039",  // Random invalid number
            "9452000000000021",  // All nines
    })
    void testInvalidNumbers(String number) {
        assertFalse(CreditCardValidator.validate(number),
                "Number should be invalid: " + number);
    }

    @Test
    void testNumbersWithSpaces() {
        // Test numbers with spaces
        assertTrue(CreditCardValidator.validate("4532 0151 2345 6789"),
                "Number with spaces should be valid");
        assertFalse(CreditCardValidator.validate("4532 0151 2345 6788"),
                "Invalid number with spaces should be rejected");
    }

    @Test
    void testInvalidCharacterInput() {
        // Test input with non-digit characters
        assertThrows(IllegalArgumentException.class, () -> {
            CreditCardValidator.validate("45320151234a6789");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            CreditCardValidator.generateCheckDigit("4532015123-4");
        });
    }

    @Test
    void testCheckDigitGeneration() {
        // Test check digit generation for various partial numbers
        String[] partialNumbers = {
                "453201512345678",
                "411111111111111",
                "902400000000000"
        };

        for (String partialNumber : partialNumbers) {
            int checkDigit = CreditCardValidator.generateCheckDigit(partialNumber);

            // Verify that adding the generated check digit creates a valid number
            String fullNumber = partialNumber + checkDigit;
            assertTrue(CreditCardValidator.validate(fullNumber),
                    "Generated check digit should make number valid: " + fullNumber);
        }
    }

    @Test
    void testEdgeCases() {
        // Extremely short number
        assertFalse(CreditCardValidator.validate("1"));
    }

    @Test
    void testPerformance() {
        // Simple performance test to ensure validation is quick
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            CreditCardValidator.validate("4532015123456789");
        }
        long endTime = System.nanoTime();

        // Validate takes less than 100 milliseconds for 10,000 iterations
        assertTrue((endTime - startTime) / 1_000_000 < 100,
                "Validation should be performant");
    }
}
