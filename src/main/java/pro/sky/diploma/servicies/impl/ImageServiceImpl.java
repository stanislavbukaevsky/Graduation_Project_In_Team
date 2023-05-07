package pro.sky.diploma.servicies.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.entities.Image;
import pro.sky.diploma.entities.User;
import pro.sky.diploma.exception.AdsNotFoundException;
import pro.sky.diploma.exception.UserNameNotFoundException;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.repositories.ImageRepository;
import pro.sky.diploma.repositories.UserRepository;
import pro.sky.diploma.servicies.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static pro.sky.diploma.constants.ExceptionTextMessageConstant.ADS_NOT_FOUND_EXCEPTION;
import static pro.sky.diploma.constants.ExceptionTextMessageConstant.USER_NAME_NOT_FOUND_EXCEPTION;
import static pro.sky.diploma.constants.LoggerTextMessageConstant.ADD_IMAGE_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для всех изображений, опубликованных на платформе.
 * Реализует интерфейс {@link ImageService}
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    @Value("${path.to.avatars.folder}")
    private String imageDir;

    /**
     * Реализация метода добавления изображения на платформу
     *
     * @param adsId     идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image addImage(Long adsId, MultipartFile imageFile) throws IOException {
        logger.info(ADD_IMAGE_MESSAGE_LOGGER_SERVICE, adsId);
        Ads ads = adsRepository.findAdsById(adsId).orElseThrow(() ->
                new AdsNotFoundException(ADS_NOT_FOUND_EXCEPTION));
        User user = userRepository.findUserByEmail(ads.getUser().getEmail()).orElseThrow(() ->
                new UserNameNotFoundException(USER_NAME_NOT_FOUND_EXCEPTION));
        LocalDateTime dateTime = LocalDateTime.now();
        Path path = Path.of(imageDir, adsId + "." + getExtensions(imageFile.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = imageFile.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Image image = imageRepository.findByAdsId(adsId).orElseGet(Image::new);
        image.setFilePath(path.toString());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        image.setDateTime(dateTime);
        image.setUser(user);
        image.setAds(ads);

        Image addImage = imageRepository.save(image);
        return addImage;
    }

    /**
     * Приватный метод, генерирующий расширение файла
     *
     * @param fileName название файла
     * @return Возвращает сгенерированное расширение файла
     */
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
