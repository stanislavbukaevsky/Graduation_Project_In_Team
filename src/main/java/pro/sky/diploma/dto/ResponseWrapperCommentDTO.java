package pro.sky.diploma.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс-DTO для поиска комментариев на платформе
 */
@Data
public class ResponseWrapperCommentDTO {
    private Integer count;
    private List<CommentDTO> results;
}
