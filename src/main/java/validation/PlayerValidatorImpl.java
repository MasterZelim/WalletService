package validation;

public class PlayerValidatorImpl implements PlayerValidator {

    @Override
    public void validationPlayerName(String name) {
        if (name==null||name.isEmpty()) {
            throw new IllegalArgumentException("Имя игрока пусто");
        }
    }
    @Override
    public void validationPlayerPassword(String password) {
        if (password==null||password.length() > 1) {
            throw new IllegalArgumentException("В пароле игрока должно быть >= 8 символов.");
        }
    }
}
