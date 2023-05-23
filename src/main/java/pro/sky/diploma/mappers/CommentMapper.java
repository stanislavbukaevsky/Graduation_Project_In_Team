package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.CommentDTO;
import pro.sky.diploma.dto.ResponseWrapperCommentDTO;
import pro.sky.diploma.entities.Comment;
import pro.sky.diploma.entities.Image;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

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
    @Mapping(source = "comment.ads.image", target = "authorImage")
    @Mapping(source = "comment.user.firstName", target = "authorFirstName")
    @Mapping(source = "dateTime", target = "createdAt", dateFormat = "dd/MM/yyyy HH:mm:ss.SS")
    @Mapping(source = "id", target = "pk")
    CommentDTO importEntityToDTO(Comment comment);

    /**
     * Этот метод конвертирует дату и время в объект типа Long
     *
     * @param dateTime дата и время
     * @return Возвращает дату и время создания комментария в миллисекундах
     */
    default Long importDateAndTimeToLong(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.ofHours(3)).toEpochMilli();
    }

    /**
     * Этот метод указывает путь к изображению
     *
     * @param image сущность изображения
     * @return Возвращает ссылку на изображение в строковом виде
     */
    default String importEntityToStringLink(Image image) {
        return "/ads/images/" + image.getAds().getId();
    }

    /**
     * Этот метод конвертирует сущность комментарий и переменную в DTO с запросом по поиску комментариев. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param count    общее количество комментариев
     * @param comments список комментариев с параметром сущности комментария
     * @return Возвращает сконвертированную DTO с запросом по поиску комментариев, опубликованном на платформе
     */
    @Mapping(source = "comments", target = "results")
    ResponseWrapperCommentDTO importVariablesToDTO(Integer count, List<Comment> comments);
}
