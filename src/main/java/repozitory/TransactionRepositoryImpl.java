package repozitory;

import config.ConnectionManager;
import model.Account;
import model.Player;
import model.Transaction;
import model.TransactionType;

import java.sql.*;
import java.util.*;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final String SELECT_ID_TRANSACTION = "SELECT id FROM transaction";
    private final String SELECT_BY_ID = "SELECT * FROM player WHERE id = ?";
    private final String INSERT = "INSERT INTO transaction (transaction_type, account_id, amount, date) " +
                                   "VALUES(?,?,?,?)";
    private final String SELECT_LEFT_JOIN = "SELECT transaction.id,transaction_type, account_id,amount,date " +
                                            "FROM transaction LEFT JOIN account ON account.id = account_id WHERE account_id = ?";

    public boolean save(Transaction transaction){
        try(Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT)){
            preparedStatement.setString(1,transaction.getTypeTransaction().name());
            preparedStatement.setLong(2,transaction.getAccount().getId());
            preparedStatement.setFloat(3,transaction.getAmount());
            preparedStatement.setTimestamp(4,transaction.getTimestamp());
            return preparedStatement.execute();

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public Optional<List<Transaction>> getByAccountIdAndPlayerId(Long accountId, Long playerId){

        List<Transaction> transactionHistory = new ArrayList<>();
        Player player = getById(playerId).get();
        try(Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LEFT_JOIN)){
            preparedStatement.setLong(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Long transactionId = resultSet.getLong("id");
                Long Id = resultSet.getLong("account_id");
                TransactionType  transactionType = TransactionType.valueOf(resultSet.getString("transaction_type"));
                float amount = resultSet.getFloat("amount");
                Timestamp timestamp = resultSet.getTimestamp("date");
                Transaction transaction = new Transaction(transactionId,transactionType,new Account(Id,player,amount),amount,timestamp);
                transactionHistory.add(transaction);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return Optional.of(transactionHistory);
    }

    public Optional<Set<Long>> getTransactionUuid(){

        Set<Long> idCompletedTransactions = new HashSet<>();
        try(Connection connection = ConnectionManager.open();
        Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ID_TRANSACTION);
            while (resultSet.next()){
                idCompletedTransactions.add(resultSet.getLong("id"));
            }
            return Optional.of(idCompletedTransactions);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> getById(Long id){
        try(Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                Player player = new Player(id,name,password);
                return Optional.of(player);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
