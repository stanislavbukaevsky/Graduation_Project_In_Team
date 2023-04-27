package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.image.filePath", target = "image")
    UserDTO importEntityToDTO(User user);
}
