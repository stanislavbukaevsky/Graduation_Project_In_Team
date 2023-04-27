package pro.sky.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entities.Comment;

/**
 * Интерфейс-репозиторий для работы с методами всех комментариев, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Comment} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
