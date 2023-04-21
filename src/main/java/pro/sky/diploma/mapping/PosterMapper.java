package pro.sky.diploma.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pro.sky.diploma.dto.Poster;
import pro.sky.diploma.entity.PosterEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PosterMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "adsId", target = "ads.id")
    @Mapping(source = "path", target = "path")
    PosterEntity toEntity(Poster dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "ads.id", target = "adsId")
    @Mapping(source = "path", target = "path")
    Poster toDto(PosterEntity entity);

    List<Poster> toDtoList(List<PosterEntity> entityList);

    List<PosterEntity> toEntityList(List<Poster> dtoList);
}