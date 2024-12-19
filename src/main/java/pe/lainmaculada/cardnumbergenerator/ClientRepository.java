package pe.lainmaculada.cardnumbergenerator;

import java.util.Optional;

public interface ClientRepository {
    Iterable<Client> findAll();
    Optional<Client> findById(Long id);
    boolean existsById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
