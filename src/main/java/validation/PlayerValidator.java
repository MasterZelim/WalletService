package validation;

public class PlayerValidator {

    public void validationPlayerName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Имя игрока пусто");
        }
    }
    public void validationPlayerPassword(String password) {
        if (password.length() > 1) {
            throw new IllegalArgumentException("В пароле игрока должно быть >= 8 символов.");
        }
    }
}
