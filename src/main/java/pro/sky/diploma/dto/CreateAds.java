package pro.sky.diploma.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Класс-DTO для размещения объявлений на платформу
 */

@Data
@Validated
public class CreateAds {
    @JsonProperty("description")
    @NotNull
    private String description;
    @JsonProperty("title")
    @NotNull
    private String title;
    @JsonProperty("price")
    @NotNull
    @Positive
    private Integer price;
}