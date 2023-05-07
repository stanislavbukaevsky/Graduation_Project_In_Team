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
     * Сигнатура метода добавления изображения на платформу
     *
     * @param adsId     идентификатор объявления
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image addImage(Long adsId, MultipartFile imageFile) throws IOException;
}