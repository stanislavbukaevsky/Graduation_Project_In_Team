package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для регистрации пользователя на платформе
 */
@Data
public class RegisterReqDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
