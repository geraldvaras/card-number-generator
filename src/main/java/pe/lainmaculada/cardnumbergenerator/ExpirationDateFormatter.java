package pe.lainmaculada.cardnumbergenerator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ExpirationDateFormatter {


    private static final DateTimeFormatter CARD_EXPIRATION_FORMATTER =
            DateTimeFormatter.ofPattern("MM/yy");

    /**
     * Formatea la fecha de expiración al formato estándar MM/YY
     *
     * @param expirationDate fecha de expiración
     * @return fecha formateada en formato MM/YY
     */
    public static String formatExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("La fecha de expiración no puede ser nula");
        }
        return expirationDate.format(CARD_EXPIRATION_FORMATTER);
    }

    /**
     * Formatea YearMonth al formato estándar de tarjetas MM/YY
     * Útil cuando solo se trabaja con mes y año
     */
    public static String formatExpirationYearMonth(YearMonth yearMonth) {
        if (yearMonth == null) {
            throw new IllegalArgumentException("El YearMonth no puede ser nulo");
        }
        return yearMonth.format(CARD_EXPIRATION_FORMATTER);
    }

    /**
     * Parsea una fecha en formato MM/YY a LocalDate
     * Establece el día como el último del mes
     */
    public static LocalDate parseExpirationDate(String expirationDate) {
        if (expirationDate == null || !expirationDate.matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use MM/YY");
        }

        String[] parts = expirationDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = 2000 + Integer.parseInt(parts[1]); // Asume años 20XX

        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atEndOfMonth(); // Último día del mes
    }

    /**
     * Valida si una fecha de expiración está vigente
     */
    public static boolean isValidExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !expirationDate.isBefore(today);
    }

}
