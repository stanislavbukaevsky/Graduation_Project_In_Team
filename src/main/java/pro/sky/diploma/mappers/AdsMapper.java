package pro.sky.diploma.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.dto.CreateAdsDTO;
import pro.sky.diploma.dto.FullAdsDTO;
import pro.sky.diploma.dto.ResponseWrapperAdsDTO;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Image;

import java.util.List;

/**
 * Маппер-интерфейс, который преобразует сущность всех объявлений, опубликованных на платформе {@link Ads}
 * в DTO {@link AdsDTO}
 */
@Mapper
public interface AdsMapper {
    /**
     * Этот метод конвертирует сущность объявлений в DTO объявлений. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param ads сущность объявлений
     * @return Возвращает сконвертированную DTO объявлений, опубликованных на платформе
     */
    @Mapping(source = "ads.user.id", target = "author")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "id", target = "pk")
    AdsDTO importEntityToDTO(Ads ads);

    /**
     * Этот метод конвертирует сущность объявлений в DTO с полной информацией об объявлении. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param ads сущность объявлений
     * @return Возвращает сконвертированную DTO полной информации об объявлении, опубликованном на платформе
     */
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "ads.user.firstName", target = "authorFirstName")
    @Mapping(source = "ads.user.lastName", target = "authorLastName")
    @Mapping(source = "ads.user.email", target = "email")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "ads.user.phoneNumber", target = "phone")
    FullAdsDTO importEntityToFullAdsDTO(Ads ads);

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
     * Этот метод конвертирует сущность объявлений и переменную в DTO с запросом по поиску объявлений. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param count общее количество объявлений
     * @param ads   список объявлений с параметром сущности объявлений
     * @return Возвращает сконвертированную DTO с запросом по поиску объявлений, опубликованном на платформе
     */
    @Mapping(source = "ads", target = "results")
    ResponseWrapperAdsDTO importVariablesToDTO(Integer count, List<Ads> ads);

    /**
     * Этот метод конвертирует DTO добавленного объявления в сущность
     *
     * @param createAdsDTO DTO добавленного объявления
     * @return Возвращает сконвертированную сущность объявлений
     */
    Ads importEntityToCreateAdsDto(CreateAdsDTO createAdsDTO);

    /**
     * Этот метод конвертирует список с параметром сущности объявления, в список с параметром DTO объявления
     *
     * @param ads список объявлений с параметром сущности объявлений
     * @return Возвращает сконвертированный список с параметром DTO объявлений
     */
    List<AdsDTO> importEntityListAdsToListAdsDTO(List<Ads> ads);
}
