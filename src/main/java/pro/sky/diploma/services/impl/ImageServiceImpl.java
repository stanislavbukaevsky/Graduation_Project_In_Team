package pro.sky.diploma.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Image;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exceptions.AdsNotFoundException;
import pro.sky.diploma.exceptions.ImageNotFoundException;
import pro.sky.diploma.exceptions.UserNameNotFoundException;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.repositories.ImageRepository;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.services.ImageService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static pro.sky.diploma.constants.ExceptionTextMessageConstant.*;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех изображений, опубликованных на платформе.
 * Реализует интерфейс {@link ImageService}
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {
    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    @Value("${path.to.avatars.folder}")
    private String imageDir;

    /**
     * Реализация метода для добавления аватарки у авторизированного пользователя, зарегистрированного на платформе
     *
     * @param username  имя пользователя (логин)
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение у пользователя
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image addImageUser(String username, MultipartFile imageFile) throws IOException {
        logger.info(ADD_IMAGE_USER_MESSAGE_LOGGER_SERVICE, username);
        LocalDateTime dateTime = LocalDateTime.now();
        User user = userRepository.findUserByEmail(username).orElseThrow(() ->
                new UserNameNotFoundException(USER_NAME_NOT_FOUND_EXCEPTION));
        Image image = new Image();
        addImage(user.getId(), imageFile);
        image.setDateTime(dateTime);
        image.setUser(user);
        Image addImage = imageRepository.save(image);
        return addImage.getUser().getImage();
    }

    /**
     * Реализация метода для изменения аватарки у авторизированного пользователя, зарегистрированного на платформе
     *
     * @param id        идентификатор пользователя
     * @param username  имя пользователя (логин)
     * @param imageFile файл изображения
     * @return Возвращает измененное изображение у пользователя
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image updateImageUser(Long id, String username, MultipartFile imageFile) throws IOException {
        logger.info(UPDATE_IMAGE_USER_MESSAGE_LOGGER_SERVICE, id, username);
        LocalDateTime dateTime = LocalDateTime.now();
        User user = userRepository.findUserByEmail(username).orElseThrow(() ->
                new UserNameNotFoundException(USER_NAME_NOT_FOUND_EXCEPTION));
        Image image = imageRepository.findByUserId(user.getId()).orElseThrow(() ->
                new ImageNotFoundException(IMAGE_NOT_FOUND_EXCEPTION));
        addImage(user.getId(), imageFile);
        image.setDateTime(dateTime);
        image.setUser(user);
        Image updateImage = imageRepository.save(image);
        return updateImage.getUser().getImage();
    }

    /**
     * Реализация метода для добавления изображения к объявлению, размещенного на платформе
     *
     * @param id        идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение к объявлению
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image addImageAds(Long id, MultipartFile imageFile) throws IOException {
        logger.info(ADD_IMAGE_ADS_MESSAGE_LOGGER_SERVICE, id);
        LocalDateTime dateTime = LocalDateTime.now();
        Ads ads = adsRepository.findAdsById(id).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        Image image = new Image();
        addImage(ads.getId(), imageFile);
        image.setDateTime(dateTime);
        image.setAds(ads);
        Image addImage = imageRepository.save(image);
        return addImage.getAds().getImage();
    }

    /**
     * Реализация метода для изменения изображения у объявления, размещенного на платформе
     *
     * @param id        идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает измененное изображение у объявления
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image updateImageAds(Long id, MultipartFile imageFile) throws IOException {
        logger.info(UPDATE_IMAGE_ADS_MESSAGE_LOGGER_IMAGE_SERVICE, id);
        LocalDateTime dateTime = LocalDateTime.now();
        Ads ads = adsRepository.findAdsById(id).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        Image image = imageRepository.findByAdsId(ads.getId()).orElseThrow(() ->
                new ImageNotFoundException(IMAGE_NOT_FOUND_EXCEPTION));
        addImage(ads.getId(), imageFile);
        image.setDateTime(dateTime);
        image.setAds(ads);
        Image updateImage = imageRepository.save(image);
        return updateImage.getAds().getImage();
    }

    /**
     * Приватный метод добавления изображения на платформу
     *
     * @param id        идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение
     * @throws IOException общий класс исключений ввода-вывода
     */
    private Image addImage(Long id, MultipartFile imageFile) throws IOException {
        logger.info(ADD_IMAGE_MESSAGE_LOGGER_SERVICE, id);
        Path path = Path.of(imageDir, id + "." + getExtensions(imageFile.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = imageFile.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Image image = new Image();
        image.setFilePath(path.toString());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());

        return imageRepository.save(image);
    }

    /**
     * Приватный метод, генерирующий расширение файла
     *
     * @param fileName название файла
     * @return Возвращает сгенерированное расширение файла
     */
    private String getExtensions(String fileName) {
        logger.info(GET_EXTENSIONS_MESSAGE_LOGGER_SERVICE, fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
