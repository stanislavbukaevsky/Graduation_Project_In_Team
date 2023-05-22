package pro.sky.diploma.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entities.User;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех пользователей, зарегистрированных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link User} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Этот метод ищет пользователей по его e-mail, так как электронная почта пользователя соответствует его логину
     *
     * @param username имя пользователя (логин)
     * @return Возвращает найденного по электронной почте пользователя
     */
    Optional<User> findUserByEmail(String username);

    /**
     * Этот метод проверяет, есть ли уже имя пользователя в базе данных
     *
     * @param email имя пользователя (логин)
     * @return Возвращает true, если пользователь уже существует в базе данных или false, если его нет
     */
    Boolean existsUserByEmail(String email);
}