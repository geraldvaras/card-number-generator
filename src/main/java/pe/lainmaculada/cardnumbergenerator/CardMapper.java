package pe.lainmaculada.cardnumbergenerator;

import static pe.lainmaculada.cardnumbergenerator.ExpirationDateFormatter.formatExpirationDate;

public class CardMapper {


    public static CardDTO fromEntity(Card card) {
        return new CardDTO(card.cardNumber(), formatExpirationDate(card.expirationDate()), card.status());

    }

    private static String maskCardNumber(String cardNumber) {
        return "****-****-****-" + cardNumber.substring(12);
    }

}
