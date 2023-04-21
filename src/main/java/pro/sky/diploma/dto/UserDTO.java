package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для всех пользователей зарегистрированных на платформе
 */
@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;
}