package pe.lainmaculada.cardnumbergenerator;

import java.util.Objects;

public final class CreditCardValidator {

    /**
     * Private constructor to prevent instantiation of utility class.
     * @throws IllegalStateException if someone attempts to create an instance
     */

    private CreditCardValidator() {
        throw new IllegalStateException(this.getClass().getName()+" class cannot be instantiated");
    }

    /**
     * Validates a number using the Luhn algorithm.
     *
     * @param number The number to validate as a string
     * @return boolean indicating whether the number passes Luhn validation
     * @throws IllegalArgumentException if input is null or contains non-digit characters
     */
    public static boolean validate(String number) {
        // Validate input
        Objects.requireNonNull(number, "Input number cannot be null");

        // Remove any whitespace and validate characters
        String cleanedNumber = number.replaceAll("\\s", "");
        if (!cleanedNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Input must contain only digits");
        }

        // Convert to char array for processing
        char[] digits = cleanedNumber.toCharArray();

        // Luhn algorithm calculation
        int sum = 0;
        boolean isEvenIndex = false;

        for (int i = digits.length - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(digits[i]);

            if (isEvenIndex) {
                digit *= 2;
                // If doubled digit is two digits, sum its digits
                if (digit > 9) {
                    digit = digit - 9;
                }
            }

            sum += digit;
            isEvenIndex = !isEvenIndex;
        }

        // Number is valid if sum is divisible by 10
        return (sum % 10 == 0);
    }

    /**
     * Generates a check digit for a given partial number using Luhn algorithm.
     *
     * @param partialNumber The number without check digit
     * @return The check digit that makes the full number valid
     * @throws IllegalArgumentException if input is invalid
     */
    public static int generateCheckDigit(String partialNumber) {
        // Validate input
        Objects.requireNonNull(partialNumber, "Partial number cannot be null");

        // Remove any whitespace and validate characters
        String cleanedNumber = partialNumber.replaceAll("\\s", "");
        if (!cleanedNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Input must contain only digits");
        }

        // Calculate check digit
        for (int checkDigit = 0; checkDigit < 10; checkDigit++) {
            String fullNumber = cleanedNumber + checkDigit;
            if (validate(fullNumber)) {
                return checkDigit;
            }
        }

        throw new IllegalStateException("Could not generate valid check digit");
    }
}