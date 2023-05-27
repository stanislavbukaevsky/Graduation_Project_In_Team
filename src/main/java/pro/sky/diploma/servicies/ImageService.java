package pro.sky.diploma.servicies;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.entities.Image;

import java.io.IOException;

/**
 * Сервис-интерфейс для всех изображений, опубликованных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface ImageService {
    /**
     * Сигнатура метода для добавления аватарки у авторизированного пользователя, зарегистрированного на платформе
     *
     * @param username  имя пользователя (логин)
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение у пользователя
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image addImageUser(String username, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для изменения аватарки у авторизированного пользователя, зарегистрированного на платформе
     *
     * @param username  имя пользователя (логин)
     * @param imageFile файл изображения
     * @return Возвращает измененное изображение у пользователя
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image updateImageUser(String username, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для добавления изображения к объявлению, размещенного на платформе
     *
     * @param id        идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение к объявлению
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image addImageAds(Long id, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для изменения изображения у объявления, размещенного на платформе
     *
     * @param id        идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает измененное изображение у объявления
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image updateImageAds(Long id, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для получения аватарки пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return Возвращает массив байт искомой аватарки
     */
    byte[] getUserImage(Long id);

    /**
     * Сигнатура метода для получения изображения у объявления по его идентификатору
     *
     * @param id идентификатор объявления
     * @return Возвращает массив байт искомого изображения
     */
    byte[] getAdsImage(Long id);
}