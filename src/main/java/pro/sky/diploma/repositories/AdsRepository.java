package pro.sky.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entities.Ads;

/**
 * Интерфейс-репозиторий для работы с методами всех объявлений, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Ads} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
}
