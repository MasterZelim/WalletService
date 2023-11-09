package service;

import model.Action;
import model.Audit;
import model.Player;
import repozitory.PlayerRepository;
import validation.PlayerValidator;

public class AuthorizationServiceImpl implements AuthorizationService{

    private final LoggerService loggerService;
    private final AuditService auditService;
    private final PlayerValidator playerValidator;
    private final PlayerRepository playerRepository;
    private boolean login;

    public AuthorizationServiceImpl(AuditService auditService, PlayerValidator playerValidator,
                                    LoggerService loggerService, PlayerRepository playerRepository) {
        this.auditService = auditService;
        this.playerValidator = playerValidator;
        this.loggerService = loggerService;
        this.playerRepository = playerRepository;

    }
    @Override
    public Player logIn(String name, String password) {
        playerValidator.validationPlayerName(name);
        playerValidator.validationPlayerPassword(password);

        Player player = playerRepository.getByName(name).orElseThrow(
                () -> new IllegalArgumentException("Игрок с таким логином не найден " + name)
        );

        if (!player.getPassword().equals(password)){

            throw new IllegalArgumentException("Неправильный пароль " + name);
        }

        loggerService.info(Action.LOGGED_IN);
        auditService.saveAuditUserHistory(new Audit(player.getId(), Action.LOGGED_IN));
        login = true;
        return  player;

    }

    @Override
    public Player playerRegister(String name,String password){

        playerValidator.validationPlayerName(name);
        playerValidator.validationPlayerPassword(password);
        if (playerRepository.getByName(name).isPresent()){
            throw  new IllegalArgumentException("Игрок с таким именем пользователя уже существует. " +
                    "Пожалуйста, используйте другое имя пользователя");
        }
        Player newPlayer=new Player(name,password);
        newPlayer = playerRepository.save(newPlayer).get();

        loggerService.info(Action.REGISTRATION);
        auditService.saveAuditUserHistory(new Audit(newPlayer.getId(),Action.REGISTRATION));
        return newPlayer;
    }

    @Override
    public boolean logout(Player player){
        login = false;
        System.out.println("Вы вышли из системы");
        auditService.saveAuditUserHistory(new Audit(player.getId(), Action.LOGGED_OUT_ACCOUNT));
        return login;
    }

}
