package pe.lainmaculada.cardnumbergenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;

@Service
public class CardService {

    private static final int CARD_PREFIX = 9452;
    private static final int EXPIRATION_YEARS = 20;


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardNumberGenerator cardNumberGenerator;

    public Card createCardForClient() {
        String cardNumber = generateUniqueCardNumber();
        String cvv = generateCVV();
        LocalDate expirationDate = calculateExpirationDate();
        Card card  = new Card(cardNumber, expirationDate, cvv, CardStatus.ACTIVE);
        return cardRepository.save(card);
    }

    public Iterable<Card> getAllCards() {
        return cardRepository.findAll();
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do {
            cardNumber = cardNumberGenerator.generateNext();
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    private String generateCVV() {
        return String.format("%03d", new SecureRandom().nextInt(1000));
    }

    private LocalDate calculateExpirationDate() {
        return LocalDate.now().plusYears(EXPIRATION_YEARS)
                .withDayOfMonth(1).plusMonths(1).minusDays(1);
    }
}
