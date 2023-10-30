package repozitory;

import config.ConnectionManager;
import model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {

    private final String INSERT = "INSERT INTO player (name,password) VALUES(?,?)";
    private final String SELECT_BY_ID = "SELECT * FROM player WHERE id =  ?";
    private final String SELECT_BY_NAME = "SELECT * FROM player WHERE name = ?";


    public Optional<Player> save(Player player) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, new String[]{"id"})) {

            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long playerId = resultSet.getLong("id");
                return Optional.of(new Player(playerId, player.getName(), player.getPassword()));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Player> getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setString(1, "id");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                Player player = new Player(id, name, password);
                return Optional.of(player);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Player> getByName(String name) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                return Optional.of(new Player(id, name, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
