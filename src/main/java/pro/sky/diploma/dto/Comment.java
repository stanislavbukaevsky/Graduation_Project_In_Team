package pro.sky.diploma.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

/**
 * Класс-DTO для опубликования комментариев
 */

@Data
public class Comment {
    @JsonProperty("author")
    private Integer author;
    @JsonProperty("authorImage")
    private String authorAvatar;
    @JsonProperty("authorFirstName")
    private String authorFirstName;
    @JsonProperty("pk")
    private Integer id;
    @JsonProperty("text")
    @NotNull
    private String text;
    @JsonProperty("createdAt")
    private String createdAt;
}