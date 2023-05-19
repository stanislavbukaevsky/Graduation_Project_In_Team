package pro.sky.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.dto.CreateAdsDTO;
import pro.sky.diploma.dto.FullAdsDTO;
import pro.sky.diploma.dto.ResponseWrapperAdsDTO;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Image;
import pro.sky.diploma.exceptions.AdsNotFoundException;
import pro.sky.diploma.mappers.AdsMapper;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.security.CustomUserDetailsService;
import pro.sky.diploma.security.UserSecurity;
import pro.sky.diploma.services.AdsService;
import pro.sky.diploma.services.ImageService;

import java.io.IOException;
import java.util.List;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.ADS_NOT_FOUND_EXCEPTION;
import static pro.sky.diploma.constants.ExceptionTextMessageConstant.USER_NAME_NOT_FOUND_EXCEPTION_2;
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
    private final CustomUserDetailsService customUserDetailsService;

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
     * @param createAdsDTO добавляемое объявление
     * @param imageFile    изображение
     * @return Возвращает новое, добавленное DTO-объявление на платформу
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public AdsDTO addAds(CreateAdsDTO createAdsDTO, MultipartFile imageFile) throws IOException {
        logger.info(ADD_ADS_MESSAGE_LOGGER_SERVICE, createAdsDTO, imageFile);
        Ads ads = adsMapper.importEntityToCreateAdsDto(createAdsDTO);
        Ads createAds = adsRepository.save(ads);
        Image image = imageService.addImageAds(createAds.getId(), imageFile);
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
     * @param id           идентификатор удаляемого объявления
     * @param userSecurity класс, с авторизированными пользователями
     * @return Возвращает DTO удаленного объявления
     */
    @Override
    public AdsDTO removeAds(Integer id, UserSecurity userSecurity) {
        logger.info(REMOVE_ADS_MESSAGE_LOGGER_SERVICE, id);
        Long idAds = Long.valueOf(id);
        Ads ads = adsRepository.findAdsById(idAds).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        if (ads != null && checkUsersByAds(id, userSecurity)) {
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
     * @param userSecurity класс, с авторизированными пользователями
     * @return Возвращает DTO измененного объявления на платформе
     */
    @Override
    public AdsDTO updateAds(Integer id, CreateAdsDTO createAdsDTO, UserSecurity userSecurity) {
        logger.info(UPDATE_ADS_MESSAGE_LOGGER_SERVICE, id, createAdsDTO);
        Long idAds = Long.valueOf(id);
        Ads ads = adsRepository.findAdsById(idAds).orElse(null);
        if (ads == null) {
            throw new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION);
        }
        checkUsersByAds(id, userSecurity);
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

    /**
     * Реализация метода для изменения изображения у объявления, размещенного на платформе
     *
     * @param id        идентификатор объявления
     * @param imageFile изображение
     * @return Возвращает DTO измененного изображения у объявления, размещенного на платформе
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public AdsDTO updateImage(Integer id, MultipartFile imageFile) throws IOException {
        logger.info(UPDATE_IMAGE_ADS_MESSAGE_LOGGER_SERVICE, id);
        Long adsId = Long.valueOf(id);
        Ads ads = adsRepository.findAdsById(adsId).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));

        if (ads.getImage() == null) {
            imageService.addImageAds(ads.getId(), imageFile);
        } else if (ads.getImage() != null) {
            imageService.updateImageAds(ads.getId(), imageFile);
        }
        return adsMapper.importEntityToDTO(ads);
    }

    /**
     * Приватный метод, который проверяет авторизированного пользователя, размещающего объявление на платформе и пользователя по его роли.
     * Этот метод может выбросить исключение {@link ResponseStatusException} со статусом 403, если у пользователя нет доступа для действия
     *
     * @param id           идентификатор объявления
     * @param userSecurity класс, с авторизированными пользователями
     * @return Возвращает true, если условие выполняется, в противном случае выбрасывает исключение
     */
    private boolean checkUsersByAds(Integer id, UserSecurity userSecurity) {
        logger.info(CHECK_USERS_ADS_MESSAGE_LOGGER_SERVICE, id);
        if (!(customUserDetailsService.checkAuthUserToAds(id, userSecurity) || customUserDetailsService.checkAdmin(userSecurity))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, USER_NAME_NOT_FOUND_EXCEPTION_2);
        }
        return true;
    }
}
