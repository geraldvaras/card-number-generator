package pe.lainmaculada.cardnumbergenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CardNumberGenerator {

    private final AtomicLong sequence = new AtomicLong(0);

    @Value("${card.prefix:9452}")
    private String prefix;

    public String generateNext() {
        long nextSequence = sequence.incrementAndGet();
        String baseNumber = String.format(
                "%s%011d",
                prefix,
                nextSequence
        );

        int checkDigit = CreditCardValidator.generateCheckDigit(baseNumber);
        return baseNumber + checkDigit;
    }

}