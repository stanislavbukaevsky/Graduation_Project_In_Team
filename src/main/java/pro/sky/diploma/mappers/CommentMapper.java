package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.entities.Comment;

/**
 * Маппер-интерфейс, который преобразует сущность комментариев,опубликованных на платформе {@link Comment}
 * в DTO {@link CommentDTO}
 */
@Mapper
public interface CommentMapper {
}
