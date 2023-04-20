package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO с полной контактной информацией о пользователе, размещающим объявление
 */
@Data
public class FullAds {
    private Integer pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;
}
