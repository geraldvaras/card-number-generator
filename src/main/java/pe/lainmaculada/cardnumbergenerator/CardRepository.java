package pe.lainmaculada.cardnumbergenerator;

import java.util.Optional;

public interface CardRepository {

    Iterable<Card> findAll();
    Optional<Card> findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);
    Card save(Card card);
    void deleteByCardNumber(String cardNumber);
}
