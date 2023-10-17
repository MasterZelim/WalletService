package service;

import model.Action;
import model.Player;
import repozitory.AuthorizationRepository;
import validation.PlayerValidator;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
public class AuthorizationService {


    private final LoggerService loggerService = new LoggerService();
    private final AuditService auditService;
    private final PlayerValidator playerValidator;
    private boolean name;
    public AuthorizationService(AuditService auditService, PlayerValidator PlayerValidator) {
        this.auditService = auditService;
        this.playerValidator = PlayerValidator;

    }
    public Player logIn(String login, String password)  {
        playerValidator.validationPlayerName(login);
        playerValidator.validationPlayerPassword(password);
        Player player = null;
        try {
            player = AuthorizationRepository.retrievePlayer(login);
        }catch (SQLException e){
            throw new RuntimeException();
        }

        if (player == null) {
            throw new IllegalArgumentException("Игрок с таким логином не найден " + login);
        }
        if (!player.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неправильный пароль " + login);
        }
        loggerService.info(Action.AUTHORIZATION);
        auditService.saveAuditUserHistory(login, Action.AUTHORIZATION);
        name = true;
        return player;
    }
    public Player registrationPlayer(String login, String password) { //проверка на null аргументов нужна.
        playerValidator.validationPlayerName(login);
        playerValidator.validationPlayerPassword(password);
        Player player = null;

        try {
            AuthorizationRepository.createTable();

            player = AuthorizationRepository.retrievePlayer(login);

            if (player != null) {
                throw new IllegalArgumentException("Игрок с таким именем пользователя уже существует. Пожалуйста, используйте другое имя пользователя.");
            }
            AuthorizationRepository.insertRecord(login,password);
            player = AuthorizationRepository.retrievePlayer(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        loggerService.info(Action.REGISTRATION);
        auditService.saveAuditUserHistory(login, Action.REGISTRATION);
        return player;
    }
    public void logout() {
        name = false;
        loggerService.info(Action.COMPLETION_WORK);
    }
}
