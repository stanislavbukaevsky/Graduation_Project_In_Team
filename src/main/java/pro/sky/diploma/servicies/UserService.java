package pro.sky.diploma.servicies;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.dto.NewPasswordDTO;
import pro.sky.diploma.dto.UserDTO;

import java.io.IOException;

/**
 * Сервис-интерфейс для всех пользователей зарегистрированных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface UserService {
    /**
     * Сигнатура метода для изменения пароля зарегистрированного пользователя на платформе.
     *
     * @param currentPassword текущий пароль
     * @param newPassword     новый пароль
     * @return Возвращает сконвертированную DTO нового пароля пользователя
     */
    NewPasswordDTO setPassword(String currentPassword, String newPassword);

    /**
     * Сигнатура метода для просмотра информации об авторизированном пользователе на платформе
     *
     * @return Возвращает найденного по электронной почте пользователя
     */
    UserDTO getUser();

    /**
     * Сигнатура метода для изменения информации об авторизированном пользователе на платформе.
     *
     * @param userDTO DTO зарегистрированного пользователя
     * @return Возвращает DTO измененного профиля пользователя
     */
    UserDTO updateUser(UserDTO userDTO);

    /**
     * Сигнатура метода для изменения аватарки у авторизированного пользователя на платформе
     *
     * @param imageFile файл изображения
     * @return Возвращает DTO измененного профиля пользователя
     * @throws IOException общий класс исключений ввода-вывода
     */
    UserDTO updateUserImage(MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для получения аватарки у авторизированного пользователя на платформе по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return Возвращает массив байт искомой аватарки
     */
    byte[] getUserImage(Long id);
}