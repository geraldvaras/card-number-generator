package pe.lainmaculada.cardnumbergenerator;

import java.time.LocalDate;

public record Card(String cardNumber, LocalDate expirationDate,
                   String cvv, CardStatus status) {
}
