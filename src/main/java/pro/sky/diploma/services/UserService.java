package pro.sky.diploma.services;

import pro.sky.diploma.dto.NewPasswordDTO;
import pro.sky.diploma.dto.UserDTO;
import pro.sky.diploma.entities.User;

/**
 * Сервис-интерфейс для всех пользователей зарегистрированных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface UserService {
    /**
     * Сигнатура метода для изменения пароля зарегистрированного пользователя на платформе.
     *
     * @param email           имя пользователя (логин)
     * @param currentPassword текущий пароль
     * @param newPassword     новый пароль
     * @return Возвращает сконвертированную DTO нового пароля пользователя
     */
    NewPasswordDTO setPassword(String email, String currentPassword, String newPassword);

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
