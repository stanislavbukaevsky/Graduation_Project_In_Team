package pro.sky.diploma.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.diploma.dto.AdsDTO;
import pro.sky.diploma.entities.Ads;
import pro.sky.diploma.repositories.AdsRepository;
import pro.sky.diploma.services.AdsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис-класс с бизнес-логикой для всех объявлений, опубликованных на платформе.
 * Реализует интерфейс {@link AdsService}
 */
@AllArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;

    public List<AdsDTO> findAllAds() {
        List<Ads> adsList = adsRepository.findAll();
        List<AdsDTO> adsDTOList = new ArrayList<>();
        for (Ads ads: adsList) {
            AdsDTO adsDTO = AdsDTO.fromAds(ads);
            adsDTOList.add(adsDTO);
        }
        return adsDTOList;
    }
}
