package pro.sky.diploma.mapping;

import org.mapstruct.Mapper;
import pro.sky.diploma.dto.Ads;
import pro.sky.diploma.dto.ResponseWrapperAds;
import pro.sky.diploma.entity.AdsEntity;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AdsMapper {
    Ads toDto(AdsEntity save);

    List<Ads> toAdsList(List<AdsEntity> all);

    ResponseWrapperAds mapToResponseWrapperAds(List<Ads> listDto, int size);
    // @Mapping(target = "id", source = "entity.id")
   // @Mapping(target = "title", source = "entity.title")
   // @Mapping(target = "imageId", source = "entity")
    //@Mapping(target = "author", source = "entity.author.id")
   // @Mapping(target = "price", source = "entity.price")
   // Ads toDto(AdsEntity entity);

   // List<Ads> toAdsDtoList(List<AdsEntity> entityList);

   // @Mapping(target = "results", source = "list")
  //  ResponseWrapperAds mapToResponseWrapperAdsDto(List<Ads> list, Integer count);
//
    //default String mapImageToString(AdsEntity adsEntity) {
      //  if (adsEntity.getImage() == null || adsEntity.getImage().getId() == null) {
      //      return null;
     //   } else {
     //       return "/ads/" + adsEntity.getId() + "/image/";
     //   }
   // }

    //default List<Ads> toAdsDtoList(List<AdsEntity> all) {
    //    return null;
    }
//}