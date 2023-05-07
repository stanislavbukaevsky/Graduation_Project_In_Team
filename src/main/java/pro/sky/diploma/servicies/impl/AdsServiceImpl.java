package pro.sky.diploma.servicies.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.dto.CreateAdsDTO;
import pro.sky.diploma.dto.FullAdsDTO;
import pro.sky.diploma.dto.ResponseWrapperAdsDTO;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Image;
import pro.sky.diploma.exception.AdsNotFoundException;
import pro.sky.diploma.mappers.AdsMapper;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.servicies.AdsService;
import pro.sky.diploma.servicies.ImageService;

import java.io.IOException;
import java.util.List;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.ADS_NOT_FOUND_EXCEPTION;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех объявлений, опубликованных на платформе.
 * Реализует интерфейс {@link AdsService}
 */
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);
    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;
    private final ImageService imageService;

    /**
     * Реализация метода для получения и просмотра всех объявлений, опубликованных на платформе
     *
     * @return Возвращает DTO всех опубликованных объявлений на платформе
     */
    @Override
    public ResponseWrapperAdsDTO getAllAds() {
        logger.info(GET_ALL_ADS_MESSAGE_LOGGER_SERVICE);
        List<Ads> listAds = adsRepository.findAll();
        ResponseWrapperAdsDTO responseWrapperAdsDTO = adsMapper.importVariablesToDTO(listAds.size(), listAds);

        for (Ads ads : listAds) {
            List<Ads> adsByTitle = adsRepository.searchAdsByTitleLikeIgnoreCase(ads.getTitle());
            List<AdsDTO> adsListDTO = adsMapper.importEntityListAdsToListAdsDTO(adsByTitle);
            responseWrapperAdsDTO.setCount(adsListDTO.size());
            responseWrapperAdsDTO.setResults(adsListDTO);
        }
        return responseWrapperAdsDTO;
    }

    /**
     * Реализация метода для добавления новых объявлений на платформу. <br>ъ
     *
     * @param createAdsDTO  добавляемое объявление
     * @param multipartFile изображение
     * @return Возвращает новое, добавленное DTO-объявление на платформу
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public AdsDTO addAds(CreateAdsDTO createAdsDTO, MultipartFile multipartFile) throws IOException {
        logger.info(ADD_ADS_MESSAGE_LOGGER_SERVICE, createAdsDTO, multipartFile);
        Ads ads = adsMapper.importEntityToCreateAdsDto(createAdsDTO);
        Ads createAds = adsRepository.save(ads);
        Image image = imageService.addImage(createAds.getId(), multipartFile);
        createAds.setImage(image);
        AdsDTO adsDTO = adsMapper.importEntityToDTO(createAds);
        return adsDTO;
    }

    /**
     * Реализация метода для получения дополнительной информации об объявлении, размещенном на платформе
     *
     * @param id идентификатор объявления
     * @return Возвращает DTO искомого объявления
     */
    @Override
    public FullAdsDTO getAds(Integer id) {
        logger.info(GET_ADS_MESSAGE_LOGGER_SERVICE, id);
        Long idAds = Long.valueOf(id);
        Ads ads = adsRepository.findById(idAds).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        FullAdsDTO fullAdsDTO = adsMapper.importEntityToFullAdsDTO(ads);
        return fullAdsDTO;
    }

    /**
     * Реализация метода для удаления объявлений с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого объявления
     * @return Возвращает DTO удаленного объявления
     */
    @Override
    public AdsDTO removeAds(Integer id) {
        logger.info(REMOVE_ADS_MESSAGE_LOGGER_SERVICE, id);
        Long idAds = Long.valueOf(id);
        Ads ads = adsRepository.findAdsById(idAds).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        if (ads != null) {
            adsRepository.delete(ads);
        }
        AdsDTO adsDTO = adsMapper.importEntityToDTO(ads);
        return adsDTO;
    }

    /**
     * Реализация метода для измения информации об объявлении, размещенного на платформе
     *
     * @param id           идентификатор изменяемого объявления
     * @param createAdsDTO объявление
     * @return Возвращает DTO измененного объявления на платформе
     */
    @Override
    public AdsDTO updateAds(Integer id, CreateAdsDTO createAdsDTO) {
        logger.info(UPDATE_ADS_MESSAGE_LOGGER_SERVICE, id, createAdsDTO);
        Long idAds = Long.valueOf(id);
        Ads ads = adsRepository.findAdsById(idAds).orElse(null);
        if (ads == null) {
            throw new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION);
        }
        ads.setDescription(createAdsDTO.getDescription());
        ads.setPrice(createAdsDTO.getPrice());
        ads.setTitle(createAdsDTO.getTitle());
        Ads result = adsRepository.save(ads);
        return adsMapper.importEntityToDTO(result);
    }

    /**
     * Реализация метода для получения объявления авторизированного пользователя, размещенного на платформе
     *
     * @return Возвращает DTO объявления авторизированного пользователя, размещенного на платформе
     */
    @Override
    public ResponseWrapperAdsDTO getAdsMe() {
        logger.info(GET_ADS_ME_MESSAGE_LOGGER_SERVICE);
        List<Ads> listAds = adsRepository.findAll();
        ResponseWrapperAdsDTO responseWrapperAdsDTO = adsMapper.importVariablesToDTO(listAds.size(), listAds);

        for (Ads ads : listAds) {
            List<Ads> adsByEmail = adsRepository.findAdsByUserEmail(ads.getUser().getEmail());
            List<AdsDTO> adsDTO = adsMapper.importEntityListAdsToListAdsDTO(adsByEmail);
            responseWrapperAdsDTO.setCount(adsDTO.size());
            responseWrapperAdsDTO.setResults(adsDTO);
        }
        return responseWrapperAdsDTO;
    }
}