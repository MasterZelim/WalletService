package repozitory;

import model.Player;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> save(Player player);
    Optional<Player> getById(Long id);
    Optional<Player> getByName(String name);
}
