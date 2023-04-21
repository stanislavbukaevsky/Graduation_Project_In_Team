package pro.sky.diploma.exceptions;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если пользователь не найден в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}