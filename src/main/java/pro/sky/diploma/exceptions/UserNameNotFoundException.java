package pro.sky.diploma.exceptions;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если пользователь не найден в базе данных по его логину. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class UserNameNotFoundException extends NoSuchElementException {
    public UserNameNotFoundException() {
    }

    public UserNameNotFoundException(String message) {
        super(message);
    }
}