package pe.lainmaculada.cardnumbergenerator;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCardNumberRepository implements CardRepository {

    private static final Map<String, Card> cards = new ConcurrentHashMap<>();

    @Override
    public Iterable<Card> findAll() {
        return cards.values();
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) {
        return existsByCardNumber(cardNumber) ? Optional.of(cards.get(cardNumber)) : Optional.empty();
    }

    @Override
    public boolean existsByCardNumber(String cardNumber) {
        return cards.containsKey(cardNumber);
    }

    @Override
    public Card save(Card card) {
         cards.put(card.cardNumber(), card);
         return card;
    }

    @Override
    public void deleteByCardNumber(String cardNumber) {
        cards.remove(cardNumber);
    }
}
