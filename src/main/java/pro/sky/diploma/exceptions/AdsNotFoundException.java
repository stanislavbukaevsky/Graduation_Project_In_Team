package pro.sky.diploma.exceptions;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если объявление не найдено в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class AdsNotFoundException extends NoSuchElementException {

    public AdsNotFoundException() {
    }

    public AdsNotFoundException(String message) {
        super(message);
    }
}
