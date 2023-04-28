package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.entities.Ads;

/**
 * Маппер-интерфейс, который преобразует сущность всех объявлений, опубликованных на платформе {@link Ads}
 * в DTO {@link AdsDTO}
 */
@Mapper
public interface AdsMapper {
    /**
     * Этот метод конвертирует сущность объявлений в DTO объявлений.
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param ads сущность объявлений
     * @return Возвращает сконвертированную DTO объявлений, опубликованных на платформе
     */
    @Mapping(source = "ads.user.id", target = "author")
    @Mapping(source = "ads.image.filePath", target = "image")
    @Mapping(source = "id", target = "pk")
    AdsDTO importEntityToDTO(Ads ads);
}
