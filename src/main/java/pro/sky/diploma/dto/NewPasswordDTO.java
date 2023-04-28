package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для смены пароля доступа на платформу
 */
@Data
public class NewPasswordDTO {
    private String currentPassword;
    private String newPassword;
}
