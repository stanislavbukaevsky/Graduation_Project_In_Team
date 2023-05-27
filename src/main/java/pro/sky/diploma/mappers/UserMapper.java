package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.NewPasswordDTO;
import pro.sky.diploma.dto.UserDTO;
import pro.sky.diploma.entities.User;

/**
 * Маппер-интерфейс, который преобразует сущность всех пользователей, зарегистрированных на платформе {@link User}
 * в DTO {@link UserDTO}
 */
@Mapper
public interface UserMapper {
    /**
     * Этот метод конвертирует сущность пользователей в DTO пользователей.
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param user сущность пользователя
     * @return Возвращает сконвертированную DTO пользователя, зарегистрированного на платформе
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "user", target = "image")
    UserDTO importEntityToDTO(User user);

    /**
     * Этот метод конвертирует сущность пользователя в DTO изменения пароля пользователя.
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param user сущность пользователя
     * @return Возвращает сконвертированную DTO нового пароля пользователя, зарегистрированного на платформе
     */
    @Mapping(source = "password", target = "newPassword")
    NewPasswordDTO importVariablesToDTO(User user);

    /**
     * Этот метод указывает путь к изображению
     *
     * @param user сущность пользователя
     * @return Возвращает ссылку на изображение в строковом виде
     */
    default String importEntityToStringLink(User user) {
        return "/users/avatars/" + user.getId();
    }
}
