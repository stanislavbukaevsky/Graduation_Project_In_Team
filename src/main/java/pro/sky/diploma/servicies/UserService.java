package pro.sky.diploma.servicies;

import pro.sky.diploma.dto.UserDTO;
import pro.sky.diploma.entities.User;

/**
 * Сервис-интерфейс для всех пользователей зарегистрированных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface UserService {
    /**
     * Сигнатура метода для просмотра информации об авторизированном пользователе на платформе
     *
     * @param username имя пользователя (логин)
     * @return Возвращает найденного по электронной почте пользователя
     */
    User getUser(String username);

    /**
     * Реализация метода для изменения информации об авторизированном пользователе на платформе.
     *
     * @param userDTO DTO зарегистрированного пользователя
     * @return Возвращает DTO измененного профиля пользователя
     */
    UserDTO updateUser(UserDTO userDTO);
}
