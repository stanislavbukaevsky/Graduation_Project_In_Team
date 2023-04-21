package pro.sky.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.diploma.entity.CommentEntity;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AdsCommentRepository extends JpaRepository<CommentEntity,Integer> {
    List<CommentEntity> findAllByAds_Id(Integer id);
    Optional<CommentEntity> findByAds_IdAndId(Integer adsId, Integer id);
    @Modifying
    @Query(value = "DELETE FROM comments WHERE ads_id=:adsId", nativeQuery = true)
    void deleteAllByAdsId(Integer adsId);
}