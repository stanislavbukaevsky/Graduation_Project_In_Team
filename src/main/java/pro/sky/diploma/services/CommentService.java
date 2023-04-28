package pro.sky.diploma.services;

import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.exceptions.CommentNotFoundException;

/**
 * Сервис-интерфейс для всех комментариев, опубликованных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface CommentService {
    /**
     * Сигнатура метода для удаления комментария, опубликованного на платформе.
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @return Возвращает DTO удаленного комментария
     */
    CommentDTO deleteComment(Integer adId, Integer commentId);

    /**
     * Сигнатура метода для изменения комментария, опубликованного на платформе.
     * Этот метод может выбросить исключение {@link CommentNotFoundException}, если комментарий не найден
     *
     * @param adId       идентификатор объявления
     * @param commentDTO DTO комментария
     * @param commentId  идентификатор комментария
     * @return Возвращает DTO измененного комментария
     */
    CommentDTO updateComment(Integer adId, CommentDTO commentDTO, Integer commentId);
}
