package pro.sky.diploma.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Класс-исключение, если пароль не совпадает с паролем в базе данных. <br>
 * Наследуется от класса {@link AuthenticationException}
 */
public class PasswordNotFoundException extends AuthenticationException {
    public PasswordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotFoundException(String message) {
        super(message);
    }
}