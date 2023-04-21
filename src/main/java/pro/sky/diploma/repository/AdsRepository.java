package pro.sky.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entity.AdsEntity;


import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<AdsEntity, Integer> {
    List<AdsEntity> findAdsEntityByAuthor_Id(Integer id);


}