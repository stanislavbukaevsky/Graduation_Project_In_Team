package pro.sky.diploma.mapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.CreateAds;
import pro.sky.diploma.entity.AdsEntity;

@Mapper(componentModel = "spring")
public interface CreateAdsMapper {
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    AdsEntity toModel(CreateAds dto);
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "title", source = "title")
    CreateAds toDto(AdsEntity entity);
}