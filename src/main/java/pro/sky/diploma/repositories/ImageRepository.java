package pro.sky.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entities.Image;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех изображений, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Image} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Этот метод ищет изображения по идентификатору объявления
     *
     * @param id идентификатор объявления
     * @return Возвращает найденное изображение по идентификатору объявления
     */
    Optional<Image> findImageByAdsId(Long id);

    /**
     * Этот метод ищет изображения по идентификатору зарегистрированного пользователя
     *
     * @param id идентификатор пользователя
     * @return Возвращает найденное изображение по идентификатору пользователя
     */
    Optional<Image> findImageByUserId(Long id);
}
