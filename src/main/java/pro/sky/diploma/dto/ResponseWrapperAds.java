package pro.sky.diploma.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс-DTO поиска объявлений на платформе
 */
@Data
public class ResponseWrapperAds {
    private Integer count;
    private List<Ads> results;
}
