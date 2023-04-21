package pro.sky.diploma.service;

import pro.sky.diploma.dto.NewPassword;
import pro.sky.diploma.dto.RegisterReq;
import pro.sky.diploma.dto.Role;


public interface AuthService {
    /**
     * Метод авторизации пользователя.
     *
     * @param userName email пользователя
     * @param password пароль пользователя
     * @return - boolean
     */
    boolean login(String userName, String password);

    /**
     * Метод регистрации пользователя.
     *
     * @param registerReq данные регистрационной формы
     * @param role
     * @return - boolean
     */
    boolean register(RegisterReq registerReq, Role role);

    /**
     * Метод смены пароля пользователя.
     *
     * @param body тело запроса
     * @return - boolean
     */
    boolean changePassword(NewPassword body);
}