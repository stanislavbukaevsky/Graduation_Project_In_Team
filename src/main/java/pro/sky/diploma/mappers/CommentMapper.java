package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.entities.Comment;

/**
 * Маппер-интерфейс, который преобразует сущность комментариев,опубликованных на платформе {@link Comment}
 * в DTO {@link CommentDTO}
 */
@Mapper
public interface CommentMapper {
    /**
     * Этот метод конвертирует сущность комментариев в DTO комментариев.
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param comment сущность комментариев
     * @return Возвращает сконвертированную DTO комментариев, опубликованных на платформе
     */
    @Mapping(source = "comment.user.id", target = "author")
    @Mapping(source = "comment.user.image.filePath", target = "authorImage")
    @Mapping(source = "comment.user.firstName", target = "authorFirstName")
    @Mapping(source = "dateTime", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss.SS")
    @Mapping(source = "id", target = "pk")
    CommentDTO importEntityToDTO(Comment comment);
}
