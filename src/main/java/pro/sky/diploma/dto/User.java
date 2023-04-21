package pro.sky.diploma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Класс-DTO для всех пользователей зарегистрированных на платформе
 */
@Validated
@Data
public class User {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("regDate")
    private String regDate;
    @JsonProperty("image")
    private String image;
}
