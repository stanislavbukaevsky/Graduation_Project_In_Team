package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для описания объявлений на платформе
 */
@Data
public class Ads {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
