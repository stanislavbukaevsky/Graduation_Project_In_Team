package pro.sky.diploma.dto;

import lombok.Data;

/**
 * Класс-DTO для опубликования комментариев
 */
@Data
public class CommentDTO {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private Integer pk;
    private String text;
}