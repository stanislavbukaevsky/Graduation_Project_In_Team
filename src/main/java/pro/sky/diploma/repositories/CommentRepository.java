package pro.sky.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entities.Comment;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех комментариев, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Comment} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Этот метод ищет комментарии по его идентификатору
     *
     * @param id идентификатор комментария
     * @return Возвращает найденный комментарий по его идентификатору
     */
    Optional<Comment> findCommentById(Long id);

    /**
     * Этот метод ищет комментарии по идентификатору объявления и по идентификатору комментария
     *
     * @param adId      идентификатор объявления
     * @param commentId идентификатор комментария
     * @return Возвращает найденный комментарий по идентификатору объявления и комментария
     */
    Optional<Comment> findAdsByIdAndId(Long adId, Long commentId);
}