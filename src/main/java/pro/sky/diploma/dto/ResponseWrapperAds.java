package pro.sky.diploma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Класс-DTO поиска объявлений на платформе
 */

@Data
public class ResponseWrapperAds {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("results")
    private List<Ads> results;
}
