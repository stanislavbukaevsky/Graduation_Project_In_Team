package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для опубликования комментариев
 */
@Data
public class Comment {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
    private Integer pk;
    private String text;
}
