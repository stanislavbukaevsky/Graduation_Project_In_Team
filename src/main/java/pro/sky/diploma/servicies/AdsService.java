package pro.sky.diploma.servicies;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.dto.CreateAdsDTO;
import pro.sky.diploma.dto.FullAdsDTO;
import pro.sky.diploma.dto.ResponseWrapperAdsDTO;

import java.io.IOException;

/**
 * Сервис-интерфейс для всех объявлений опубликованных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AdsService {
    /**
     * Сигнатура метода для получения и просмотра всех объявлений, опубликованных на платформе
     *
     * @return Возвращает DTO всех опубликованных объявлений на платформе
     */
    ResponseWrapperAdsDTO getAllAds(String title);

    /**
     * Сигнатура метода для добавления новых объявлений на платформу. <br>ъ
     *
     * @param createAdsDTO  добавляемое объявление
     * @param multipartFile изображение
     * @return Возвращает новое, добавленное DTO-объявление на платформу
     * @throws IOException общий класс исключений ввода-вывода
     */
    AdsDTO addAds(CreateAdsDTO createAdsDTO, MultipartFile multipartFile) throws IOException;

    /**
     * Сигнатура метода для получения дополнительной информации об объявлении, размещенном на платформе
     *
     * @param id идентификатор объявления
     * @return Возвращает DTO искомого объявления
     */
    FullAdsDTO getAds(Integer id);

    /**
     * Сигнатура метода для удаления объявлений с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого объявления
     */
    void removeAds(Integer id);

    /**
     * Сигнатура метода для измения информации об объявлении, размещенного на платформе
     *
     * @param id           идентификатор изменяемого объявления
     * @param createAdsDTO объявление
     * @return Возвращает DTO измененного объявления на платформе
     */
    AdsDTO updateAds(Integer id, CreateAdsDTO createAdsDTO);

    /**
     * Сигнатура метода для получения объявления авторизированного пользователя, размещенного на платформе
     *
     * @return Возвращает DTO объявления авторизированного пользователя, размещенного на платформе
     */
    ResponseWrapperAdsDTO getAdsMe();

    /**
     * Сигнатура метода для изменения изображения у объявления, размещенного на платформе
     *
     * @param id        идентификатор объявления
     * @param imageFile изображение
     * @return Возвращает DTO измененного изображения у объявления, размещенного на платформе
     * @throws IOException общий класс исключений ввода-вывода
     */
    AdsDTO updateImage(Integer id, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для получения изображения у объявления по его идентификатору
     *
     * @param id идентификатор объявления
     * @return Возвращает массив байт искомого изображения
     */
    byte[] getAdsImage(Long id);

}