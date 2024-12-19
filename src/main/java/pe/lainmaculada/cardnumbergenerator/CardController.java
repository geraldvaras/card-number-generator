package pe.lainmaculada.cardnumbergenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {


    @Autowired
    private CardService cardService;


    @PostMapping
    public ResponseEntity<CardDTO> createCard() {
        Card card = cardService.createCardForClient();
        return ResponseEntity.ok(CardMapper.fromEntity(card));
    }

    @GetMapping
    public Iterable<Card> listCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/validate/{cardNumber}")
    public boolean validateCardNumber(@PathVariable  String cardNumber) {
        return CreditCardValidator.validate(cardNumber);
    }
}