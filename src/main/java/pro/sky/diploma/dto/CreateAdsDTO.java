package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для размещения объявлений на платформу
 */
@Data
public class CreateAdsDTO {
    private String description;
    private Integer price;
    private String title;
    private String image;
}