package pro.sky.diploma.dto;

import lombok.Data;
import pro.sky.diploma.entities.Image;
import pro.sky.diploma.entities.User;

/**
 * Класс-DTO для всех пользователей зарегистрированных на платформе
 */
@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Image image;

    /**
     * Создаёт {@link UserDTO} из {@link User}
     * @param user Пользователь на входе
     * @return возвращает DTO пользователя
     */
    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhoneNumber());
        userDTO.setImage(user.getImage());
        return userDTO;
    }

    /**
     * Создаёт объект {@link User} из текущего {@link UserDTO}
     * @return {@link UserDTO}
     */
       public User toUser() {
           User user = new User();
           user.setId(this.getId());
           user.setFirstName(this.getFirstName());
           user.setLastName(this.getLastName());
           return user;
       }
}
