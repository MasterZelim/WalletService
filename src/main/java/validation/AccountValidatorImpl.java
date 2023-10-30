package validation;

public class AccountValidatorImpl implements AccountValidator {
    @Override
    public void amountValidator(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма должна быть > 0");
        }
    }

}
