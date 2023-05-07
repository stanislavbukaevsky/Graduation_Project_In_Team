package pro.sky.diploma.exception;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если комментарий не найден в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException() {
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}