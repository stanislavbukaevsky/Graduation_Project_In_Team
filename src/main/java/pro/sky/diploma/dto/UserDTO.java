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

       public User toUser() {
           User user = new User();
           user.setId(this.getId());
           user.setFirstName(this.getFirstName());
           user.setLastName(this.getLastName());
           return user;
       }
}
