package pro.sky.diploma.servicies;
import pro.sky.diploma.dto.RegisterReqDTO;

/**
 * Сервис-интерфейс для авторизации и регистрации пользователей на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AuthService {
    /**
     * Сигнатура метода авторизации пользователей на платформе
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    void login(String username, String password);

    /**
     * Сигнатура метода регистрации новых пользователей на платформе
     *
     * @param registerReq класс-DTO для регистрации пользователя на платформе
     */
    void register(RegisterReqDTO registerReq);
}