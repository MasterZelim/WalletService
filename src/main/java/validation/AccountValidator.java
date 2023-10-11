package validation;

public class AccountValidator {

    public void amountValidation(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма должна быть > 0");
        }
    }
}
