package pro.sky.diploma.servicies.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.dto.CreateAdsDTO;
import pro.sky.diploma.dto.FullAdsDTO;
import pro.sky.diploma.dto.ResponseWrapperAdsDTO;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Comment;
import pro.sky.diploma.entities.Image;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exceptions.AdsNotFoundException;
import pro.sky.diploma.exceptions.ImageNotFoundException;
import pro.sky.diploma.exceptions.UserNameNotFoundException;
import pro.sky.diploma.mappers.AdsMapper;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.repositories.CommentRepository;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.security.CustomUserDetailsService;
import pro.sky.diploma.security.UserSecurity;
import pro.sky.diploma.servicies.AdsService;
import pro.sky.diploma.servicies.ImageService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static pro.sky.diploma.constants.ExceptionTextMessageConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех объявлений, опубликованных на платформе.
 * Реализует интерфейс {@link AdsService}
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdsServiceImpl implements AdsService {
    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;
    private final ImageService imageService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserSecurity userSecurity;
    private final CommentRepository commentRepository;

    /**
     * Реализация метода для получения и просмотра всех объявлений, опубликованных на платформе
     *
     * @return Возвращает DTO всех опубликованных объявлений на платформе
     */
    @Override
    public ResponseWrapperAdsDTO getAllAds(String title) {
        logger.info(GET_ALL_ADS_MESSAGE_LOGGER_SERVICE);
        List<Ads> listAds = adsRepository.findAllByAds(title);
        List<AdsDTO> listAdsDTO = adsMapper.importEntityListAdsToListAdsDTO(listAds);
        ResponseWrapperAdsDTO responseWrapperAdsDTO = new ResponseWrapperAdsDTO();
        responseWrapperAdsDTO.setCount(listAdsDTO.size());
        responseWrapperAdsDTO.setResults(listAdsDTO);
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
        LocalDateTime dateTime = LocalDateTime.now();
        User user = userRepository.findUserByEmail(userSecurity.getUsername()).orElseThrow(() ->
                new UserNameNotFoundException(USER_NAME_NOT_FOUND_EXCEPTION));
        Ads ads = adsMapper.importEntityToCreateAdsDto(createAdsDTO);
        ads.setDateTime(dateTime);
        ads.setUser(user);
        Ads savedAds = adsRepository.save(ads);
        Image adsImage = imageService.addImageAds(savedAds.getId(), imageFile);
        savedAds.setImage(adsImage);
        return adsMapper.importEntityToDTO(savedAds);
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
        return adsMapper.importEntityToFullAdsDTO(ads);
    }

    /**
     * Реализация метода для удаления объявлений с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого объявления
     */
    @Override
    public void removeAds(Integer id) {
        logger.info(REMOVE_ADS_MESSAGE_LOGGER_SERVICE, id);
        Long idAds = Long.valueOf(id);
        Ads ads = adsRepository.findAdsById(idAds).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        List<Comment> comments = ads.getComments();
        if (checkUsersByAds(ads.getId())) {
            comments.stream().forEach(comment -> commentRepository.deleteById(comment.getId()));
            adsRepository.deleteById(idAds);
        }
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
        Ads ads = adsRepository.findAdsById(idAds).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        checkUsersByAds(idAds);
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
        List<Ads> listAdsByUser = adsRepository.findAdsByUserEmail(userSecurity.getUsername());
        List<AdsDTO> listAdsByUserDTO = adsMapper.importEntityListAdsToListAdsDTO(listAdsByUser);
        ResponseWrapperAdsDTO responseWrapperAdsDTO = new ResponseWrapperAdsDTO();
        responseWrapperAdsDTO.setCount(listAdsByUserDTO.size());
        responseWrapperAdsDTO.setResults(listAdsByUserDTO);
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
        checkUsersByAds(adsId);
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
     * Реализация метода для получения изображения у объявления по его идентификатору
     *
     * @param id идентификатор объявления
     * @return Возвращает массив байт искомого изображения
     */
    @Override
    public byte[] getAdsImage(Long id) {
        logger.info(GET_IMAGE_ADS_MESSAGE_LOGGER_SERVICE, id);
        byte[] image = imageService.getAdsImage(id);
        if (image == null) {
            throw new ImageNotFoundException(IMAGE_NOT_FOUND_EXCEPTION);
        }
        return image;
    }

    /**
     * Приватный метод, который проверяет авторизированного пользователя, размещающего объявление на платформе и пользователя по его роли.
     * Этот метод может выбросить исключение {@link ResponseStatusException} со статусом 403, если у пользователя нет доступа для действия
     *
     * @param id идентификатор объявления
     * @return Возвращает true, если условие выполняется, в противном случае выбрасывает исключение
     */
    private boolean checkUsersByAds(Long id) {
        logger.info(CHECK_USERS_ADS_MESSAGE_LOGGER_SERVICE, id);
        if (!(customUserDetailsService.checkAuthUserToAds(id) || customUserDetailsService.checkAdmin())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, USER_NAME_NOT_FOUND_EXCEPTION_2);
        }
        return true;
    }
}