package pro.sky.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entities.Ads;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех объявлений, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Ads} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    /**
     * Этот метод выводит объявление, найденное по его идентификатору
     *
     * @param id идентификатор объявления
     * @return Возвращает объявление, у которого совпадает идентификатор с искомым
     */
    Optional<Ads> getAdsById(Long id);

    /**
     * Этот метод ищет объявление по его идентификатору
     *
     * @param id идентификатор объявления
     * @return Возвращает найденное объявление, у которого совпадает идентификатор с искомым
     */
    Optional<Ads> findAdsById(Long id);

    /**
     * Этот метод ищет все объявления по его названию игнорируя регистр. <br>
     * В этом методе используется аннотация {@link Query}, для детального поиска по объявлениям.
     * Также, используется оператор Like, для обширного поиска
     *
     * @param title название объявления
     * @return Возвращает список найденных по названию объявлений
     */
    @Query(value = "SELECT * FROM ads WHERE ads.title LIKE %:title%", nativeQuery = true)
    List<Ads> searchAdsByTitleLikeIgnoreCase(@Param("title") String title);

    /**
     * Этот метод ищет все объявления, размещенные одним пользователем по его электронной почте
     *
     * @param email электронная почта пользователя (логин)
     * @return Возвращает список найденных по электронной почте объявлений
     */
    List<Ads> findAdsByUserEmail(String email);
}
