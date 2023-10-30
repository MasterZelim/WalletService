package repozitory;

import config.ConnectionManager;
import model.Action;
import model.Audit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuditRepositoryImpl implements AuditRepository{

    private final String INSERT = "INSERT INTO audit (player_id, action, date) VALUES(?,?,?)";
    private final String SELECT_By_Player_Id_Action_LEFT_JOIN = "SELECT audit.id, action, date FROM audit " +
            "LEFT JOIN player ON player_id WHERE player_id = ?";

    public boolean save(Audit audit){
        try(Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
//            preparedStatement.setLong(1, audit.getPlayerID());
//            preparedStatement.setString(2, audit.getAction().name());
//            preparedStatement.setTimestamp(3, audit.getTimestamp());
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<Audit>> getById(Long playerId){

        List<Audit> actions = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
        PreparedStatement  preparedStatement = connection.prepareStatement(SELECT_By_Player_Id_Action_LEFT_JOIN)){
            preparedStatement.setLong(1,playerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                Long auditId = resultSet.getLong("id");
                Action action = Action.valueOf(resultSet.getString("action"));
                Timestamp timestamp = resultSet.getTimestamp("Date");
//                actions.add((new Audit(auditId,playerId,action,timestamp));

            }
            return  Optional.of(actions);

        }catch (SQLException e){

            throw new RuntimeException(e);
        }

    }
}
