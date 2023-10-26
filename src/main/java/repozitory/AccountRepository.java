package repozitory;

import config.ConnectionManager;
import model.Account;
import model.Player;
import java.sql.*;
import java.util.Optional;

public class AccountRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "1";

    private final String SELECT_BY_player_Id_LEFT_JOIN = "SELECT account.id, account.player_id, account.balance, " +
            "player.name, player.password FROM account LEFT JOIN player ON player.id = player_id WHERE player_id = ?";

    private final String SELECT_BY_PLAYER_NAME_LEFT_JOIN = "SELECT account.id, account.player_id, account.balance, " +
            "player.name, player.password FROM account LEFT JOIN player ON player.id = player_id WHERE player.name = ?";
    private final String INSERT_ACCOUNT = "INSERT INTO account(player_id,balance) VALUES(?,?)";
    private final String UPDATE_BALANCE = "UPDATE account SET balance = ? WHERE id = ?";


        public Optional<Account> getAccountById(long playerId){
            try( Connection connection = ConnectionManager.open();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_player_Id_LEFT_JOIN)) {
                     preparedStatement.setLong(1,playerId);
                     ResultSet resultSet = preparedStatement.executeQuery();
                     if (resultSet.next()){
                         Long accountId = resultSet.getLong("id");
                         float balance = resultSet.getFloat("balance");
                         String name = resultSet.getString("name");
                         String password = resultSet.getString("password");
                         return Optional.of(new Account(accountId,new Player(playerId,name,password), balance));
                     }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }

    public Optional<Account> getAccountByName(String name){

            try (Connection connection = ConnectionManager.open();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PLAYER_NAME_LEFT_JOIN)) {

                preparedStatement.setString(1,name);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    Long id = resultSet.getLong("id");
                    Long playerId = resultSet.getLong("player_id");
                    float balance = resultSet.getFloat("balance");
                    String PlayerName = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    return Optional.of(new Account(id,new Player(playerId,PlayerName,password), balance));

                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            return Optional.empty();
    }

    public boolean save(Account account){

            try(Connection connection = ConnectionManager.open();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT)) {

                preparedStatement.setLong(1,account.getPlayer().getId());
                preparedStatement.setFloat(2,account.getBalance());
                return preparedStatement.executeUpdate()>0;

            }catch (SQLException e){
                throw new RuntimeException();
            }
    }

    public float getCurrentBalance(Long id){

            try(Connection connection = ConnectionManager.open();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_player_Id_LEFT_JOIN)) {
                preparedStatement.setLong(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    return resultSet.getFloat("balance");

                }

            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            return 0;
    }

    public boolean saveCurrentBalance(Account account){

            try (Connection connection = ConnectionManager.open();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE)){
                preparedStatement.setFloat(1,account.getBalance());
                preparedStatement.setFloat(2,account.getId());
                return preparedStatement.executeUpdate()>0;

            }catch (SQLException e){
                throw new RuntimeException(e);
            }
    }
}
