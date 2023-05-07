package pro.sky.diploma.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс-DTO поиска объявлений на платформе
 */
@Data
public class ResponseWrapperAdsDTO {
    private Integer count;
    private List<AdsDTO> results;
}