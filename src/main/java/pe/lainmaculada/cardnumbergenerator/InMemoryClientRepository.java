package pe.lainmaculada.cardnumbergenerator;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private static final Map<Long, Client> clients = new ConcurrentHashMap<>();


    @Override
    public Iterable<Client> findAll() {
        return clients.values();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return existsById(id) ? Optional.of(clients.get(id)) : Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return clients.get(id) != null;
    }

    @Override
    public Client save(Client client) {
        clients.put(client.id(), client);
        return client;
    }

    @Override
    public void deleteById(Long id) {
        clients.remove(id);
    }
}
