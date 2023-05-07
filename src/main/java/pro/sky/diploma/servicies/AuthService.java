package pro.sky.diploma.servicies;

import pro.sky.diploma.dto.RegisterReqDTO;
import pro.sky.diploma.dto.Role;

/**
 * Сервис-интерфейс для авторизации и регистрации пользователей на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AuthService {
    /**
     * Сигнатура метода авторизации пользователей на платформе
     *
     * @param userName имя пользователя
     * @param password пароль пользователя
     * @return Возвращает авторизированного пользователя, если такой существует
     */
    boolean login(String userName, String password);

    /**
     * Сигнатура метода регистрации новых пользователей на платформе
     *
     * @param registerReq класс-DTO для регистрации пользователя на платформе
     * @param role        роль пользователя
     * @return Возвращает зарегистрированного пользователя
     */
    boolean register(RegisterReqDTO registerReq, Role role);
}
